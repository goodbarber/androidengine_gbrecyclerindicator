package com.goodbarber.recyclerindicator

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class RecycledSubscriptions {
    private val liveDataSubscriptions = mutableListOf<Pair<LiveData<*>, Observer<*>>>()
    private val jobs = mutableListOf<Job>()

    fun <T> observe(owner: androidx.lifecycle.LifecycleOwner, liveData: LiveData<T>, action: (T) -> Unit) {
        val observer = Observer<T> { action(it) }
        liveData.observe(owner, observer)
        @Suppress("UNCHECKED_CAST")
        liveDataSubscriptions.add(Pair(liveData, observer as Observer<*>))
    }

    fun trackJob(job: Job) {
        jobs.add(job)
    }

    @Suppress("UNCHECKED_CAST")
    fun clear() {
        // Remove observers
        for (pair in liveDataSubscriptions) {
            val liveData = pair.first as LiveData<Any?>
            val observer = pair.second as Observer<Any?>
            liveData.removeObserver(observer)
        }
        liveDataSubscriptions.clear()

        // Cancel jobs
        for (job in jobs) {
            job.cancel()
        }
        jobs.clear()
    }
}

fun <T> GBRecyclerViewHolder<*>.observeRecycled(liveData: LiveData<T>, action: (T) -> Unit) {
    this.recycledSubscriptions.observe(this, liveData, action)
}

fun GBRecyclerViewHolder<*>.launchRecycled(block: suspend CoroutineScope.() -> Unit) {
    val job = this.lifecycleScope.launch {
        this@launchRecycled.repeatOnLifecycle(Lifecycle.State.RESUMED) {
            block()
        }
    }
    this.recycledSubscriptions.trackJob(job)
}
