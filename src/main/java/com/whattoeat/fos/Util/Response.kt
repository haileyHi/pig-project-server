package com.whattoeat.fos.Util

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

data class Response(val status : Int, val message: String, val data: Object?){
    companion object {
        fun newResult(status: HttpStatus, message: String?, result: Object?): ResponseEntity<Response> {
            return if (result != null)
                ResponseEntity(Response(status.value(), message!!, result), status)
            else
                ResponseEntity(Response(status.value(), message!!, null),status)
        }
    }
}