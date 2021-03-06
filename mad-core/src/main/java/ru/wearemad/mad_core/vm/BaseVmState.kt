package ru.wearemad.mad_core.vm

import ru.wearemad.mad_core.properties.ObjectBindableProperty

open class BaseVmState {

    val loading = ObjectBindableProperty<LoadingState>(LoadingState.None)
}

sealed class LoadingState {

    object Loading : LoadingState()

    object None : LoadingState()

    object Transparent : LoadingState()
}