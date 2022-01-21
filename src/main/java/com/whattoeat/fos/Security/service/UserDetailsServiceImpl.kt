package com.whattoeat.fos.Security.service

import com.whattoeat.fos.Exception.InputNotFoundException
import com.whattoeat.fos.User.Domain.Entity.User
import com.whattoeat.fos.User.Domain.Repository.UserRepository
import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.util.*

@Service
@RequiredArgsConstructor
class UserDetailsServiceImpl: UserDetailsService {
    @Autowired
    private lateinit var userRepository: UserRepository
    // 아이디 기반으로 데이터 조회하기.
    override fun loadUserByUsername(username: String?): UserDetails {
        if (username != null) {
            val optUser: Optional<User> = userRepository.findUserByNickname(username)
            if (!optUser.isPresent) {
                throw UsernameNotFoundException("User with nickname : $username doesn't exist.")
            }
            val user: User = optUser.get()
//            val authorities: List<GrantedAuthority> = mutableListOf()

            return UserDetailsImpl(user)//, authorities)
        }
        else throw InputNotFoundException()
    }
}