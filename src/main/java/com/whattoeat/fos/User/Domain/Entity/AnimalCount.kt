package com.whattoeat.fos.User.Domain.Entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "animal_count")
data class AnimalCount (
    @Id
    @Column(name = "animal_id", nullable = false)
    open var id: Int? = null,

    @Column(name="count")
    open var count: Int = 0
){
    fun increaseCount(){
        this.count += 1
    }
}