package com.phat.mytestcicd.presentation.ex

import android.app.Activity
import android.content.pm.ActivityInfo
import android.os.Build
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KClass

/**
 * Get the Class of generic type from [this] at [genericTypeIndex].
 *
 * **Example**
 * ***
 *  class Test<VM: ViewModel, VDB: ViewDataBinding> {}
 *  * The index of VM is 0
 *  * The index of VDB is 1
 * ***
 *
 * @param genericTypeIndex index of param has type is generic type in [parent class][this].
 */
fun <T : Any> Any.getClassFromGenericType(genericTypeIndex: Int): Class<T> {
    val paramType: ParameterizedType = this.javaClass.genericSuperclass as ParameterizedType
    val genericType: Type = paramType.actualTypeArguments[genericTypeIndex]
    @Suppress("UNCHECKED_CAST")
    return genericType as Class<T>
}

fun <T : Any> Any.getKClassFromGenericType(genericTypeIndex: Int): KClass<T> {
    return getClassFromGenericType<T>(genericTypeIndex).kotlin
}

inline fun <reified T> Any.cast(): T = this as T

fun <VB : ViewBinding> AppCompatActivity.reflectViewBinding(): VB {
    val viewBindingClass: Class<VB> = getClassFromGenericType(0)
    val inflateMethod = viewBindingClass.getMethod("inflate", LayoutInflater::class.java)
    @Suppress("UNCHECKED_CAST")
    return inflateMethod.invoke(null, layoutInflater) as VB
}

inline fun <reified T> Activity.host(): ReadOnlyProperty<Any, T?> {
    return ReadOnlyProperty { _, _ ->
        if (this@host is T) {
            this@host // Return the Activity itself if it matches the type
        } else {
            null // Return null if the Activity does not match the expected type
        }
    }
}
