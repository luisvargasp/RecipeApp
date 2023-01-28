package com.codechallenge.recipeapp.data.util

import android.content.res.Resources.NotFoundException
import com.codechallenge.recipeapp.data.model.StatusDataModel
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

object ErrorUtil {

    fun <T : Any> result(response: Response<*>): Result<T> {
        return Result.Error(handle(response.errorBody(), response.code()))
    }

    fun <T : Any> result(e: Throwable): Result<T> {
        return Result.Error(handle(e))
    }


    private fun handle(responseBody: ResponseBody?, statusCode: Int): Exception {
        val bodyJson = responseBody?.string()
        val errorResponse = try {
            Gson().fromJson(bodyJson, StatusDataModel::class.java)
        } catch (e: Exception) {
            throw e
        }

        return when (statusCode) {
            400->{
                BadRequestException(errorResponse.message)

            }
            401  -> {
                UnauthorizedException(errorResponse.message)
            }
            403 -> {
                ForbiddenException(errorResponse.message)
            }
            404 -> {
                NotFoundException(errorResponse.message)
            }
            else -> {
                UnexpectedException(errorResponse.message)
            }
        }
    }

    private fun handle(e: Throwable): Exception = when (e) {
        is SocketTimeoutException -> {
            com.codechallenge.recipeapp.data.util.SocketTimeoutException()
        }
        is ConnectException -> {
            com.codechallenge.recipeapp.data.util.ConnectException()
        }
        is HttpException -> {
            com.codechallenge.recipeapp.data.util.HttpsException()
        }
        is UnknownHostException -> {
            com.codechallenge.recipeapp.data.util.UnknownHostException()
        }
        else -> {
            e as java.lang.Exception
        }
    }
    suspend fun <T : Any> handleApiService(invoker: suspend Result<T>.() ->Result<T>): Result<T> {
        return try {
            withContext(Dispatchers.IO) {
                invoker(Result.Success(null))
            }
        } catch (e: Throwable) {
            ErrorUtil.result(e)
        }
    }
}

