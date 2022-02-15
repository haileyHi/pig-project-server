package com.whattoeat.fos.Food.Domain.Repository

import com.whattoeat.fos.Food.DTO.FoodDTO
import com.whattoeat.fos.Food.Domain.Entity.Food
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*


interface FoodRepository : JpaRepository<Food, Int> {
    fun findAll(spec: Specification<Food>, pageable: Pageable?): Page<Food>
    fun findFoodsByNameContainsOrderByName(name: String): List<Food>

    //fun findFoodsByCategory_TitleOrderByNameAsc(category: String): List<Food>
    fun findFoodById(id: Int): Optional<Food>

    @Query(
        "select @rn\\:=@rn+1 as \"rank\", v.menu_id as \"menuId\", f.name as \"menuName\" ,count(menu_id) as \"count\" " +
                "from vote v, food f, (SELECT @rn\\:=0) rn " +
                "where v.menu_id = f.id " +
                "group by menu_id " +
                "order by count(menu_id) desc " +
                "limit 5", nativeQuery = true
    )
    fun findTopRank(): List<FoodDTO.FoodRank>
}