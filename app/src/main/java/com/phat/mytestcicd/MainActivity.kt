package com.phat.mytestcicd

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.phat.mytestcicd.presentation.viewmodel.UserViewModel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val viewModel: UserViewModel by viewModels()

    private var frameID = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val frameLayout = FrameLayout(this).apply {
            id = View.generateViewId()
            frameID = id
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )
        }

        setContentView(frameLayout)
    }

    /**
     * This function collects the StateFlow from the ViewModel while the activity
     * is in at least the STARTED state. When the activity moves to a lower lifecycle
     * state (e.g., STOPPED), the collection is automatically cancelled.
     */
    private fun initViewModel() {
        lifecycleScope.launch {
            viewModel.user.collect { user ->
                user?.let {

                }
            }
        }
    }

}
