package th.co.dtac.library.textfield

import android.content.Context
import android.text.Editable
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Log
import androidx.appcompat.widget.AppCompatEditText


/**
 * Created by PrewSitthirat on 3/2/21.
 */

class DtacTextField : AppCompatEditText {

    private var listener: ((Boolean) -> Unit)? = null

    constructor(context: Context) : super(context) {
        initAttr(null)
        initProperties()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initAttr(attrs)
        initProperties()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initAttr(attrs)
        initProperties()
    }

    private fun initAttr(attrs: AttributeSet?) {
        attrs?.let {

        }
    }

    private fun initProperties() {
        filters = arrayOf<InputFilter>(LengthFilter(11))
        inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_SIGNED
        this.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                Log.d(this@DtacTextField.javaClass.simpleName, getMSISDN())
                isMSISDN(s)
                listener?.invoke(isMSISDN(s))
            }
        })
    }

    fun addTextConditionsChangeListener(listener: (Boolean) -> Unit) {
        this.listener = listener
    }

    private fun isMSISDN(text: Editable?): Boolean {
        return if (text.isNullOrBlank()) {
            false
        } else {
            when {
                text.startsWith("0") -> {
                    text.length == 10
                }
                text.startsWith("66") -> {
                    if ("\\d{11}".toRegex().matches(text)) {
                        Log.d("Prew", "66 regex true")
                        true
                    } else {
                        Log.d("Prew", "66 regex false")
                        false
                    }

                }
                else -> {
                    false
                }
            }
        }
    }

    fun getMSISDN(): String {
        return if (text != null) {
            if (text!!.startsWith("66")) {
                text.toString()
            } else if (text!!.startsWith("0")) {
                val msisdn = "66" + text?.substring(1, text!!.length)
                msisdn
            } else {
                text?.toString() ?: ""
            }
        } else {
            ""
        }
    }
}