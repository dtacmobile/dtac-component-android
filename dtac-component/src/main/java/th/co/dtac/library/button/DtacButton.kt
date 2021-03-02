package th.co.dtac.library.button

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.res.ResourcesCompat
import th.co.dtac.library.DtacWidget
import th.co.dtac.library.R

/**
 * Created by PrewSitthirat on 6/11/2020 AD.
 */

open class DtacButton : AppCompatButton {

    protected var isScale: Boolean = true

    constructor(context: Context) : super(context) {
        setDefault()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initAttrs(attrs)
        setDefault()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr
    ) {
        initAttrs(attrs)
        setDefault()
    }

    @SuppressLint("CustomViewStyleable")
    private fun initAttrs(attrs: AttributeSet?) {
        val typedArray: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.BaseComponent)
        isScale = typedArray.getBoolean(R.styleable.BaseComponent_isScale, true)
        typedArray.recycle()
    }

    private fun setDefault() {
        isAllCaps = false
        if (text.toString().isBlank()) {
            text = this.javaClass.simpleName
        }
    }
}