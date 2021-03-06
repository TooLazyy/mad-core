package ru.wearemad.mad_core.properties

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter

open class ObjectBindableProperty<T : Any?> {

    val mostRecentValue: StateValue<T>
        get() = stateFlow.value
    private val stateFlow: MutableStateFlow<StateValue<T>>
    private var freezeValues = false

    constructor() {
        stateFlow = MutableStateFlow(StateValue.None)
    }

    constructor(value: T) {
        stateFlow = MutableStateFlow(StateValue.Some(value))
    }

    suspend fun subscribe(listener: (newValue: T) -> Unit) {
        stateFlow
            .filter { !freezeValues }
            .collect { value ->
                value.actionIfHasValue {
                    listener(it)
                }
            }
    }

    fun post(newValue: T) {
        stateFlow.value = StateValue.Some(newValue)
    }

    fun freeze(freeze: Boolean) {
        freezeValues = freeze
    }

    fun getValueOrDefault(default: T): T = mostRecentValue.getValueOrDefault(default)

    fun getValueOrNull(): T? = mostRecentValue.getValueOrNull()

    fun clear() {
        stateFlow.value = StateValue.None
        freezeValues = false
    }
}

class StringBindableProperty :
    ObjectBindableProperty<String> {

    constructor(initialValue: String) : super(initialValue)
    constructor() : super()
}

class BooleanBindableProperty :
    ObjectBindableProperty<Boolean> {

    constructor(initialValue: Boolean) : super(initialValue)
    constructor() : super()
}

class IntBindableProperty :
    ObjectBindableProperty<Int> {

    constructor(initialValue: Int) : super(initialValue)
    constructor() : super()
}

class LongBindableProperty :
    ObjectBindableProperty<Long> {

    constructor(initialValue: Long) : super(initialValue)
    constructor() : super()
}

class ListBindableProperty<T> :
    ObjectBindableProperty<List<T>> {

    constructor(initialValue: List<T>) : super(initialValue)
    constructor() : super()
}