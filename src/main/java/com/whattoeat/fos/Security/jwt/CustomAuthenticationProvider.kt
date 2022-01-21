package com.whattoeat.fos.Security.jwt

import com.whattoeat.fos.User.Service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

class CustomAuthenticationProvider: AuthenticationProvider {
    @Autowired
    lateinit var userDetailsService: UserDetailsService
    @Autowired
    lateinit var bCryptPasswordEncoder: BCryptPasswordEncoder

    override fun authenticate(authentication: Authentication?): Authentication {
        val token: UsernamePasswordAuthenticationToken = authentication as UsernamePasswordAuthenticationToken
        // AuthenticationFilter로 생성된 토큰 확인하기.
        val nickname: String = token.name
        val password: String = token.credentials as String
        val userDetails: UserDetails
        userDetails = userDetailsService.loadUserByUsername(nickname)
        // UserDetailsService를 이용해 DB에서 아이디로 사용자 조회하기.
        if(!bCryptPasswordEncoder.matches(password, userDetails.password)){
            throw BadCredentialsException("${userDetails.username} Invalid password")
        }
        return UsernamePasswordAuthenticationToken(userDetails, password, userDetails.authorities)
    }

    override fun supports(authentication: Class<*>?): Boolean {
        if (authentication != null) {
            return authentication == UsernamePasswordAuthenticationToken::class
        }
        return false
    }
}