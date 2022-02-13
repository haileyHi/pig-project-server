package com.whattoeat.fos.Food.Domain.Repository

import com.whattoeat.fos.Food.Domain.Entity.Rank
import org.springframework.data.jpa.repository.JpaRepository

interface RankRepository : JpaRepository<Rank, Int> {
    fun findRanksByIdIsLessThanOrderById(id : Int): List<Rank>
}