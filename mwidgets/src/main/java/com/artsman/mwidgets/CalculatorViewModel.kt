package com.artsman.mwidgets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.util.*
import javax.inject.Inject

class CalculatorViewModel @Inject constructor(private val calculator: Calculator){
    private val mStates= MutableStateFlow<States>(States.Display("0"))
    private val mInputStack= Stack<BigDecimal>()
    private val mOperationStack= Stack<String>()
    private var _display=""
    fun subscribe(): StateFlow<States> {
        return mStates
    }

    fun setAction(action: Actions) {
        GlobalScope.launch {
            when(action){
                Actions.Start -> mStates.value=(States.Display("0"))
                is Actions.Input -> {
                    if (action.input.matches("[0-9]".toRegex())) {
                        processNumberInput(action)
                    } else {
                        if (mOperationStack.isEmpty()) {
                            mOperationStack.push(action.input)
                            if (mInputStack.isEmpty()) {
                                mInputStack.push(_display.toBigDecimal())
                                _display = ""
                            }
                            return@launch
                        }
                        var answer = mInputStack.peek();
                        val operation = mOperationStack.pop()
                        val input1 = mInputStack.pop()
                        if (_display.isNotEmpty()) {
                            val input2 = _display.toBigDecimal()
                            answer = calculator.calculate(
                                input1,
                                input2,
                                operation.getOperator()
                            )
                        }
                        mInputStack.push(answer)
                        if (action.input != "=") {
                            mOperationStack.push(action.input)
                        }
                        _display = ""
                        mStates.emit(States.Display(answer.toString()))
                    }
                }
            }
        }
    }

    private fun String.getOperator()=when(this){
        "+"-> CalculatorWidget.OPERATOR.ADD
        "-"-> CalculatorWidget.OPERATOR.SUB
        "/"-> CalculatorWidget.OPERATOR.DIV
        "*"-> CalculatorWidget.OPERATOR.PROD
        else-> CalculatorWidget.OPERATOR.NONE
    }

    private fun processNumberInput(action: Actions.Input) {
        if (_display.isEmpty()) {
            mStates.value = States.Display("${action.input}")
            _display += action.input
        } else {
            _display += action.input
            mStates.value = States.Display("$_display")
        }
    }

    sealed class States{
        data class Display(val value: String=""): States()
    }

    sealed class Actions{
        data class Input(val input: String) : Actions() {

        }

        object Start: Actions()
    }
}