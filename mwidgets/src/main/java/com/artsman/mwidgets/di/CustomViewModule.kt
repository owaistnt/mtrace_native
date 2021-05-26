package com.artsman.mwidgets.di

import com.artsman.mwidgets.Calculator
import com.artsman.mwidgets.CalculatorViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewComponent
import dagger.hilt.android.scopes.ViewScoped

@InstallIn(ViewComponent::class)
@Module
class CustomViewModule {
    @Provides
    fun getCalculatorVM(calculator: Calculator)= CalculatorViewModel(calculator)
}
