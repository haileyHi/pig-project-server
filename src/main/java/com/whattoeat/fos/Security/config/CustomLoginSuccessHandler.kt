package com.whattoeat.fos.Security.config

import com.whattoeat.fos.Security.service.UserDetailsImpl
import com.whattoeat.fos.User.Domain.Entity.User
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class CustomLoginSuccessHandler: SavedRequestAwareAuthenticationSuccessHandler(){
    override fun onAuthenticationSuccess(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authentication: Authentication?
    ) {
        val user: User = ((authentication?.principal) as UserDetailsImpl).user
        super.onAuthenticationSuccess(request, response, authentication)
    }
}