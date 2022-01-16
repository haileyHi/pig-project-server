package com.whattoeat.fos.Food.Domain.Repository

import com.whattoeat.fos.Food.Domain.Entity.Food
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface FoodRepository : JpaRepository<Food, Int>{
    fun findFoodsByNameContainsOrderByName(name: String): List<Food>
    fun findFoodsByCategory_TitleOrderByNameAsc(category: String): List<Food>
    fun findFoodById(id: Int): Optional<Food>
}