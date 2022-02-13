package com.whattoeat.fos.Exception

class UserNotFoundException(nickname: String) : RuntimeException("$nickname NotFoundException")