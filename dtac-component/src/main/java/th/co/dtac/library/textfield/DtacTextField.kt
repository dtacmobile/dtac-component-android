package th.co.dtac.library.textfield

import android.content.Context
import android.graphics.Typeface
import android.text.Editable
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.res.ResourcesCompat
import th.co.dtac.library.DtacWidget
import th.co.dtac.library.R


/**
 * Created by PrewSitthirat on 3/2/21.
 */

class DtacTextField : AppCompatEditText {

    private var listener: ((Boolean) -> Unit)? = null
    private var regexType = 0
    private var regex = ""

    companion object {
        private const val REGEX_MSISDN_TYPE = 1
        private const val REGEX_MSISDN = "[6]{2}+\\d{9}"
        private const val REGEX_PHONE_NUM = "[0][\\d]{9}"
        private const val REGEX_EMAIL_TYPE = 2
        private const val REGEX_EMAIL = "[\\w\\.\\+]{1,60}\\@[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}(\\.[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25})+"
    }

    constructor(context: Context) : super(context) {
        initAttr(null)
        setFont()
        initProperties()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initAttr(attrs)
        setFont()
        initProperties()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initAttr(attrs)
        setFont()
        initProperties()
    }

    private fun initAttr(attrs: AttributeSet?) {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.DtacTextField)
            regexType = typedArray.getInt(R.styleable.DtacTextField_regexType, 0)
            typedArray.recycle()
        }
    }

    private fun initProperties() {
        when (regexType) {
            REGEX_MSISDN_TYPE -> {
                filters = arrayOf<InputFilter>(LengthFilter(11))
                inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_SIGNED
                regex = REGEX_MSISDN
            }
            REGEX_EMAIL_TYPE -> {
                filters = arrayOf<InputFilter>(LengthFilter(50))
                inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
                regex = REGEX_EMAIL
            }
            else -> {

            }
        }
        this.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (s.isNullOrBlank()) {
                    listener?.invoke(false)
                } else {
                    if (regexType == REGEX_MSISDN_TYPE) {
                        listener?.invoke(REGEX_MSISDN.toRegex().matches(s) || REGEX_PHONE_NUM.toRegex().matches(s))
                    } else {
                        listener?.invoke(regex.toRegex().matches(s))
                    }
                }
            }
        })
    }

    private fun setFont() {
        val font: Typeface? = when (DtacWidget.currentLanguage().toUpperCase()) {
            "MM", "MY" -> {
                ResourcesCompat.getFont(context, R.font.myanmar_sagar)
            }
            else -> {
                ResourcesCompat.getFont(context, R.font.dtac2018_regular)
            }
        }
        typeface = font ?: ResourcesCompat.getFont(context, R.font.dtac2018_regular)
    }

    fun addTextConditionsChangeListener(listener: (Boolean) -> Unit) {
        this.listener = listener
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