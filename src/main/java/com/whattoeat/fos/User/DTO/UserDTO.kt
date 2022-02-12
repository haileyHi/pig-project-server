package com.whattoeat.fos.User.DTO

import com.fasterxml.jackson.annotation.JsonProperty
import com.whattoeat.fos.Vote.DTO.VoteDTO

class UserDTO {
    class UserProfile(
        val id: Int,
        val nickname: String,
        val selection: ArrayList<String>
    )

    class UserProfileSelf(
        val id: Int,
        val nickname: String,
        val password: String,
        val selection: ArrayList<VoteDTO.VoteMenuResult>?
    )

    class RequestSetPassword (
        @JsonProperty("password")
        val password: String
    )

}