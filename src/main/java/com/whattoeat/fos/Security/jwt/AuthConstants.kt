package com.whattoeat.fos.Security.jwt

import lombok.AccessLevel
import lombok.NoArgsConstructor

@NoArgsConstructor(access = AccessLevel.PRIVATE)
object AuthConstants {
    const val AUTH_HEADER = "Authorization"
    const val TOKEN_TYPE = "Bearer"
}