package com.smorzhok.factorialtest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.math.BigInteger

class MainViewModel : ViewModel() {

    private val scope = CoroutineScope(Dispatchers.Main + CoroutineName("MyCoroutineScope"))

    private val _state = MutableLiveData<State>()
    val state: LiveData<State> get() = _state

    fun calculate(value: String?) {
        _state.value = Loading
        if (value.isNullOrBlank()) {
            _state.value = Error
            return
        }

        scope.launch {
            val number = value.toLong()
            withContext(Dispatchers.Default) {
                val result = factorial(number)
                withContext(Dispatchers.Main){
                    _state.value = Factorial(result)
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }

    private fun factorial(number: Long): String {

        var result = BigInteger.ONE
        for (i in 1..number) {
            result = result.multiply(BigInteger.valueOf(i))
        }
        return result.toString()

    }
}