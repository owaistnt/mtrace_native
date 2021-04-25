package com.artsman.mwidgets

import androidx.lifecycle.Observer
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain

class CalculatorViewModelTest : StringSpec({

    lateinit var calculatorViewModel: CalculatorViewModel

    val observer: Observer<CalculatorViewModel.States> = mockk<Observer<CalculatorViewModel.States>>()
    val coroutineDispatcher = TestCoroutineDispatcher()

    /*listener(InstantExecutorListener())*/
    beforeEach {
        Dispatchers.setMain(coroutineDispatcher)
        calculatorViewModel = CalculatorViewModel()
    }

    afterEach {
        //clearMocks(observer)
    }

    "on start it must display 0"{
        calculatorViewModel.setAction(CalculatorViewModel.Actions.Start)
        val subscription = calculatorViewModel.subscribe()
        assert(subscription.value == CalculatorViewModel.States.Display("0"))
    }

    "on digit enter it must display number without zero prefix"{
        coroutineDispatcher.runBlockingTest {
            calculatorViewModel.setAction(CalculatorViewModel.Actions.Start)
            calculatorViewModel.subscribe().first() shouldBe CalculatorViewModel.States.Display("0")
            calculatorViewModel.setAction(CalculatorViewModel.Actions.Input("1"))
            calculatorViewModel.subscribe().first() shouldBe CalculatorViewModel.States.Display("1")
            calculatorViewModel.setAction(CalculatorViewModel.Actions.Input("2"))
            calculatorViewModel.subscribe().first() shouldBe CalculatorViewModel.States.Display("12")

        }
    }

    "on non digit it must not add to display"{
        coroutineDispatcher.runBlockingTest {
            calculatorViewModel.setAction(CalculatorViewModel.Actions.Input("+"))
            calculatorViewModel.subscribe().first() shouldNotBe  CalculatorViewModel.States.Display("+")
        }
    }
})

