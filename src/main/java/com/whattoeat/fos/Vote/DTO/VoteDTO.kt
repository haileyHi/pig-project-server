package com.whattoeat.fos.Vote.DTO

import com.fasterxml.jackson.annotation.JsonProperty
import lombok.*

class VoteDTO {
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    class AddVoteRequest(
        @JsonProperty("list")
        val list: ArrayList<Int>
    )

    class VoteMenuResult(
        val menuId: Int,
        val menuTitle: String,
        val categoryId: Int,
        val categoryName: String,
        val count: Int,
        val rank: Int
    )

    interface RankResult {
        val menu_id: Int
        val name: String
        val count: Integer
    }
}