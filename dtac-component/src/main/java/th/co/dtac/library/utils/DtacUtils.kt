package th.co.dtac.library.utils

import android.app.Activity
import android.content.Context
import android.util.TypedValue

fun convertToDP(size: Int, context: Activity): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, size.toFloat(), context.resources
            .displayMetrics
    ).toInt()
}

fun convertToDP(size: Int, context: Context): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, size.toFloat(), context.resources
            .displayMetrics
    ).toInt()
}