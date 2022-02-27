package com.whattoeat.fos.Security.filter

import java.io.IOException
import javax.servlet.*
import javax.servlet.http.HttpServletResponse

class HeaderFilter : Filter {
    @Throws(IOException::class, ServletException::class)
    override fun doFilter(request: ServletRequest?, response: ServletResponse, chain: FilterChain) {
        val res: HttpServletResponse = response as HttpServletResponse
        res.addHeader("Access-Control-Allow-Origin", "*")
        res.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS")
        res.setHeader("Access-Control-Max-Age", "3600")
        res.setHeader(
            "Access-Control-Allow-Headers",
            "X-Requested-With, Content-Type, Authorization, X-XSRF-token"
        )
        res.setHeader("Access-Control-Expose-Headers", "Authorization")
        res.setHeader("Access-Control-Allow-Credentials", "true")
        chain.doFilter(request, response)
    }
}
