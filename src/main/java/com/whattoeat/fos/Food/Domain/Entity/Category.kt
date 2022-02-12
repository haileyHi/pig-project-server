package com.whattoeat.fos.Food.Domain.Entity

import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
import javax.persistence.*

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "category")
@Data
open class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    open var id: Int? = null

    @Column(name = "title", nullable = false, length = 45)
    open var title: String? = null
}