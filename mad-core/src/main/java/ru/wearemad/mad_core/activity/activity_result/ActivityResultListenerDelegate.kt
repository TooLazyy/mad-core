package ru.wearemad.mad_core.activity.activity_result

import android.content.Intent
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class ActivityResultListenerDelegate(
    listener: (requestCode: Int, resultCode: Int, data: Intent?) -> Unit,
    private val target: Fragment
) : ReadOnlyProperty<Fragment, ParentActivityResultListener> {

    private val resultListener: ParentActivityResultListener =
        ParentActivityResultListener { requestCode: Int, resultCode: Int, data: Intent? ->
            listener(requestCode, resultCode, data)
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

    override fun getValue(thisRef: Fragment, property: KProperty<*>): ParentActivityResultListener {
        if (target.lifecycle.currentState == Lifecycle.State.DESTROYED) {
            clear()
        }
        return resultListener
    }

    @MainThread
    private fun init() {
        (target.activity as? ParentActivityResultProvider)?.registerActivityResult(resultListener)
    }

    @MainThread
    private fun clear() {
        (target.activity as? ParentActivityResultProvider)?.unregisterActivityResult(resultListener)
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

fun Fragment.activityResultListener(
    listener: (requestCode: Int, resultCode: Int, data: Intent?) -> Unit
): ActivityResultListenerDelegate = ActivityResultListenerDelegate(listener, this)