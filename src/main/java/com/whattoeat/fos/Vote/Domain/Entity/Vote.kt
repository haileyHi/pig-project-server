package com.whattoeat.fos.Vote.Domain.Entity

import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import java.sql.Timestamp
import javax.persistence.*

@Entity
@Table(
    name = "vote", indexes = [
        Index(name = "fk_vote_menu_id_idx", columnList = "menu_id")
    ]
)
@DynamicUpdate
@DynamicInsert
class Vote(
    @EmbeddedId
    open var id: VoteId? = null
){
    @Column(name = "add_date")
    var addDate: Timestamp? = null
}