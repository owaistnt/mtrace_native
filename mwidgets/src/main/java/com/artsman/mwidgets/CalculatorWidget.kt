package com.artsman.mwidgets

import android.content.Context
import android.util.AttributeSet
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import com.artsman.mwidgets.databinding.WidgetLayoutCalculatorBinding
import dagger.hilt.EntryPoint
import dagger.hilt.EntryPoints
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.components.ViewComponent
import dagger.hilt.android.components.ViewWithFragmentComponent
import dagger.hilt.android.scopes.ViewScoped
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.math.BigDecimal
import javax.inject.Inject
import javax.inject.Singleton

/**
 * TODO: document your custom view class.
 */
@AndroidEntryPoint
class CalculatorWidget : ConstraintLayout {

   /* @EntryPoint
    @InstallIn(ViewComponent::class)
    interface WidgetEntryPoint {
        @ViewScoped
        fun getViewModel(): CalculatorViewModel
    }*/

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(attrs, defStyle)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {
        // Load attributes
        val a = context.obtainStyledAttributes(
            attrs, R.styleable.CalculatorWidget, defStyle, 0
        )
        //viewModel=EntryPoints.get(context.applicationContext, WidgetEntryPoint::class.java).getViewModel()
        a.recycle()
        inflateLayout()
    }
    @Inject
    lateinit var viewModel: CalculatorViewModel
    lateinit var binding : WidgetLayoutCalculatorBinding
    fun inflateLayout(){
        val rootView=inflate(context, R.layout.widget_layout_calculator, this)
        binding=WidgetLayoutCalculatorBinding.bind(rootView)

        binding.run {
            btn0.applyNumberClick()
            btn1.applyNumberClick()
            btn2.applyNumberClick()
            btn3.applyNumberClick()
            btn4.applyNumberClick()
            btn5.applyNumberClick()
            btn6.applyNumberClick()
            btn7.applyNumberClick()
            btn8.applyNumberClick()
            btn9.applyNumberClick()
        }
        val launch = GlobalScope.launch {
            viewModel.subscribe().collect {
                    when(it){
                        is CalculatorViewModel.States.Display -> binding.textView.text=it.value
                    }
            }
        }
    }

    fun Button.applyNumberClick(){
        this.setOnClickListener {
            viewModel.setAction(CalculatorViewModel.Actions.Input(this.text.toString()))
        }
    }
    private var mPreviousValue: Long=0
    private var mPreviousOperator= OPERATOR.NONE
    fun Button.applyOperator(){
        val currentOperator=getOperator(this.text.toString())
    }

    fun getOperator(text: String): OPERATOR {
        return when(text){
            "+"-> OPERATOR.ADD
            "/"->OPERATOR.DIV
            "*"->OPERATOR.PROD
            "-"->OPERATOR.SUB
            else -> OPERATOR.NONE
        }
    }

    enum class OPERATOR{
        ADD, SUB, PROD, DIV, NONE
    }

}

class Calculator @Inject constructor(){

    fun calculate(a: BigDecimal, b: BigDecimal, operator: CalculatorWidget.OPERATOR): BigDecimal {
        return when(operator){
            CalculatorWidget.OPERATOR.ADD -> a+b
            CalculatorWidget.OPERATOR.SUB -> a-b
            CalculatorWidget.OPERATOR.PROD -> a*b
            CalculatorWidget.OPERATOR.DIV -> a/b
            CalculatorWidget.OPERATOR.NONE -> BigDecimal.ZERO
        }
    }
}


