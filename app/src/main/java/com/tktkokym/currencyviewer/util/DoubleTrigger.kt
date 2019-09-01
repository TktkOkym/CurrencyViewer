package com.tktkokym.currencyviewer.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

// Make Trigger having 2 Mutable Live Data
class DoubleTrigger<A, B>(a: LiveData<A>, b: LiveData<B>) : MediatorLiveData<Pair<A?, B?>>() {
    init {
        addSource(a) { value = it to b.value }
        addSource(b) { value = a.value to it }
    }
}