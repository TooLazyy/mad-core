package ru.wearemad.mad_core.activity

import androidx.annotation.LayoutRes
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.NavigatorHolder
import ru.wearemad.mad_navigation.navigator.ActivityNavigator

abstract class BaseFlowActivity(
    @LayoutRes
    layoutId: Int
) : BaseActivity(layoutId) {

    abstract val navigationHolder: NavigatorHolder

    private val navigator: Navigator by lazy {
        createNavigator()
    }

    abstract val containerId: Int

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigationHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigationHolder.removeNavigator()
        super.onPause()
    }

    open fun createNavigator(): Navigator = ActivityNavigator(containerId, this)
}