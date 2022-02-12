package com.whattoeat.fos.User.Domain.Entity

import lombok.AccessLevel
import lombok.Builder
import lombok.NoArgsConstructor
import lombok.RequiredArgsConstructor
import org.hibernate.annotations.DynamicUpdate
import java.sql.Timestamp
import java.util.*
import javax.persistence.*

@NoArgsConstructor
@RequiredArgsConstructor
@DynamicUpdate
@Entity
@Table(name = "user")
@Builder
class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Int = 0

    @Column(name = "nickname", nullable = false, length = 45)
    var nickname: String? = null

    @Column(name = "password", length= 60)
    var password: String

    @Column(name = "is_confirmed")
    var isConfirm: Boolean = false

    @Column(name ="add_date", nullable = true)
    var addDate: Timestamp = Timestamp(System.currentTimeMillis())

    constructor(nickname: String?, password: String) {
        this.nickname = nickname
        this.password = password
    }

    fun changePassword(password: String){
        this.password = password
        this.isConfirm = true
    }
}
