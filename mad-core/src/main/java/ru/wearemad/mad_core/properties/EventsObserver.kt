package ru.wearemad.mad_core.properties

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class EventsObserver<T : Any?>(
    lifecycleOwner: LifecycleOwner,
    private val flowToObserve: Flow<T>,
    private val flowObserver: suspend (T) -> Unit
) {

    private var flowEmitterJob: Job? = null

    private val lifecycleObserver: LifecycleEventObserver = LifecycleEventObserver { source, event ->
        when (event) {
            Lifecycle.Event.ON_START -> {
                stop()
                source.start()
            }
            Lifecycle.Event.ON_STOP -> stop()
            else -> {/*do nothing*/
            }
        }
    }

    init {
        val state = lifecycleOwner.lifecycle.currentState
        if (state != Lifecycle.State.DESTROYED) {
            lifecycleOwner.lifecycle.addObserver(lifecycleObserver)
            if (state == Lifecycle.State.STARTED) {
                lifecycleOwner.start()
            }
        } else {
            lifecycleOwner.lifecycle.removeObserver(lifecycleObserver)
            stop()
        }
    }

    private suspend fun startObserving() {
        flowToObserve
            .collect {
                flowObserver(it)
            }
    }

    private fun stop() {
        flowEmitterJob?.cancel()
        flowEmitterJob = null
    }

    private fun LifecycleOwner.start() {
        flowEmitterJob = lifecycleScope.launch {
            this@EventsObserver.startObserving()
        }
    }
}