package com.whattoeat.fos.Vote.Controller

import com.whattoeat.fos.Util.Response
import com.whattoeat.fos.Vote.DTO.VoteDTO
import com.whattoeat.fos.Vote.Service.VoteService
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

    @GetMapping("/{nickname}")
    fun getOrders(httpRequest: HttpServletRequest): ResponseEntity<Response>{
        return voteService.findVotesByNickname(request = httpRequest)
    }

    @GetMapping("/count")
    fun getOrderUserCount() : ResponseEntity<Response>{
        return voteService.findVotedUserCount();
    }
}