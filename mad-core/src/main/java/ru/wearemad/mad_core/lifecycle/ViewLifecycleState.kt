package ru.wearemad.mad_core.lifecycle

sealed class ViewLifecycleState(
    val id: Int
) {

    abstract fun checkLifecycleFit(state: ViewLifecycleState): Boolean

    object None : ViewLifecycleState(-1) {

        override fun checkLifecycleFit(state: ViewLifecycleState): Boolean = false
    }

    object Created : ViewLifecycleState(0) {

        override fun checkLifecycleFit(
            state: ViewLifecycleState
        ): Boolean = state.id >= Created.id &&
                state.id < Destroyed.id
    }

    object Started : ViewLifecycleState(1) {

        override fun checkLifecycleFit(
            state: ViewLifecycleState
        ): Boolean = state.id >= Started.id &&
                state.id < Stopped.id
    }

    object Resumed : ViewLifecycleState(2) {

        override fun checkLifecycleFit(
            state: ViewLifecycleState
        ): Boolean = state.id == Resumed.id
    }

    object Paused : ViewLifecycleState(3) {

        override fun checkLifecycleFit(
            state: ViewLifecycleState
        ): Boolean = state.id >= Paused.id
    }

    object Stopped : ViewLifecycleState(4) {

        override fun checkLifecycleFit(
            state: ViewLifecycleState
        ): Boolean = state.id >= Stopped.id
    }

    object Destroyed : ViewLifecycleState(5) {

        override fun checkLifecycleFit(
            state: ViewLifecycleState
        ): Boolean = state.id >= Destroyed.id
    }
}