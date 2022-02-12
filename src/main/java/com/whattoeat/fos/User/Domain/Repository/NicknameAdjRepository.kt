package com.whattoeat.fos.User.Domain.Repository

import com.whattoeat.fos.User.Domain.Entity.NicknameAdj
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface NicknameAdjRepository : JpaRepository<NicknameAdj, Int> {
    fun findNicknameAdjById(id: Int): Optional<NicknameAdj>

}