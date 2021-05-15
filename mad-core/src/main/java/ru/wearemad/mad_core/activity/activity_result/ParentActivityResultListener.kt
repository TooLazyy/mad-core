package ru.wearemad.mad_core.activity.activity_result

import android.content.Intent

fun interface ParentActivityResultListener {

    fun onParentActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
}