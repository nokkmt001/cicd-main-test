package com.phat.mytestcicd.presentation.ex

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty

/**
 * A getter delegate that designed for interface only.
 *
 * This delegate checks whether [the parent fragment][Fragment.getParentFragment]
 * or [the activity context][Activity] (where [this fragment][this]
 * is attached to) is implementing specified interface? If the interface is implemented, it returns
 * the host context where the interface is implemented, otherwise, it returns null.
 *
 * Purpose is adapt to changing device config (rotation change, color mode,...), specified interface
 * will not be affected when the fragment is reinitialized.
 *
 * ```
 * // Dialog fragment that use hostContext() delegate to get actionHandler interface.
 * class LoginPopup() : DialogFragment() {
 *     /**
 *      * Get ActionHandler from the host context (Activity/Fragment) that is hosting this popup.
 *      */
 *     private val actionHandler: ActionHandler? by host()
 *
 *     override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
 *         super.onViewCreated(view, savedInstanceState)
 *         view.findViewById<Button>(R.id.btn_login).setOnClickListener {
 *             actionHandler?.onClickLogin()
 *         }
 *     }
 *
 *     interface ActionHandler {
 *         fun onClickLogin()
 *     }
 * }
 *
 * // Activity that hosting the LoginPopup after login button is clicked.
 * class LoginActivity: Activity(), LoginPopup.ActionHandler {
 *     override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
 *         super.onCreate(savedInstanceState, persistentState)
 *         findViewById<Button>(R.id.btn_login).setOnClickListener {
 *             val loginPopup = LoginPopup()
 *             loginPopup.show(fragmentManager, "LoginPopup")
 *         }
 *     }
 *
 *     override fun onClickLogin() {
 *         // Do something after login button is clicked from LoginPopup
 *     }
 * }
 * ```
 */
inline fun <reified T> Fragment.host(): ReadOnlyProperty<Any, T?> {
    return ReadOnlyProperty { _, _ ->
        val activityContext: Context? = this@host.context
        val parentFragment: Fragment? = this@host.parentFragment

        if (parentFragment != null) {
            if (parentFragment is T) parentFragment else null
        } else if (activityContext is T) {
            activityContext
        } else {
            null
        }
    }
}

fun <VB : ViewBinding> Fragment.reflectViewBinding(
    inflater: LayoutInflater,
    container: ViewGroup?
): VB {
    val viewBindingClass: Class<VB> = getClassFromGenericType(0)
    val inflateMethod = viewBindingClass.getMethod(
        "inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java
    )
    @Suppress("UNCHECKED_CAST")
    return inflateMethod.invoke(null, inflater, container, false) as VB
}
