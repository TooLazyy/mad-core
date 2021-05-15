package ru.wearemad.mad_core.activity

import android.content.Intent
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import ru.wearemad.mad_core.activity.activity_result.ParentActivityResultListener
import ru.wearemad.mad_core.activity.activity_result.ParentActivityResultProvider

abstract class BaseActivity(
    @LayoutRes
    private val layoutId: Int
) : AppCompatActivity(),
    ParentActivityResultProvider {

    private val listeners: ArrayList<ParentActivityResultListener> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
        afterCreate(savedInstanceState)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        listeners.forEach {
            it.onParentActivityResult(requestCode, resultCode, data)
        }
    }

    override fun registerActivityResult(listener: ParentActivityResultListener) {
        if (listeners.contains(listener).not()) {
            listeners.add(listener)
        }
    }

    override fun unregisterActivityResult(listener: ParentActivityResultListener) {
        listeners.remove(listener)
    }

    open fun afterCreate(savedInstanceState: Bundle?) {}
}