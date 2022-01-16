package com.whattoeat.fos.Food

import com.whattoeat.fos.Food.Controller.FoodController
import com.whattoeat.fos.Food.DTO.FoodDTO
import com.whattoeat.fos.Food.Domain.Repository.FoodRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class FoodControllerTest {
    @Autowired
    lateinit var foodRepository: FoodRepository

    @Test
    fun 개별_메뉴_탐색() {
        // given
        val categoryName: String = "음료/디저트"
        val id: Int = 1

        // when
        val dto: FoodDTO.FoodListResponse = FoodDTO.FoodListResponse(id = id, categoryName = categoryName, menuName = "아메리카노", image = null)

        // then
        assertThat(dto.id).isEqualTo(id)
        assertThat(dto.categoryName).isEqualTo(categoryName)
    }

    @Test
    fun 키워드_검색() {
        val keyword: String = "아"

        val list = foodRepository.findFoodsByNameContainsOrderByName(keyword)

        for (item in list){
            println("${item.category} 의 ${item.name}")
        }

    }
}