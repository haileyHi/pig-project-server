package com.whattoeat.fos.User.Domain.Repository

import com.whattoeat.fos.User.Domain.Entity.AnimalCount
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface AnimalCountRepository : JpaRepository<AnimalCount, Int> {
    fun findAnimalCountById(id : Int) : Optional<AnimalCount>
}