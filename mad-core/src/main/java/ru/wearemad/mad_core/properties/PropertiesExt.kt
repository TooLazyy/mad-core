package ru.wearemad.mad_core.properties

import android.view.View
import android.widget.TextView
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import ru.surfstudio.android.easyadapter.EasyAdapter
import ru.surfstudio.android.easyadapter.ItemList
import ru.surfstudio.android.easyadapter.controller.BindableItemController

//region fragment
fun <T> Fragment.bind(property: ObjectBindableProperty<T>, listener: (newValue: T) -> Unit) {
    viewLifecycleOwner
        .lifecycleScope
        .launchWhenStarted {
            property.subscribe {
                listener(it)
            }
        }
}

fun <T> Fragment.bindAdapter(
    property: ListBindableProperty<T>,
    adapter: EasyAdapter,
    controllerProvider: ItemList.BindableItemControllerProvider<T>
) {
    bind(property) {
        adapter
            .setItems(
                ItemList
                    .create()
                    .addAll(it, controllerProvider)
            )
    }
}

fun Fragment.bindEnabled(property: BooleanBindableProperty, view: View) {
    bind(property) {
        view.isEnabled = it
    }
}

fun Fragment.bindText(property: StringBindableProperty, view: TextView) {
    bind(property) {
        view.text = it
    }
}

fun Fragment.bindVisible(property: BooleanBindableProperty, view: View) {
    bind(property) {
        view.isVisible = it
    }
}

fun Fragment.bindInvisible(property: BooleanBindableProperty, view: View) {
    bind(property) {
        view.isInvisible = it
    }
}

fun <T> Fragment.bindAdapter(
    property: ListBindableProperty<T>,
    adapter: EasyAdapter,
    controller: BindableItemController<T, *>,
) {
    bind(property) {
        adapter
            .setItems(
                ItemList
                    .create()
                    .addAll(it, controller)
            )
    }
}
//endregion