package com.whattoeat.fos.Vote.Service

import com.whattoeat.fos.Food.Domain.Repository.FoodRepository
import com.whattoeat.fos.User.Domain.Repository.UserRepository
import com.whattoeat.fos.Util.Response
import com.whattoeat.fos.Util.UserIdExtraction
import com.whattoeat.fos.Vote.DTO.VoteDTO
import com.whattoeat.fos.Vote.Domain.Entity.Vote
import com.whattoeat.fos.Vote.Domain.Entity.VoteId
import com.whattoeat.fos.Vote.Domain.Repository.VoteRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import javax.servlet.http.HttpServletRequest

@Service
class VoteService(private val voteRepository: VoteRepository) {
    @Autowired
    private lateinit var userRepository: UserRepository
    @Autowired
    private lateinit var foodRepository: FoodRepository

    fun addVote(request: HttpServletRequest, voteRequest: VoteDTO.AddVoteRequest) : ResponseEntity<Response>{
        // 토큰으로 아이디 추출하는 거 만들기.
        val userNickname = UserIdExtraction().getNickname(request)
            ?: return Response.newResult(HttpStatus.BAD_REQUEST, "닉네임 생성 후 이용해주세요.", null)
        val optUser = userRepository.findUserByNickname(userNickname)
        if(!optUser.isPresent) return Response.newResult(HttpStatus.UNAUTHORIZED, "정상적인 방법으로 닉네임을 생성 후 이용해주세요.", null)
        if (voteRequest.list.size == 0) {
            return Response.newResult(HttpStatus.BAD_REQUEST, "메뉴를 선택해주세요.", null)
        }
        val getUser = optUser.get()
        for (item in voteRequest.list){
            val voteId = VoteId(userId = getUser.id, menuId = item)
            voteRepository.save(Vote(voteId))
        }
        return Response.newResult(HttpStatus.OK, "메뉴 선택이 완료되었습니다.", null)
    }

    fun findVotesByNickname(nickname: String/*request: HttpServletRequest*/): ResponseEntity<Response> {
//        val nickname = UserIdExtraction().getNickname(request)
//            ?: return Response.newResult(HttpStatus.BAD_REQUEST, "로그인 후 다시 이용해주세요.", null)
        val optUser = userRepository.findUserByNickname(nickname)
        if(!optUser.isPresent) {
            return Response.newResult(HttpStatus.BAD_REQUEST, "존재하지 않는 닉네임입니다.", null)
        }
        val user = optUser.get()
        val voteResult = findVoteResultsFromUserId(user.id)
        if (voteResult == null){
            return Response.newResult(HttpStatus.NO_CONTENT, "투표 내역이 없습니다.", null)
        }
        /*val data = voteRepository.findVotesByIdUserId(getUser.id)
        if(data.size == 0){ // 204
            return Response.newResult(HttpStatus.NO_CONTENT, "투표 내역이 없습니다.", null)
        }
        // 5위까지 랭크 구해서 그 중에 속하면 아래 랭크로
        val topRankList: ArrayList<VoteDTO.RankResult> = voteRepository.findTop5RankGroupByMenuId()
        val rankMap = HashMap<Int, Int>()
        for (item in topRankList) {
            rankMap[item.menuId as Int] = item.count as Int
        }
        val voteResult = arrayListOf<VoteDTO.VoteMenuResult>()
        for (item in data) {
            val optMenu = foodRepository.findFoodById(item.id?.menuId!!)
            if(!optMenu.isPresent) continue  // 해당 메뉴가 존재하지 않는 경우 넘어간다,
            val menu = optMenu.get()
            // vote 총 득표 수
            val voteCnt = voteRepository.countAllByIdMenuId(menu.id)

            // 랭킹 top 5 리스트 가져올 repository 함수 생성하기. 해당 리스트 에 menuID가 있으면 rank에 등수를 표시. 없으면 null로 전달
            voteResult.add(VoteDTO.VoteMenuResult(
                menuId = menu.id,
                menuTitle = menu.name,
                categoryId = menu.category?.id!!,
                categoryName = menu.category?.title!!,
                count = Integer.valueOf(voteCnt.toString()),
                rank = rankMap.getOrDefault(menu.id, -1)
            ))
        }*/
        return Response.newResult(HttpStatus.OK, "목표로 설정한 음식 리스트를 불러왔습니다.", voteResult as Object)
    }

    fun findVoteResultsFromUserId(userId : Int) : ArrayList<VoteDTO.VoteMenuResult>?{
        val top5Ranks: ArrayList<VoteDTO.RankResult> = voteRepository.findTop5RankGroupByMenuId()
        val data = voteRepository.findVotesByIdUserId(userId)
        if (data.size == 0) {
            return null
        }
        // 5위까지 랭크 구해서 그 중에 속하면 아래 랭크로
        val topRankList: ArrayList<VoteDTO.RankResult> = voteRepository.findTop5RankGroupByMenuId()
        val rankMap = HashMap<Int, Int>()
        for (item in topRankList) {
            rankMap.put(item.menu_id, item.count as Int)
        }
        val voteResult = arrayListOf<VoteDTO.VoteMenuResult>()
        for (item in data) {
            val optMenu = foodRepository.findFoodById(item.id?.menuId!!)
            if(!optMenu.isPresent) continue  // 해당 메뉴가 존재하지 않는 경우 넘어간다,
            val menu = optMenu.get()
            // vote 총 득표 수
            val voteCnt = voteRepository.countAllByIdMenuId(menu.id)
            println("메뉴 아이디 : ${menu.id}")
            // 랭킹 top 5 리스트 가져올 repository 함수 생성하기. 해당 리스트 에 menuID가 있으면 rank에 등수를 표시. 없으면 null로 전달
            voteResult.add(VoteDTO.VoteMenuResult(
                menuId = menu.id,
                menuTitle = menu.name,
                categoryId = menu.category?.id!!,
                categoryName = menu.category?.title!!,
                count = Integer.valueOf(voteCnt.toString()),
                rank = rankMap.getOrDefault(menu.id, -1)
            ))
        }
        return voteResult
    }

    fun findVotesByMenuId(menuId: Int): ResponseEntity<Response> {
        TODO()
    }

    fun findVotedUserCount() : ResponseEntity<Response> {
        val userCount = voteRepository.countByDistinctUserId()
        if (userCount >= 0){
            return Response.newResult(HttpStatus.OK, "현재 $userCount 명이 이용하고 있어요.", userCount as Object)
        }
        return Response.newResult(HttpStatus.BAD_REQUEST, "잘못된 요청입니다.", null)
    }

    fun findTopRank() : ResponseEntity<Response> {
        val rank = voteRepository.findTop5RankGroupByMenuId()
        val rankMap = HashMap<Int, Int>()
        val voteResult = arrayListOf<VoteDTO.VoteMenuResult>()
        var idx = 1
        for (item in rank) {
            val optMenu = foodRepository.findFoodById(item.menu_id)
            if(!optMenu.isPresent) continue  // 해당 메뉴가 존재하지 않는 경우 넘어간다,
            val menu = optMenu.get()
            // vote 총 득표 수
            val voteCnt = voteRepository.countAllByIdMenuId(menu.id)

            // 랭킹 top 5 리스트 가져올 repository 함수 생성하기. 해당 리스트 에 menuID가 있으면 rank에 등수를 표시. 없으면 null로 전달
            voteResult.add(VoteDTO.VoteMenuResult(
                menuId = menu.id,
                menuTitle = menu.name,
                categoryId = menu.category?.id!!,
                categoryName = menu.category?.title!!,
                count = Integer.valueOf(voteCnt.toString()),
                rank = idx++
            ))
        }

        return Response.newResult(HttpStatus.OK, "가장 많이 뽑힌 5가지 음식을 불러왔어요.", voteResult as Object)
    }
}