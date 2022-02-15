package com.whattoeat.fos.Food.Domain.Entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "rank")
class Rank (
    @Id
    @Column(name = "id", nullable = false)
    var id: Int,

    @Column(name = "menu_id")
    var menuId: Int,

    @Column(name = "prev_rank")
    var prevRank: Int?
)