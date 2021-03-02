package th.co.dtac.library.textview

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.TypedValue
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import th.co.dtac.library.DtacWidget
import th.co.dtac.library.R

/**
 * Created by PrewSitthirat on 3/1/21.
 */

class DtacContentBlueLabel : DtacLabel {

    constructor(context: Context) : super(context) {
        setFont()
        setupProperties()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        setFont()
        setupProperties()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        setFont()
        setupProperties()
    }

    private fun setupProperties() {
        setTextSize(if (isScale) TypedValue.COMPLEX_UNIT_SP else TypedValue.COMPLEX_UNIT_DIP, 16f)
        setTextColor(ContextCompat.getColor(context, R.color.dtac_telenor_link))
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
}