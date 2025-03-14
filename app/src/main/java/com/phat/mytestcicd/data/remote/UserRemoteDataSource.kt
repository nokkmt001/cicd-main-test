package com.phat.mytestcicd.data.remote

import com.phat.mytestcicd.data.Api

interface UserRemoteDataSource {
    suspend fun getUser(id: Int): UserRemote
}

class UserRemoteDataSourceImpl(private val apiService: Api) : UserRemoteDataSource {

    override suspend fun getUser(id: Int): UserRemote {
//        return apiService.fetchUser()
        return UserRemote(id, "p", "p@gmail.com")
    }
}