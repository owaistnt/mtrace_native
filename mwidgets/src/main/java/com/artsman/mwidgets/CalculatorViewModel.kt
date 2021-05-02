package com.artsman.mwidgets

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CalculatorViewModel(){
    private val mStates= MutableStateFlow<States>(States.Display("0"))

    private var _display=""
    fun subscribe(): StateFlow<States> {
        return mStates
    }

    fun setAction(action: Actions) {
        when(action){
            Actions.Start -> mStates.value=(States.Display("0"))
            is Actions.Input -> {
                if (action.input.matches("[0-9]".toRegex())) {
                    processNumberInput(action)
                }
            }
        }
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