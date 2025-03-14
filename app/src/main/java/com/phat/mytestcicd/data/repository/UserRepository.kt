package com.phat.mytestcicd.data.repository

import com.phat.mytestcicd.data.local.UserLocalDataSource
import com.phat.mytestcicd.data.remote.User
import com.phat.mytestcicd.data.remote.UserRemote
import com.phat.mytestcicd.data.remote.UserRemoteDataSource
import com.phat.mytestcicd.data.remote.toDomain
import com.phat.mytestcicd.data.remote.toEntity
import com.phat.mytestcicd.data.remote.toUser

interface UserRepository {
    suspend fun getUser(id: Int): User
    suspend fun saveUser(user: UserRemote)
}

class UserRepositoryImpl(
    private val remoteDataSource: UserRemoteDataSource,
    private val localDataSource: UserLocalDataSource
) : UserRepository {
    override suspend fun getUser(id: Int): User {
        val localUser = localDataSource.getCachedUser(id)
        return if (localUser != null) {
            localUser.toDomain()
        } else {
            val remoteUser = remoteDataSource.getUser(id)
            localDataSource.cacheUser(remoteUser.toEntity())
            remoteUser.toUser()
        }
    }

    override suspend fun saveUser(user: UserRemote) {
        localDataSource.cacheUser(user.toEntity())
    }
}
