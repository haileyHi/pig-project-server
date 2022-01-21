package com.whattoeat.fos.Security.jwt

import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtTokenInterceptor: HandlerInterceptor {
    override fun preHandle (
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any): Boolean {
        val header = request.getHeader(AuthConstants.AUTH_HEADER)

        if (header != null) {
            val token = TokenUtils.getTokenFromHeader(header)
            if (TokenUtils.isValidToken(token)) {
                return true
            }
        }
        response.sendRedirect("/error/unauthorized")
        return false
    }
}