package com.whattoeat.fos.Util

import com.whattoeat.fos.Security.jwt.AuthConstants
import com.whattoeat.fos.Security.jwt.TokenUtils
import javax.servlet.http.HttpServletRequest

class UserIdExtraction {
    fun getNickname(servletRequest: HttpServletRequest): String?{
        val header: String = servletRequest.getHeader(AuthConstants.AUTH_HEADER) ?: return null
        val token: String? = TokenUtils.getTokenFromHeader(header)
        val claims = TokenUtils.getClaimsFromToken(token)
        val id = TokenUtils.getUserIdFromToken(token)
        println("id: >> $id")
        return id;
    }
}