package ru.wearemad.mad_core.properties

import androidx.fragment.app.Fragment
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow

fun <T : Any?> Flow<T>.observeEvents(
    fragment: Fragment,
    eventsObserver: suspend (T) -> Unit
): EventsObserver<T> = EventsObserver(fragment.viewLifecycleOwner, this, eventsObserver)

fun <T : Any?> createEventsChannel(): Channel<T> = Channel(Channel.BUFFERED)