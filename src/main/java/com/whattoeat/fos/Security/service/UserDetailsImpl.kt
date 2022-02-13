package com.whattoeat.fos.Security.service

import com.whattoeat.fos.User.Domain.Entity.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserDetailsImpl(val user: User): UserDetails {

    private val authorities: Collection<GrantedAuthority?>? = null
    override fun getAuthorities(): Collection<GrantedAuthority?>? {
        return authorities
    }

    override fun getPassword(): String = user.password

    override fun getUsername(): String? = user.nickname

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true
}