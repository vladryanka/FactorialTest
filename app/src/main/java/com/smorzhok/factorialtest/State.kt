package com.smorzhok.factorialtest

sealed class State

data object Error : State()
data object Loading : State()
class Factorial(val factorial: String):State()