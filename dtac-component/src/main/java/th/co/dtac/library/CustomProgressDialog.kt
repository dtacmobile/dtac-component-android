package th.co.dtac.library

import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.Window
import com.airbnb.lottie.LottieAnimationView


class CustomProgressDialog {
    companion object {
        private var dialog: Dialog? = null
        private lateinit var animationView: LottieAnimationView
        @JvmStatic
        fun showProgress(act: Context?, isCancelable: Boolean) {
            try {

                if (dialog != null && dialog!!.isShowing) {
                    dialog?.dismiss()
                    if (animationView.isAnimating) {
                        animationView.pauseAnimation()
                    }
                }
                dialog = Dialog(act!!, R.style.NewDialog)
                dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
                dialog?.setCancelable(isCancelable)
                dialog?.setCanceledOnTouchOutside(isCancelable)
                dialog?.setContentView(R.layout.custom_loading_layout)
                animationView = dialog!!.findViewById(R.id.animation_view)

                animationView.playAnimation()
                dialog?.show()

            } catch (e: Exception) {
                Log.e(this.toString(), e.toString())
            }
        }

        //..also create a method which will hide the dialog when some work is done
        fun hideProgress() {
            try {
                if (dialog != null) {
                    if (animationView.isAnimating) {
                        animationView.pauseAnimation()
                    }
                    dialog?.dismiss()
                }
            } catch (e: Exception) {
                Log.e(this.toString(), e.toString())
            }
        }
    }

}