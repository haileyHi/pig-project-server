package com.whattoeat.fos.User.Domain.Repository

import com.whattoeat.fos.User.Domain.Entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository: JpaRepository<User, Int> {
    fun findUserByNickname(nickname : String): Optional<User>

    fun findUserByNicknameAndPassword(nickname: String, password: String): Optional<User>
}