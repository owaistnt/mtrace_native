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
import com.artsman.mwidgets.databinding.WidgetLayoutCalculatorBinding

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

}