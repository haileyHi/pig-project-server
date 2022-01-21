package com.whattoeat.fos.Security.filter

import com.fasterxml.jackson.databind.ObjectMapper
import com.whattoeat.fos.Exception.InputNotFoundException
import com.whattoeat.fos.User.Domain.Entity.User
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.io.IOException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class CustomAuthenticationFilter(): UsernamePasswordAuthenticationFilter() {
    constructor(authenticationManager: AuthenticationManager?) : this() {
        super.setAuthenticationManager(authenticationManager)
    }

    @Throws(AuthenticationException::class)
    override fun attemptAuthentication(request: HttpServletRequest?, response: HttpServletResponse?): Authentication {
        val authRequest: UsernamePasswordAuthenticationToken
        try{
            val user: User = ObjectMapper().readValue(request!!.inputStream, User::class.java)
            authRequest = UsernamePasswordAuthenticationToken(user.nickname, user.password)
        } catch (e: IOException){
            throw InputNotFoundException()
        }
        setDetails(request, authRequest)
        return authenticationManager.authenticate(authRequest)
    }
}