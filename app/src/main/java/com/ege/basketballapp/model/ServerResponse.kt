package com.ege.basketballapp.model

data class ServerResponse<T>(val success: Boolean, val data: T, val message: String)