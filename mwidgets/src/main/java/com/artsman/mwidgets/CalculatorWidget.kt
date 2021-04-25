package com.artsman.mwidgets

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.artsman.mwidgets.databinding.WidgetLayoutCalculatorBinding
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.math.BigDecimal

/**
 * TODO: document your custom view class.
 */
class CalculatorWidget : ConstraintLayout {

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

        a.recycle()
        inflateLayout()
    }

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
    }

    fun Button.applyNumberClick(){
        this.setOnClickListener {
            binding.textView.text=binding.textView.text.toString()+this.text.toString()
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

class Calculator{

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


