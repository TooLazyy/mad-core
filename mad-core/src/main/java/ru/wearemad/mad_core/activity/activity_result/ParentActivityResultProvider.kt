package ru.wearemad.mad_core.activity.activity_result

interface ParentActivityResultProvider {

    fun registerActivityResult(listener: ParentActivityResultListener)

    fun unregisterActivityResult(listener: ParentActivityResultListener)
}