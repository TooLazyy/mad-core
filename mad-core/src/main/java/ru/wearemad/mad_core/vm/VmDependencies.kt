package ru.wearemad.mad_core.vm

import ru.wearemad.mad_base.coroutines.DispatchersProvider
import ru.wearemad.mad_base.message.MessageController
import ru.wearemad.mad_navigation.CiceroneHolder
import ru.wearemad.mad_navigation.router.AppRouter

data class VmDependencies(
    val ciceroneHolder: CiceroneHolder,
    val router: AppRouter,
    val messageController: MessageController,
    val dispatchers: DispatchersProvider,
)