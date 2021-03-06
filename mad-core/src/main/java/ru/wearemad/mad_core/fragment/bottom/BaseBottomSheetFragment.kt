package ru.wearemad.mad_core.fragment.bottom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.os.bundleOf
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

private const val ARG_FRAGMENT_DESTROYED_ONCE = "arg_fragment_destroyed_once"

abstract class BaseBottomSheetFragment(
    @LayoutRes
    private val layoutId: Int
) : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = layoutInflater.inflate(layoutId, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (arguments == null) {
            arguments = bundleOf()
        }
        afterCreate(savedInstanceState, isDestroyedOnce(savedInstanceState))
    }

    open fun afterCreate(savedInstanceState: Bundle?, recreated: Boolean) {}

    override fun onDestroyView() {
        setDestroyedOnce()
        super.onDestroyView()
    }

    override fun onDestroy() {
        setDestroyedOnce()
        super.onDestroy()
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