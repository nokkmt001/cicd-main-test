package com.phat.mytestcicd.domain

import com.phat.mytestcicd.data.remote.User
import com.phat.mytestcicd.data.repository.UserRepository

class GetUserUseCase(private val repository: UserRepository) {

    suspend operator fun invoke(id: Int): User {
        return repository.getUser(id)
    }
}
