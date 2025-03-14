package com.phat.mytestcicd.data.local


interface UserLocalDataSource {
    suspend fun getCachedUser(id: Int): UserEntity?
    suspend fun cacheUser(user: UserEntity)
}

class UserLocalDataSourceImpl(private val database: AppDatabase) : UserLocalDataSource {

    override suspend fun getCachedUser(id: Int): UserEntity? {
        return database.userDao().getUser(id)
    }
    override suspend fun cacheUser(user: UserEntity) {
        database.userDao().insertUser(user)
    }
}