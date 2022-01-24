package com.whattoeat.fos.Food.Service

import com.whattoeat.fos.Food.DTO.FoodDTO
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

    fun getFoodListByKeyword(keyword: String): ResponseEntity<Response>{
        val list = foodRepository.findFoodsByNameContainsOrderByName(keyword)
        val arr = ArrayList<FoodDTO.FoodListResponse>()
        for (food in list){
            arr.add(FoodDTO.FoodListResponse(id = food.id,
                categoryName = food.category?.title!!,
                menuName = food.name,
                image = food.image ?: "null"))
        }
        return Response.newResult(HttpStatus.OK, "키워드로 음식을 검색했습니다", arr as Object);
    }

    fun getFoodListByCategory(category: String, sortBy: String, page: Int, pageSize: Int): ResponseEntity<Response>{
        if(sortBy == "rank" || sortBy == "alphabet" || sortBy == "standard") {
            val pageRequest: PageRequest = PageRequest.of(page, pageSize, Sort.by(sortBy))
        }
        val pageRequest = PageRequest.of(page, pageSize, Sort.by("rank"))
        if (sortBy == "alphabet") {

        }
        val list = foodRepository.findFoodsByCategory_TitleOrderByNameAsc(category)
        val arr = ArrayList<FoodDTO.FoodListResponse>()
        for (food in list){
            arr.add(FoodDTO.FoodListResponse(
                id = food.id,
                categoryName = food.category?.title!!,
                menuName = food.name,
                image = food.image ?: "null"
            ))
        }
        return Response.newResult(HttpStatus.OK, "카테고리로 음식을 검색했습니다.", arr as Object)
    }

    // 5개 순위 가져오기
    fun getTop5Ranks() : ResponseEntity<Response>{
        // 지금 상위 5개
        val curList: List<Array<Any>> = foodRepository.findTopRank()
        val prevList = rankRepository.findAll()

        val prevMap : HashMap<Int, Int> = HashMap()
        for (item in prevList){
            prevMap.put(item.menuId, item.id)
        }

        val data = mutableListOf<FoodDTO.FoodRankResponse>()
        for (item in curList) {
            // rank, menu_id, menu_name, count 순서
            val rank: Int = item[0] as Int;
            val menuId: Int = item[1] as Int;
            val menuName: String = item[2] as String;
            val count: Int = item[3] as Int;
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
            result = data as Object)
    }

}