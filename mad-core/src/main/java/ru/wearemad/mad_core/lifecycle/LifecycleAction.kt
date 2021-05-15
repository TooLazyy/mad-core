package ru.wearemad.mad_core.lifecycle

class LifecycleAction(
    val targetState: ViewLifecycleState,
    val action: () -> Unit
)