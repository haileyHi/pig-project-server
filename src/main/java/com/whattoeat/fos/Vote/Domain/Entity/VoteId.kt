package com.whattoeat.fos.Vote.Domain.Entity

import lombok.Builder
import lombok.EqualsAndHashCode
import lombok.Getter
import lombok.NoArgsConstructor
import org.hibernate.Hibernate
import java.io.Serializable
import java.util.*
import javax.persistence.Column
import javax.persistence.Embeddable


@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Embeddable
@NoArgsConstructor
open class VoteId(
    @EqualsAndHashCode.Include
    @Column(name = "user_id", nullable = false)
    open var userId: Int? = null,

    @EqualsAndHashCode.Include
    @Column(name = "menu_id", nullable = false)
    open var menuId: Int? = null) : Serializable {

    companion object {
        private const val serialVersionUID = 5936516080869359014L
    }
}