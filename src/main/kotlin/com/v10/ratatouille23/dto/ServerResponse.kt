package com.v10.ratatouille23.dto

class ServerResponse<T>(
    val data: T,
    val message: String
) {
    companion object {
        private const val success: String = "Success!"
        private const val failed: String = "Failed"

        fun <T> ok(data: T): ServerResponse<T>{
            return ServerResponse(data, success)
        }

        fun <T> failed(data: T): ServerResponse<T>{
            return ServerResponse(data, failed)
        }
    }
}