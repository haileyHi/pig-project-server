package com.whattoeat.fos.Vote.Controller

import com.whattoeat.fos.Util.Response
import com.whattoeat.fos.Util.UserIdExtraction
import com.whattoeat.fos.Vote.Service.VoteService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/order")
class VoteController(private val voteService: VoteService) {
    @PostMapping
    fun addOrders(httpRequest: HttpServletRequest, @RequestBody list: ArrayList<Int>): ResponseEntity<Response>{
        return voteService.addVote(httpRequest, list)
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