package com.whattoeat.fos.User.Domain.Repository

import com.whattoeat.fos.User.Domain.Entity.NicknameNoun
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface NicknameNounRepository : JpaRepository<NicknameNoun, Int> {
    fun findNicknameNounById(id : Int): Optional<NicknameNoun>
}