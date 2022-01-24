package com.whattoeat.fos.Food.Domain.Repository

import com.whattoeat.fos.Food.Domain.Entity.Food
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface FoodRepository : JpaRepository<Food, Int>{
    fun findFoodsByNameContainsOrderByName(name: String): List<Food>
    fun findFoodsByCategory_TitleOrderByNameAsc(category: String): List<Food>
    fun findFoodById(id: Int): Optional<Food>

    @Query("select @rn:=@rn+1 as \"rank\", v.menu_id, f.name ,count(menu_id) as \"count\"\n" +
            "from vote v, food f, (SELECT @rn:=0) rn\n" +
            "where v.menu_id = f.id\n" +
            "group by menu_id\n" +
            "order by count(menu_id) desc\n" +
            "limit 5;", nativeQuery = true)
    fun findTopRank() : List<Array<Any>>
}