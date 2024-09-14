package com.ikhsan.storyapp.base.helper

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData


fun <T> Fragment.observeEvent(data: LiveData<Event<T>>, onBound: ((T) -> Unit)) {
    data.observe(this.viewLifecycleOwner, EventObserver {
        onBound.invoke(it)
    })
}

fun <T> Fragment.observe(data: LiveData<T>, onBound: ((T?) -> Unit)) {
    data.observe(this.viewLifecycleOwner) {
        onBound.invoke(it)
    }
}

fun <T> Activity.observeEvent(data: LiveData<Event<T>>, onBound: ((T) -> Unit)) {
    data.observe(this as LifecycleOwner, EventObserver {
        onBound.invoke(it)
    })
}

fun <T> Activity.observe(data: LiveData<T>, onBound: ((T?) -> Unit)) {
    data.observe(this as LifecycleOwner) {
        onBound.invoke(it)
    }
}