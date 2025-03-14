package com.phat.mytestcicd.presentation.viewmodel

import com.phat.mytestcicd.data.remote.User
import com.phat.mytestcicd.domain.GetUserUseCase
import com.phat.mytestcicd.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class UserViewModel(private val getUserUseCase: GetUserUseCase): BaseViewModel() {

    private val _user: MutableStateFlow<User?> = MutableStateFlow(null)
    val user: StateFlow<User?> = _user

    init {
        loadUser(1)
    }

    fun loadUser(userId: Int) {
        customScope.launchSafeIO {
            val data = getUserUseCase.invoke(userId)
            _user.update { data }
        }
    }
}
