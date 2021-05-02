package com.artsman.mwidgets

import androidx.lifecycle.Observer
import app.cash.turbine.test
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.should
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
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class CalculatorViewModelTest : StringSpec({

    lateinit var calculatorViewModel: CalculatorViewModel

    val testCoroutingDispatcher= TestCoroutineDispatcher()
    /*listener(InstantExecutorListener())*/
    beforeEach {
        Dispatchers.setMain(testCoroutingDispatcher)
        calculatorViewModel = CalculatorViewModel(Calculator())
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
        testCoroutingDispatcher.runBlockingTest {
            calculatorViewModel.subscribe().test {
                calculatorViewModel.setAction(CalculatorViewModel.Actions.Start)
                expectItem() shouldBe CalculatorViewModel.States.Display("0")
                calculatorViewModel.setAction(CalculatorViewModel.Actions.Input("1"))
                expectItem() shouldBe CalculatorViewModel.States.Display("1")
                calculatorViewModel.setAction(CalculatorViewModel.Actions.Input("2"))
                expectItem() shouldBe CalculatorViewModel.States.Display("12")

            }

        }
    }

    "on non digit it must not add to display"{
        runBlocking {
            calculatorViewModel.setAction(CalculatorViewModel.Actions.Input("+"))
            calculatorViewModel.subscribe().first() shouldNotBe  CalculatorViewModel.States.Display("+")
        }
    }

    "on simple calculation"{
        testCoroutingDispatcher.runBlockingTest {
            calculatorViewModel.subscribe().test {
                expectItem() shouldBe CalculatorViewModel.States.Display("0")
                calculatorViewModel.setAction(CalculatorViewModel.Actions.Input("1"))
                expectItem() shouldBe CalculatorViewModel.States.Display("1")
                calculatorViewModel.setAction(CalculatorViewModel.Actions.Input("0"))
                expectItem() shouldBe CalculatorViewModel.States.Display("10")
                calculatorViewModel.setAction(CalculatorViewModel.Actions.Input("+"))
                calculatorViewModel.setAction(CalculatorViewModel.Actions.Input("5"))
                expectItem() shouldBe CalculatorViewModel.States.Display("5")
                calculatorViewModel.setAction(CalculatorViewModel.Actions.Input("+"))
                val expectedResult= expectItem()
                expectedResult shouldBe CalculatorViewModel.States.Display("15")

                //substracting 5 from it now
                calculatorViewModel.setAction(CalculatorViewModel.Actions.Input("-"))
                expectItem() shouldBe CalculatorViewModel.States.Display("15")
                calculatorViewModel.setAction(CalculatorViewModel.Actions.Input("5"))
                expectItem() shouldBe CalculatorViewModel.States.Display("5")
                calculatorViewModel.setAction(CalculatorViewModel.Actions.Input("="))
                expectItem() shouldBe CalculatorViewModel.States.Display("10")
                cancel()
            }
        }
    }
})

