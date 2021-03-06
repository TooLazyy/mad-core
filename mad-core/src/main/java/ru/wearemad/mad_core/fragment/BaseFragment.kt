package ru.wearemad.mad_core.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment

private const val ARG_FRAGMENT_DESTROYED_ONCE = "arg_fragment_destroyed_once"

abstract class BaseFragment(
    @LayoutRes
    private val layoutId: Int
) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(layoutId, container, false)

    override fun onHiddenChanged(hidden: Boolean) {
        if (hidden) {
            hideEvent()
        } else {
            showEvent()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (arguments == null) {
            arguments = bundleOf()
        }
        afterCreate(savedInstanceState, isDestroyedOnce(savedInstanceState))
    }

    override fun onDestroyView() {
        setDestroyedOnce()
        super.onDestroyView()
    }

    override fun onDestroy() {
        setDestroyedOnce()
        super.onDestroy()
    }

    protected fun registerFragmentResult(
        requestKey: String
    ) {
        childFragmentManager
            .setFragmentResultListener(
                requestKey,
                this,
                { key, result ->
                    onFragmentResult(key, result)
                }
            )
    }

    open fun afterCreate(savedInstanceState: Bundle?, recreated: Boolean) {}

    open fun onHide() {}

    open fun onShow() {}

    open fun onFragmentResult(key: String, result: Bundle) {}

    open fun backPressHandled(): Boolean = false

    private fun showEvent() {
        if (isAdded) {
            onShow()
        }
    }

    private fun hideEvent() {
        if (isAdded) {
            onHide()
        }
    }

    private fun setDestroyedOnce() {
        arguments?.putBoolean(ARG_FRAGMENT_DESTROYED_ONCE, true)
    }

    private fun isDestroyedOnce(savedInstanceState: Bundle?): Boolean {
        return if (savedInstanceState != null) {
            true
        } else {
            arguments?.getBoolean(ARG_FRAGMENT_DESTROYED_ONCE) ?: false
        }
    }
}