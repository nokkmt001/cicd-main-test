package com.phat.mytestcicd.presentation.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.phat.mytestcicd.presentation.ex.reflectViewBinding

abstract class BaseActivity<T: ViewBinding?>: AppCompatActivity() {

    private var _binding: T? = null
    protected val binding get() = _binding?: error("")

    protected var userLayoutID: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (userLayoutID) {
          try {
              _binding = reflectViewBinding()
              setContentView(binding.root)
          } catch (e: Exception) {

          }
        }

        initViews()
        initViewModel()

    }

    protected open fun initViewModel() {

    }

    protected open fun initViews() {

    }
}