package com.whattoeat.fos.Food.Domain.Repository

import com.whattoeat.fos.Food.Domain.Entity.Food
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

interface FoodRepository : JpaRepository<Food, Int>{
    fun findFoodsByNameContainsOrderByName(name: String): List<Food>
    fun findFoodsByCategoryOrderByNameAsc(category: String): List<Food>
    fun findFoodById(id: Int): Optional<Food>
}
/*
interface BoardRepository : JpaRepository<Board?, Int?> {
    fun findBoardById(id: Int): Optional<Board?>?

    @Query(value = "SELECT * FROM qna order by add_date desc", nativeQuery = true)
    fun findAllQuestions(pageable: Pageable?): List<Board?>?
    fun countAllByTitleIsNotNull(): Long
    fun countBoardsByTitleContains(keyword: String?): Long
    fun findBoardsByTitleContainsOrderByAddDate(keyword: String?, pageable: Pageable?): List<Board?>?
}*/
