package ru.wearemad.mad_core.vm

import androidx.annotation.MainThread
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.withContext
import ru.wearemad.mad_base.coroutines.RequestResult
import kotlin.coroutines.CoroutineContext

abstract class BaseVm<S : BaseVmState>(
    val state: S,
    protected val dependencies: VmDependencies
) : ViewModel(),
    CoroutineScope {

    private val parentJob = SupervisorJob()

    override val coroutineContext: CoroutineContext = dependencies.dispatchers.default() + parentJob

    override fun onCleared() {
        parentJob.cancelChildren()
    }

    @MainThread
    protected open fun onError(error: Throwable) {
        state.loading.post(LoadingState.None)
        showErrorMessage(error)
    }

    protected suspend fun runOnUi(block: suspend () -> Unit) =
        withContext(dependencies.dispatchers.main()) {
            block()
        }

    protected suspend fun <T> RequestResult<T>.handleResult(
        block: suspend (T) -> Unit
    ) = runOnUi {
        when (val result = this@handleResult) {
            is RequestResult.Success -> block(result.data)
            is RequestResult.Error -> onError(result.error)
        }
    }

    protected suspend fun <T> RequestResult<T>.handleResultWithError(
        resultBlock: suspend (T) -> Unit,
        errorBlock: suspend (Throwable) -> Unit
    ) = runOnUi {
        when (val result = this@handleResultWithError) {
            is RequestResult.Success -> resultBlock(result.data)
            is RequestResult.Error -> errorBlock(result.error)
        }
    }

    @MainThread
    protected open fun showErrorMessage(error: Throwable) {
    }

    open fun exit() {
        dependencies.router.exit()
    }
}