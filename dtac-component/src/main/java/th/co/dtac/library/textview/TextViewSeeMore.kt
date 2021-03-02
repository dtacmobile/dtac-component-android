package th.co.dtac.library.textview

import android.content.Context
import android.text.SpannableString
import android.text.Spanned
import android.text.TextUtils
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatTextView


/**
 * Created by PrewSitthirat on 3/1/21.
 */

class TextViewSeeMore : AppCompatTextView {

    companion object {
        private const val MAX_LINE = 4 + 1
    }

    constructor(context: Context) : super(context) {
        initInstance()
    }
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initInstance()
    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initInstance()
    }

    private fun initInstance() {
        this.maxLines = MAX_LINE
        ellipsize = TextUtils.TruncateAt.END
    }

    fun setTextSeeMore(message: String) {
        this.movementMethod = LinkMovementMethod.getInstance()
        this.text = message
        this.post {
            if (lineCount > (MAX_LINE - 1)) {
                val string = message.substring(this.layout.getLineStart(MAX_LINE - 2), this.layout.getLineEnd(MAX_LINE - 2))
                val spanString = SpannableString(string.substring(0, string.length - 10) + "  See More")
                this.maxLines = MAX_LINE - 1
                val seeMoreText: String = message.substring(0, this.layout.getLineStart(MAX_LINE - 2)) + spanString
                val seeMoreSpan = SpannableString(seeMoreText)
                val clickableSpan: ClickableSpan = object : ClickableSpan() {
                    override fun onClick(widget: View) {
                        this@TextViewSeeMore.maxLines = Integer.MAX_VALUE
                        this@TextViewSeeMore.text = message
                    }
                }
                seeMoreSpan.setSpan(clickableSpan, seeMoreSpan.length - 8, seeMoreSpan.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                text = seeMoreSpan
            }
        }
    }
}