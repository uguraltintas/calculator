package com.uguraltintas.calculator.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

enum class CalculatorOperation { PLUS, DIVIDE, MULTIPLY, MINUS, NOTHING }

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val _number = MutableLiveData("0")
    val number: LiveData<String>
        get() = _number

    private val _number1 = MutableLiveData("")
    val number1: LiveData<String>
        get() = _number1

    private val calculatorOperation = MutableLiveData(CalculatorOperation.NOTHING)

    val number1Visibility = MutableLiveData(false)

    fun onNumberClick(value: Int) {
        if(_number.value.equals("ERROR")) {
            _number.value = "0"
            _number1.postValue("")
        }
        if (_number.value.equals("0")) {
            _number.value = value.toString()
        } else {
            _number.value = _number.value + value.toString()
        }
    }

    fun onPlusClick() {
        calculatorOperation.postValue(CalculatorOperation.PLUS)
        if (_number1.value.equals("")) {
            _number1.postValue(_number.value)
            _number.value = ""
            number1Visibility.postValue(true)
        } else {
            val number1 = _number1.value?.toBigInteger()?.plus(_number.value!!.toBigInteger())
            _number1.postValue(number1.toString())
            _number.postValue("0")
        }
    }

    fun onMultiplyClick() {
        calculatorOperation.postValue(CalculatorOperation.MULTIPLY)
        if (_number1.value.equals("")) {
            _number1.postValue(_number.value)
            _number.value = ""
            number1Visibility.postValue(true)
        } else {
            val number1 = _number1.value?.toBigInteger()?.times(_number.value!!.toBigInteger())
            _number1.postValue(number1.toString())
            _number.postValue("0")
        }
    }

    fun onDivideClick() {
        calculatorOperation.postValue(CalculatorOperation.DIVIDE)
        if (_number1.value.equals("")) {
            _number1.postValue(_number.value)
            _number.value = ""
            number1Visibility.postValue(true)
        } else {
            try {
                val number1 = _number1.value?.toDouble()?.div(_number.value!!.toDouble())
                _number1.postValue(number1.toString())
                _number.postValue("0")
            } catch (e: Exception) {
                _number.postValue("ERROR")
                number1Visibility.postValue(false)
            }
        }
    }

    fun onMinusClick() {
        calculatorOperation.postValue(CalculatorOperation.MINUS)
        if (_number1.value.equals("")) {
            _number1.postValue(_number.value)
            _number.value = ""
            number1Visibility.postValue(true)
        } else {
            val number1 = _number1.value?.toBigInteger()?.minus(_number.value!!.toBigInteger())
            _number1.postValue(number1.toString())
            _number.postValue("0")
        }
    }

    fun onEqualsClick() {
        when (calculatorOperation.value) {
            CalculatorOperation.MINUS -> {
                val number = _number1.value?.toBigInteger()?.minus(_number.value!!.toBigInteger())
                _number.postValue(number.toString())
                _number1.postValue("")
                number1Visibility.postValue(false)
                calculatorOperation.postValue(CalculatorOperation.NOTHING)
            }
            CalculatorOperation.DIVIDE -> {
                try {
                    val number = _number1.value?.toDouble()?.div(_number.value!!.toDouble())
                    _number.postValue(number.toString())
                    _number1.postValue("")
                    number1Visibility.postValue(false)
                    calculatorOperation.postValue(CalculatorOperation.NOTHING)
                } catch (e: Exception) {
                    _number.postValue("ERROR")
                    number1Visibility.postValue(false)
                    calculatorOperation.postValue(CalculatorOperation.NOTHING)
                }
            }
            CalculatorOperation.MULTIPLY -> {
                val number = _number1.value?.toBigInteger()?.times(_number.value!!.toBigInteger())
                _number.postValue(number.toString())
                _number1.postValue("")
                number1Visibility.postValue(false)
                calculatorOperation.postValue(CalculatorOperation.NOTHING)
            }

            CalculatorOperation.PLUS -> {
                val number = _number1.value?.toBigInteger()?.plus(_number.value!!.toBigInteger())
                _number.postValue(number.toString())
                _number1.postValue("")
                number1Visibility.postValue(false)
                calculatorOperation.postValue(CalculatorOperation.NOTHING)
            }
            else -> {}
        }

    }

    fun onAllClearClick() {
        _number.postValue("0")
        _number1.postValue("")
    }
}