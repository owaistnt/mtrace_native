package com.artsman.mwidgets

import junit.framework.TestCase
import org.junit.Before
import org.junit.Test

class CalculatorViewModelTest : TestCase(){

    lateinit var calculatorViewModel: CalculatorViewModel
    @Before
    fun setup(){
        calculatorViewModel= CalculatorViewModel()
    }
    @Test
    fun givenNoValueOperatorMustReturnSameValue(){

    }
}