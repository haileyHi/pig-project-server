package com.whattoeat.fos.Security.config

import com.whattoeat.fos.Security.jwt.AuthConstants
import com.whattoeat.fos.Security.jwt.TokenUtils
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
        val token : String = TokenUtils.generateJwtToken(user)
        // 로그인 성공 시 response에 토큰 반환.
        response!!.addHeader(AuthConstants.AUTH_HEADER, "${AuthConstants.TOKEN_TYPE} $token")
    }
}