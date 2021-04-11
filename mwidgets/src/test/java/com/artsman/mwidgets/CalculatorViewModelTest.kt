package com.artsman.mwidgets

import androidx.lifecycle.Observer
import io.kotest.core.spec.style.StringSpec
import io.mockk.clearMocks
import io.mockk.mockk
import io.mockk.verify

class CalculatorViewModelTest : StringSpec({

    lateinit var calculatorViewModel: CalculatorViewModel

    val observer:Observer<CalculatorViewModel.States> = mockk<Observer<CalculatorViewModel.States>>()

    listener(InstantExecutorListener())
    beforeEach {
        calculatorViewModel= CalculatorViewModel()
        calculatorViewModel.subcribe().observeForever(observer)
    }

    afterEach {
        calculatorViewModel.subcribe().removeObserver(observer)
        //clearMocks(observer)
    }

    "on start it must display 0"{
        calculatorViewModel.setAction(CalculatorViewModel.Actions.Start)
        verify(atLeast = 1) { observer.onChanged(CalculatorViewModel.States.Display("0")) }
    }
})

