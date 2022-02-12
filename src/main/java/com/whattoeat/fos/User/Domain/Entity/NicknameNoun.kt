package com.whattoeat.fos.User.Domain.Entity

import javax.persistence.*

@Entity
@Table(name = "nickname_noun")
open class NicknameNoun {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    open var id: Int? = null

    @Column(name = "animal", nullable = false, length = 45)
    open var animal: String? = null
}