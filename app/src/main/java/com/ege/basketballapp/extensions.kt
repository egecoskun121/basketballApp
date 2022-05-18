package com.ege.hayah

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ege.basketballapp.model.Resource
import com.ege.basketballapp.model.ServerResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Response


fun ViewGroup.inflater(layoutRes: Int): View =
    LayoutInflater.from(context).inflate(layoutRes, this, false)



suspend fun <T> getResponse(
    request: suspend () -> Response<ServerResponse<T>>,
    defaultErrorMessage: String
): Resource<T> {
    return try {
        val result = request.invoke()
        if (result.isSuccessful) {
            val response: ServerResponse<T>? = result.body()
            return Resource.success(response?.data)
        } else {
            val gson = Gson()
            val type = object : TypeToken<ServerResponse<String>?>() {}.type
            val errorResponse: ServerResponse<String> =
                gson.fromJson(result.errorBody()!!.charStream(), type)
            Resource.error(errorResponse.message, null)
        }
    } catch (e: Throwable) {
        e.printStackTrace()
        Resource.error(defaultErrorMessage, null)
    }
}

