package ru.wearemad.mad_core.fragment.bottom

import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.NavigatorHolder
import ru.wearemad.mad_core.fragment.FragmentDestroyChecker
import ru.wearemad.mad_navigation.CiceroneHolder
import ru.wearemad.mad_navigation.navigator.FragmentNavigator

abstract class BaseFlowBottomSheetFragment(
    layoutId: Int
) : BaseBottomSheetFragment(layoutId) {

    abstract val ciceroneHolder: CiceroneHolder

    private val navigator: Navigator by lazy {
        createNavigator()
    }

    abstract val navigatorKey: String

    open val navigationHolder: NavigatorHolder
        get() = ciceroneHolder.getOrCreateHolder(navigatorKey)

    override fun onResume() {
        super.onResume()
        navigationHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigationHolder.removeNavigator()
        super.onPause()
    }

    override fun onDestroy() {
        if (FragmentDestroyChecker.isDestroying(this)) {
            ciceroneHolder.remove(navigatorKey)
        }
        super.onDestroy()
    }

    open fun createNavigator(): Navigator = FragmentNavigator(0, this)
}