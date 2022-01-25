package com.whattoeat.fos.Vote.Domain.Repository

import com.whattoeat.fos.Vote.DTO.VoteDTO
import com.whattoeat.fos.Vote.Domain.Entity.Vote
import com.whattoeat.fos.Vote.Domain.Entity.VoteId
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface VoteRepository : JpaRepository<Vote, VoteId> {
//    fun findVotesWithFilter(spec: Specification<Vote>, pageable: Pageable): Page<Vote>
    fun findVotesByIdUserId(id: Int): ArrayList<Vote>

    fun countAllByIdMenuId(id: Int): Long

//    fun findVotesByIdMenuId(id: Int): ArrayList<Vote>
//
    @Query(value = "select v.menu_id, f.name ,count(menu_id)" +
                    "from vote v, food f" +
                    "where v.menu_id = f.id" +
                    "group by v.menu_id" +
                    "order by count(menu_id) desc" +
                    "limit 5", nativeQuery = true)
    fun findTop5RankGroupByMenuId(): ArrayList<VoteDTO.RankResult>

    @Query(value = "select count(distinct user_id)\n" +
            "from vote", nativeQuery = true)
    fun countByDistinctUserId() : Long
}