package com.whattoeat.fos.Security.config

import com.whattoeat.fos.Security.filter.HeaderFilter
import com.whattoeat.fos.Security.jwt.JwtTokenInterceptor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

class WebMvcConfig: WebMvcConfigurer {
    @Autowired
    private lateinit var jwtTokenInterceptor: JwtTokenInterceptor

    @Autowired
    private lateinit var headerFilter: HeaderFilter

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(jwtTokenInterceptor)
            .addPathPatterns("/user")
    }

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedOrigins("*") // 허용할 서버 주소 넣기. (정해지면 그 때 넣자)
            .allowedMethods("*") //
            .allowCredentials(false) // 크레덴셜 안 쓰기로 함.
            .maxAge(3600) // preflight 결과를 캐시에 저장.
    }

    @Bean
    fun getFilterRegistrationBean() : FilterRegistrationBean<HeaderFilter>{
        val registrationBean: FilterRegistrationBean<HeaderFilter> = FilterRegistrationBean(headerFilter)
        registrationBean.setOrder(Integer.MIN_VALUE)
        registrationBean.addUrlPatterns("/")
        return registrationBean
    }

//    @Bean
//    fun createHeaderFilter() : HeaderFilter = HeaderFilter()

//    @Bean
//    fun jwtTokenInterceptor() : JwtTokenInterceptor = JwtTokenInterceptor()
}