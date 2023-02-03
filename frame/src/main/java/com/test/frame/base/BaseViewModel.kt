package com.test.frame.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


open class BaseViewModel : ViewModel() {


    fun launchOnUI(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch { block() }
    }

    fun <T> launchOnIO(block: suspend CoroutineScope.() -> T) {
        viewModelScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO){
                block()
            }
        }
    }
}

