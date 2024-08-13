package com.zzy.component.flow.message

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class FlowBus : ViewModel() {
    companion object {
        val instance by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            FlowBus()
        }
    }


}

class Event<T>(private val key: String, isSticky: Boolean) {
    private val _events = MutableSharedFlow<T>(replay = if (isSticky) 1 else 0)
    val events = _events.asSharedFlow()

    fun observeEvent(
        lifecycleOwner: LifecycleOwner,
        dispatcher: CoroutineDispatcher = Dispatchers.Main.immediate,
        minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
        action: (t: T) -> Unit
    ) {
        lifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onDestroy(owner: LifecycleOwner) {
                super.onDestroy(owner)
                val subscriptCount = _events.subscriptionCount.value
                if (subscriptCount <= 0){
                    FlowBus.instance
                }
            }
        })
    }
}