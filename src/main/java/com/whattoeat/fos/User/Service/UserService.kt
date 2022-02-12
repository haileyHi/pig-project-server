package com.whattoeat.fos.User.Service

import com.whattoeat.fos.User.DTO.UserDTO
import com.whattoeat.fos.User.Domain.Entity.User
import com.whattoeat.fos.User.Domain.Repository.AnimalCountRepository
import com.whattoeat.fos.User.Domain.Repository.NicknameAdjRepository
import com.whattoeat.fos.User.Domain.Repository.NicknameNounRepository
import com.whattoeat.fos.User.Domain.Repository.UserRepository
import com.whattoeat.fos.Util.Response
import com.whattoeat.fos.Vote.DTO.VoteDTO
import com.whattoeat.fos.Vote.Service.VoteService
import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@RequiredArgsConstructor
@Service
open class UserService(private val userRepository: UserRepository) {
    @Autowired
    private lateinit var nicknameAdjRepository: NicknameAdjRepository

    @Autowired
    private lateinit var nicknameNounRepository: NicknameNounRepository

    @Autowired
    private lateinit var animalCountRepository: AnimalCountRepository

    @Autowired
    private lateinit var voteService: VoteService

    @Autowired
    private lateinit var passwordEncoder: BCryptPasswordEncoder

    @Transactional
    open fun createUser(): ResponseEntity<Response> {
        // 생성된 모든 아이디 수가 adj count * noun count 와 일치하면 error 리턴하기.
        var availableNickname = ""
        var flag: Boolean = false
        val nounCount = nicknameNounRepository.count().toInt()
        val adjCount = nicknameAdjRepository.count().toInt()

        var count = 1;
        while (!flag && count < (nounCount * adjCount)) { // when flag became true this loop will end.
            // 난수 발생으로 랜덤 닉네임 만들기.
            val animalRdmIdx = ((Math.random() * nounCount) + 1).toInt() // 1부터 명사 갯수 이내에서 난수 발생.
            // 해당 난수에서 동물 개수 몇개인지 확인하기.
            val checkAnimal = animalCountRepository.findAnimalCountById(animalRdmIdx)
            if (!checkAnimal.isPresent) { //해당 인덱스 동물이 없는 경우 다음 랜덤 인덱스로 찾아가기.(올 일이 없음)
                continue
            }

            val getAnimalCount = checkAnimal.get()
            if (checkAnimal.get().count == adjCount) { // 동물 이름 다 찼으면 다음 동물에 빈 자리 찾으러 간다.
                continue
            }

            // 가능한 동물의 이름 가져오기.
            val animalRdm = nicknameNounRepository.findNicknameNounById(animalRdmIdx as Int)
//            val adjIdx = 1
            val animalAdjRdmIdx = ((Math.random() * adjCount) + 1).toInt()
            // 랜덤 인덱스로 형용사 찾기.
            while (!flag) {
                val adjRdm = nicknameAdjRepository.findNicknameAdjById(animalAdjRdmIdx as Int)
                // 랜덤으로 생성된 닉네임
                val genNickname = adjRdm.get().adjective + " " + animalRdm.get().animal
                println("$count 체크 $genNickname")
                val optUser = userRepository.findUserByNickname(genNickname)
                if (!optUser.isPresent) { // 존재하면 다시 다음 인덱스로 찾아보기.
                    availableNickname = genNickname
                    flag = true
//                    getAnimalCount.count += 1
                    getAnimalCount.increaseCount()
                    break
                }
            }
            count++
        }
        if (count == (nounCount * adjCount)) {
            return Response.newResult(HttpStatus.SERVICE_UNAVAILABLE, "준비된 닉네임이 다 떨어졌어요! 조금만 기다려주세요.", null)
        }

        val user = User(nickname = availableNickname, password = passwordEncoder.encode("1234"))
        userRepository.save(user)


        val data: MutableMap<String, String> = mutableMapOf()
        data.put("nickname", user.nickname!!)
        data.put("default", "1234")
        return Response.newResult(HttpStatus.OK, "계정 생성이 완료되었습니다.", data as Object)
    }

    fun getUser(nickname: String): ResponseEntity<Response> {
//        val userId = TokenUtils.getTokenFromHeader(httpRequest.getHeader())
        val optUser = userRepository.findUserByNickname(nickname)
        if (!optUser.isPresent) {
            return Response.newResult(HttpStatus.BAD_REQUEST, "존재하지 않는 닉네임입니다.", null)
        }
        val user = optUser.get()
        println(passwordEncoder.encode("1234"))
        val order = arrayListOf<String>()
        // 남이나 나나 똑같이 가져오면 되는데 (비밀번호 빼고)
        val data = UserDTO.UserProfile(id = user.id!!, nickname = user.nickname!!, selection = order)
        return Response.newResult(HttpStatus.OK, "사용자 정보를 불러왔습니다.", data as Object)
    }

    fun getUserSelfProfile(nickname: String): ResponseEntity<Response> {
        val optUser = userRepository.findUserByNickname(nickname = nickname)
        if (!optUser.isPresent) {
            return Response.newResult(HttpStatus.BAD_REQUEST, "존재하지 않는 닉네임입니다.", null)
        }
        val user = optUser.get()
        // 선택 내역 가져오기.
        val order = arrayListOf<String>()
        // vote service에서 닉네임으로 투표 내역 가져오기.
        // 여기서 아이디 넘겨서 투표 내역 + 현재 몇 위인지 + 내용을 가져오자.
        val votes = voteService.findVoteResultsFromUserId(user.id)
        val data: UserDTO.UserProfileSelf = UserDTO.UserProfileSelf(
            id = user.id!!,
            nickname = user.nickname!!,
            password = user.password!!,
            selection = votes
        )

        return Response.newResult(HttpStatus.OK, "유저 개인 정보를 불러왔습니다.", data as Object)
    }

    @Transactional
    open fun setUserPassword(nickname: String, passwordRequest: UserDTO.RequestSetPassword): ResponseEntity<Response> {
        // get nickname out from
        val optUser = userRepository.findUserByNickname(nickname = nickname)
        if (!optUser.isPresent) {
            return Response.newResult(HttpStatus.BAD_REQUEST, "존재하지 않는 닉네임입니다.", null)
        }
        val user = optUser.get()
        // if 비밀번호 길이가 4자리가 안 될때
        if (passwordRequest.password.length != 4) {
            return Response.newResult(HttpStatus.BAD_REQUEST, "비밀번호 길이를 확인해주세요.", null)
        }
        // test password
        if (!passwordEncoder.matches("1234", user.password.toString())) {
            return Response.newResult(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다.", null)
        }

        user.changePassword(passwordEncoder.encode(passwordRequest.password))
        return Response.newResult(HttpStatus.OK, "비밀번호가 설정되었습니다.", null)
    }

}