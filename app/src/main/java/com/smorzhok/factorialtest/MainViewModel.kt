package com.smorzhok.factorialtest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _state = MutableLiveData<State>()
    val state: LiveData<State> get() = _state

    fun calculate(value: String?) {
        _state.value = State(
            loading = true
        )
        if (value.isNullOrBlank()) {
            _state.value = State(
                error = true
            )
            return
        }
        viewModelScope.launch {
            val number = value.toLong()

            delay(1000)
            _state.value = State(
                factorial = number.toString()
            )
        }
    }
}