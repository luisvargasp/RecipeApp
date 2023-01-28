package com.codechallenge.recipeapp.data.util

import retrofit2.Response

sealed class Result<out T : Any> {

    data class Success<out T : Any>(val data: T?) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()

    inline fun <reified T : Any> result(response: Response<*>, bodyCode: Int? = 0, exception: Exception = UnexpectedException(), success: () -> T?): Result<T> {
        return if (response.isSuccessful) {
            if (bodyCode != 0) {
                ErrorUtil.result(exception)
            } else {
                Success(success())
            }
        } else {
            ErrorUtil.result(response)
        }
    }

    inline fun <reified T : Any> result(response: Response<*>, bodyCode: Int? = 0, bodyMessage: String?, success: () -> T?): Result<T> {
        return result(response, bodyCode, UnexpectedException(bodyMessage), success)
    }
}