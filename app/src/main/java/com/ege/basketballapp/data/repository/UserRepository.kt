package com.ege.basketballapp.data.repository

import com.ege.basketballapp.data.dataSource.UserRemoteDataSource
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource
) {

}