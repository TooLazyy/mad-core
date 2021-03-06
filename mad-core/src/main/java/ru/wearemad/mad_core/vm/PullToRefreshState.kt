package ru.wearemad.mad_core.vm

data class PullToRefreshState(
    val enabled: Boolean,
    val refreshing: Boolean
)