package ru.wearemad.mad_core.activity.activity_result

import android.os.Bundle
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentResultListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class ActivityFragmentResultListenerDelegate(
    listener: (requestKey: String, result: Bundle) -> Unit,
    private val requestKey: String,
    private val target: Fragment
) : ReadOnlyProperty<Fragment, FragmentResultListener> {

    private val resultListener: FragmentResultListener =
        FragmentResultListener { requestKey, result ->
            listener(requestKey, result)
        }
    private val lifecycleObserver: TargetLifecycleObserver = TargetLifecycleObserver()

    init {
        val lifecycle = target.lifecycle
        if (lifecycle.currentState != Lifecycle.State.DESTROYED) {
            lifecycle.addObserver(lifecycleObserver)
        } else {
            lifecycle.removeObserver(lifecycleObserver)
        }
    }

    override fun getValue(thisRef: Fragment, property: KProperty<*>): FragmentResultListener {
        if (target.lifecycle.currentState == Lifecycle.State.DESTROYED) {
            clear()
        }
        return resultListener
    }

    @MainThread
    private fun init() {
        target
            .activity
            ?.supportFragmentManager
            ?.setFragmentResultListener(requestKey, target, resultListener)
    }

    @MainThread
    private fun clear() {
        target
            .activity
            ?.supportFragmentManager
            ?.clearFragmentResultListener(requestKey)
    }

    private inner class TargetLifecycleObserver : LifecycleObserver {

        @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
        fun onCreate() {
            init()
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        fun onDestroy() {
            clear()
        }
    }
}

fun Fragment.activityFragmentResult(
    requestKey: String,
    listener: (requestKey: String, result: Bundle) -> Unit
): ActivityFragmentResultListenerDelegate = ActivityFragmentResultListenerDelegate(listener, requestKey, this)