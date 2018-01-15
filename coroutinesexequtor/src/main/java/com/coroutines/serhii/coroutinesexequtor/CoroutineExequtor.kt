package com.coroutines.serhii.coroutinesexequtor

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import android.util.Log
import kotlinx.coroutines.experimental.*

/**
 * Created by Serhii Chaban sc@madappgang.com on 26.11.17.
 */
fun LifecycleOwner.asyncUI(block: suspend LifecycleAwareCoroutine.() -> Unit) = LifecycleAwareCoroutine().apply {
    job = asyncUi(
            block = {
                block(this)
            },
            onCancel = {
                lifecycle.removeObserver(this)
            })
    lifecycle.addObserver(this)
    job.invokeOnCompletion {
        lifecycle.removeObserver(this)
    }
}

class LifecycleAwareCoroutine : LifecycleObserver {
    lateinit var job: Job
    var cancelEvent: Lifecycle.Event = Lifecycle.Event.ON_DESTROY
    fun asyncUi(block: suspend () -> Unit, onCancel: () -> Unit): Job {
        return async(kotlinx.coroutines.experimental.android.UI) {
            try {
                block.invoke()
            } catch (e: CancellationException) {
                Log.d("CorExecutor", "cancel coroutine on event : ${cancelEvent.name}")
                onCancel.invoke()
            }
        }
    }

    val isActive = job.isActive
    val isCanceled = job.isCancelled
    val isCompleted = job.isCompleted

    suspend fun <T> asyncNow(block: suspend () -> T): T {
        return async(CommonPool) {
            block.invoke()
        }.await()
    }

    suspend fun <T> promice(block: suspend () -> T): Deferred<T> {
        return async(CommonPool) {
            block.invoke()
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun destroy() {
        cancel(Lifecycle.Event.ON_DESTROY)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun stop() {
        cancel(Lifecycle.Event.ON_STOP)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun pause() {
        cancel(Lifecycle.Event.ON_PAUSE)
    }

    private fun cancel(event: Lifecycle.Event) {
        if (cancelEvent == event) {
            cancel()
        }
    }

    fun cancel(exception: CancellationException = CancellationException()) {
        job.cancel(exception)
    }

}