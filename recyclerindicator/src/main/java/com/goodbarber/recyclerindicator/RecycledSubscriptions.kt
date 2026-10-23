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

    // True while the observers are registered on their LiveData, see setObserving()
    private var isObserving = false

    fun <T> observe(owner: androidx.lifecycle.LifecycleOwner, liveData: LiveData<T>, action: (T) -> Unit) {
        val observer = Observer<T> { action(it) }
        if (isObserving) {
            liveData.observe(owner, observer)
        }
        @Suppress("UNCHECKED_CAST")
        liveDataSubscriptions.add(Pair(liveData, observer as Observer<*>))
    }

    fun trackJob(job: Job) {
        jobs.add(job)
    }

    /**
     * Registers the observers on their LiveData while the cell is on screen, and removes them otherwise while keeping
     * them for when it comes back. A LiveData only drops an observer whose owner is DESTROYED, which a reusable cell
     * never is: left registered, a cell whose screen is gone would stay reachable, with its whole activity, from a
     * LiveData that outlives it.
     */
    @Suppress("UNCHECKED_CAST")
    fun setObserving(owner: androidx.lifecycle.LifecycleOwner, observing: Boolean) {
        if (observing == isObserving) {
            return
        }
        isObserving = observing
        for (pair in liveDataSubscriptions) {
            val liveData = pair.first as LiveData<Any?>
            val observer = pair.second as Observer<Any?>
            if (observing) {
                liveData.observe(owner, observer)
            } else {
                liveData.removeObserver(observer)
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun clear() {
        // Remove observers
        if (isObserving) {
            for (pair in liveDataSubscriptions) {
                val liveData = pair.first as LiveData<Any?>
                val observer = pair.second as Observer<Any?>
                liveData.removeObserver(observer)
            }
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
