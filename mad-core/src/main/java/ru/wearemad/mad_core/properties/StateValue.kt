package ru.wearemad.mad_core.properties

sealed class StateValue<out T> {

    data class Some<T>(val value: T) : StateValue<T>()

    object None : StateValue<Nothing>()

    fun actionIfHasValue(action: (T) -> Unit) {
        if (this is Some) {
            action(this.value)
        }
    }

    fun getValueOrNull(): T? =
        when (this) {
            is Some -> value
            is None -> null
        }
}

fun <T> StateValue<T>.getValueOrDefault(default: T): T =
    when (this) {
        is StateValue.Some -> value
        is StateValue.None -> default
    }