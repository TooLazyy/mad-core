package ru.wearemad.mad_core.lifecycle

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import ru.wearemad.mad_core.vm.BaseVm

fun Fragment.attachVmToLifecycle(vm: BaseVm<*>) {
    lifecycle.addObserver(
        object : LifecycleObserver {

            @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
            fun onCreate(owner: LifecycleOwner) {
                vm.lifecycleStateChanged(ViewLifecycleState.Created)
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
            fun onResume(owner: LifecycleOwner) {
                vm.lifecycleStateChanged(ViewLifecycleState.Resumed)
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_START)
            fun onStart(owner: LifecycleOwner) {
                vm.lifecycleStateChanged(ViewLifecycleState.Started)
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
            fun onStop(owner: LifecycleOwner) {
                vm.lifecycleStateChanged(ViewLifecycleState.Stopped)
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
            fun onPause(owner: LifecycleOwner) {
                vm.lifecycleStateChanged(ViewLifecycleState.Paused)
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            fun onDestroy(owner: LifecycleOwner) {
                vm.lifecycleStateChanged(ViewLifecycleState.Destroyed)
            }
        }
    )
}