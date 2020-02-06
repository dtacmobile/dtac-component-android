package th.co.dtac.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import th.co.dtac.library.utils.DtacUtilsKt;

/**
 * @author BabeDev
 */
public class DtacConfirmButton extends AppCompatButton {

    public DtacConfirmButton(Context context) {
        super(context);
        setFont(context);
    }

    public DtacConfirmButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont(context);
    }

    public DtacConfirmButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFont(context);
    }

    private void setFont(Context context) {
        GradientDrawable shape =  new GradientDrawable();
        shape.setCornerRadius(6);
        shape.setColor(ContextCompat.getColor(context, R.color.dtac_telenor_link));
        setTypeface(ResourcesCompat.getFont(context, R.font.dtac2018_regular));
        setTextColor(ContextCompat.getColor(context, R.color.dtac_white));
        setBackgroundDrawable(shape);
        setAllCaps(false);
        setHeight(48);
    }
}
