package com.whattoeat.fos.Food.Service

import com.whattoeat.fos.Food.DTO.FoodDTO
import com.whattoeat.fos.Food.Domain.Entity.FoodSpecs
import com.whattoeat.fos.Food.Domain.Entity.Rank
import com.whattoeat.fos.Food.Domain.Repository.FoodRepository
import com.whattoeat.fos.Food.Domain.Repository.RankRepository
import com.whattoeat.fos.Util.Response
import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@RequiredArgsConstructor
@Service
class FoodService {
    @Autowired
    private lateinit var foodRepository: FoodRepository

    @Autowired
    private lateinit var rankRepository: RankRepository

    fun findAll(order: String, page: Int, pageSize: Int) : ResponseEntity<Response>{
        val pageRequest = PageRequest.of(page -1, pageSize, Sort.by(order))
        val foodPage = foodRepository.findAll(pageRequest)
        val arr: ArrayList<FoodDTO.FoodListResponse> = ArrayList()
        for (food in foodPage) {
            arr.add(
                FoodDTO.FoodListResponse(
                    id = food.id,
                    categoryName = food.category?.title!!,
                    menuName = food.name,
                    image = food.image ?: "null"
                )
            )
        }
        return Response.newResult(HttpStatus.OK, "전체 메뉴를 불러왔어요.", arr as Any)
    }

    fun findFoodsWithFilter(searchKeys: Map<FoodSpecs.SearchKey, Any>, order: String, page: Int, pageSize: Int) : ResponseEntity<Response>{
        val pageRequest = PageRequest.of(page -1, pageSize, Sort.by(order))
        val foodPage = foodRepository.findAll(FoodSpecs().searchWith(searchKeys), pageRequest)

        // 결과
        val arr: ArrayList<FoodDTO. FoodListResponse> = ArrayList()
        for (food in foodPage) {
            arr.add(
                FoodDTO.FoodListResponse(
                    id = food.id,
                    categoryName = food.category?.title!!,
                    menuName = food.name,
                    image = food.image ?: "null"
                )
            )
        }

        return if (arr.isNotEmpty()) {
            Response.newResult(HttpStatus.OK, "일치하는 메뉴를 불러왔습니다. ", arr as Any)
        } else {
            Response.newResult(HttpStatus.NO_CONTENT, "조건을 만족하는 결과가 없어요", arr as Any)
        }
    }
    fun getFoodListByKeyword(keyword: String): ResponseEntity<Response>{
        val list = foodRepository.findFoodsByNameContainsOrderByName(keyword)
        val arr = ArrayList<FoodDTO.FoodListResponse>()
        for (food in list){
            arr.add(FoodDTO.FoodListResponse(id = food.id,
                categoryName = food.category?.title!!,
                menuName = food.name,
                image = food.image ?: "null"))
        }
        return Response.newResult(HttpStatus.OK, "키워드로 음식을 검색했습니다", arr as Any)
    }


    // 5개 순위 가져오기
    fun getTop5Ranks() : ResponseEntity<Response>{
        // 지금 상위 5개
        val curList: List<FoodDTO.FoodRank> = foodRepository.findTopRank()
        val prevList: List<Rank> = rankRepository.findRanksByIdIsLessThanOrderById(6)

        val prevMap : HashMap<Int, Int> = HashMap()
        for (item in prevList) {
            prevMap.put(item.menuId, item.id)
        }

        val data = mutableListOf<FoodDTO.FoodRankResponse>()
        for (item in curList) {
            // rank, menu_id, menu_name, count 순서
            val rank: Int = item.rank
            val menuId: Int = item.menuId
            val menuName: String = item.menuName
            val count: Int = item.count
            // 기존에도 순위에 들었던 메뉴라면,
            if (!prevMap.containsKey(menuId)){
                data.add(
                    FoodDTO.FoodRankResponse(
                        rank = rank,
                        menuId = menuId,
                        menuName = menuName,
                        orderCount = count,
                        rankDiff = 0
                    )
                )
            } else {
                // 기존에 순위에 없었다면,,
                data.add(
                    FoodDTO.FoodRankResponse(
                        rank = rank,
                        menuId = menuId,
                        menuName = menuName,
                        orderCount = count,
                        rankDiff = prevMap.get(menuId)!! - rank
                    )
                )
            }
        }

        return Response.newResult(status = HttpStatus.OK,
            message ="지금 가장 인기있는 메뉴 목록을 가져왔어요.",
            result = data as Any
        )
    }

}