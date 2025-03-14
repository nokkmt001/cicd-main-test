package com.phat.mytestcicd.presentation.base

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    private val customJob = SupervisorJob()
    protected val customScope = CoroutineScope(Dispatchers.IO + customJob)

    protected fun launchSafe(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch {
            try {
                block()
            } catch (e: Exception) {
                Log.e("BaseViewModel", "Error in viewModelScope coroutine", e)
            }
        }
    }

    protected fun CoroutineScope.launchSafe(
        dispatcher: CoroutineDispatcher = Dispatchers.Main,
        block: suspend CoroutineScope.() -> Unit
    ) {
        this.launch(dispatcher) {
            try {
                block()
            } catch (e: Exception) {
                Log.e("BaseViewModel", "Error in coroutine", e)
            }
        }
    }

    protected fun CoroutineScope.launchSafeIO(
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        block: suspend CoroutineScope.() -> Unit
    ) {
        this.launch(dispatcher) {
            try {
                block()
            } catch (e: Exception) {
                Log.e("BaseViewModel", "Error in coroutine", e)
            }
        }
    }

    protected fun launchSafeInCustomScope(
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        block: suspend CoroutineScope.() -> Unit
    ) {
        customScope.launch(dispatcher) {
            try {
                block()
            } catch (e: Exception) {
                Log.e("BaseViewModel", "Error in customScope coroutine", e)
            }
        }
    }

    /**
     * Launch a coroutine on the Main dispatcher.
     * Use this for UI-related operations.
     */
    protected fun launchOnMain(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                block()
            } catch (e: Exception) {
                Log.e("BaseViewModel", "Error in Main dispatcher coroutine", e)
            }
        }
    }

    /**
     * Launch a coroutine on the Default dispatcher.
     * Use this for CPU-intensive tasks.
     */
    protected fun launchOnDefault(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch(Dispatchers.Default) {
            try {
                block()
            } catch (e: Exception) {
                Log.e("BaseViewModel", "Error in Default dispatcher coroutine", e)
            }
        }
    }

    /**
     * Launch a coroutine on the IO dispatcher.
     * Use this for network or disk I/O tasks.
     */
    protected fun launchOnIO(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                block()
            } catch (e: Exception) {
                Log.e("BaseViewModel", "Error in IO dispatcher coroutine", e)
            }
        }
    }

    /**
     * Launch a coroutine on the Unconfined dispatcher.
     * Use this when you want the coroutine to start immediately in the current call-frame.
     */
    protected fun launchOnUnconfined(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch(Dispatchers.Unconfined) {
            try {
                block()
            } catch (e: Exception) {
                Log.e("BaseViewModel", "Error in Unconfined dispatcher coroutine", e)
            }
        }
    }

    protected fun <T> MutableStateFlow<T>.update(transform: (T) -> T) {
        this.value = transform(this.value)
    }

    override fun onCleared() {
        super.onCleared()
        customJob.cancel()
    }
}

/**
 * Extension function for MutableStateFlow that allows updating the value
 * based on a transformation function.
 */
fun <T> MutableStateFlow<T>.update(transform: (T) -> T) {
    this.value = transform(this.value)
}

class EmptyViewModel : ViewModel()
