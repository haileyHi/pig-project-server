package com.whattoeat.fos.Security.config

import com.whattoeat.fos.Security.filter.CustomAuthenticationFilter
import org.springframework.boot.autoconfigure.security.servlet.PathRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
open class SecurityConfig : WebSecurityConfigurerAdapter() {
    override fun configure(web: WebSecurity) {
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    override fun configure(http: HttpSecurity) {
        http.csrf().disable().cors().and()
            .authorizeRequests()
            .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
            .anyRequest().permitAll()
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .formLogin()
            .disable()
            .addFilterBefore(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter::class.java)
    }

    @Bean
    open fun bCryptPassEncoder() : BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Throws(Exception::class)
    @Bean
    open fun customAuthenticationFilter() : CustomAuthenticationFilter {
        val customAuthenticationFilter = CustomAuthenticationFilter(authenticationManager())
        customAuthenticationFilter.setFilterProcessesUrl("/user/login") // 이 url에서 만든 필터로 처리할 것이다.
        customAuthenticationFilter.setAuthenticationSuccessHandler(customLoginSuccessHandler())
        customAuthenticationFilter.afterPropertiesSet()
        return customAuthenticationFilter
    }

    @Bean
    open fun customLoginSuccessHandler() : CustomLoginSuccessHandler = CustomLoginSuccessHandler()
}
