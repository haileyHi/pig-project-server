package com.whattoeat.fos.Vote.Controller

import com.whattoeat.fos.Util.Response
import com.whattoeat.fos.Vote.DTO.VoteDTO
import com.whattoeat.fos.Vote.Service.VoteService
import org.springframework.data.repository.query.Param
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@CrossOrigin("*")
@RestController
@RequestMapping("/order")
class VoteController(private val voteService: VoteService) {
    @PostMapping
    fun addOrders(httpRequest: HttpServletRequest, @RequestBody voteRequest: VoteDTO.AddVoteRequest): ResponseEntity<Response>{
        return voteService.addVote(httpRequest, voteRequest)
    }

    @GetMapping
    fun getOrders(@Param("nickname") nickname: String): ResponseEntity<Response>{
        return voteService.findVotesByNickname(nickname)
    }

    @GetMapping("/count")
    fun getOrderUserCount() : ResponseEntity<Response>{
        return voteService.findVotedUserCount()
    }

    @GetMapping("/top5")
    fun getOrderTop5() : ResponseEntity<Response>{
        return voteService.findTopRank()
    }
}