package com.ege.basketballapp.data.dataSource

import com.ege.basketballapp.data.api.UserApi
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(
    private val userApi: UserApi
) {


}