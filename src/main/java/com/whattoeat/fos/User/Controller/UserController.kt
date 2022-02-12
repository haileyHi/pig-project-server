package com.whattoeat.fos.User.Controller

import com.whattoeat.fos.User.DTO.UserDTO
import com.whattoeat.fos.User.Service.UserService
import com.whattoeat.fos.Util.Response
import com.whattoeat.fos.Util.UserIdExtraction
import lombok.NoArgsConstructor
import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@CrossOrigin("*")
@NoArgsConstructor
@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
class UserController {
    @Autowired
    private lateinit var userService: UserService

    @PostMapping
    fun createUser(request: HttpServletRequest): ResponseEntity<Response>{
        return userService.createUser()
    }

    @GetMapping("/{nickname}")
    fun getUserInfo(request: HttpServletRequest, @PathVariable nickname: String): ResponseEntity<Response>{
        val userNicknameExtracted = UserIdExtraction().getNickname(request)
        if (userNicknameExtracted != null && userNicknameExtracted == nickname) {
            return userService.getUserSelfProfile(nickname)
        }
        else return userService.getUser(nickname)
    }

    @PutMapping("/password")
    fun updateUserPassword(request: HttpServletRequest, @RequestBody passwordRequest: UserDTO.RequestSetPassword) : ResponseEntity<Response> {
        val nickname = UserIdExtraction().getNickname(request)
            ?: return Response.newResult(HttpStatus.UNAUTHORIZED, "로그인 후 이용해주세요.", null)
        return userService.setUserPassword(nickname, passwordRequest)
    }
}
