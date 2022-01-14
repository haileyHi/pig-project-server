package com.whattoeat.fos.Food.Service

import com.whattoeat.fos.Food.DTO.FoodDTO
import com.whattoeat.fos.Food.Domain.Repository.FoodRepository
import com.whattoeat.fos.Util.Response
import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@RequiredArgsConstructor
@Service
class FoodService {
    private lateinit var foodRepository: FoodRepository

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

    fun getFoodListByCategory(category: String): ResponseEntity<Response>{
        val list = foodRepository.findFoodsByCategoryOrderByNameAsc(category)
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



}