package com.whattoeat.fos.User.Domain.Entity

import javax.persistence.*

@Entity
@Table(name = "nickname_adj")
open class NicknameAdj {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    open var id: Int? = null

    @Column(name = "adjective", nullable = false, length = 45)
    open var adjective: String? = null
}