package com.phat.mytestcicd.data.remote

import com.phat.mytestcicd.data.local.UserEntity

data class User(
    val id: Int,
    val name: String,
    val email: String
)

data class UserRemote(
    val id: Int,
    val name: String,
    val email: String
)

fun UserRemote.toEntity(): UserEntity {
    return UserEntity(
        id = this.id,
        name = this.name,
        email = this.email
    )
}

fun UserRemote.toUser(): User {
    return User(
        id = this.id,
        name = this.name,
        email = this.email
    )
}

fun UserEntity.toDomain(): User {
    return User(
        id = id,
        name = name,
        email = email
    )
}

fun User.toEntity(): UserEntity {
    return UserEntity(
        id = this.id,
        name = this.name,
        email = this.email
    )
}