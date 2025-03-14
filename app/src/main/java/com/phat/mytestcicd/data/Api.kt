package com.phat.mytestcicd.data

import com.phat.mytestcicd.data.remote.UserRemote

interface Api {

    suspend fun fetchUser(): UserRemote

}