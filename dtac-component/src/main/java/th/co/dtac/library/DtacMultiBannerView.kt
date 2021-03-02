package th.co.dtac.library

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Handler
import android.os.Looper.getMainLooper
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.bumptech.glide.Glide
import com.rd.PageIndicatorView
import java.io.File
import java.lang.Exception

/**
 * Created by PrewSitthirat on 2/23/21.
 */

class DtacMultiBannerView : FrameLayout {

    var interval: Long = 0
    var isReverse: Boolean = false
    var isAutoScroll: Boolean = false
        set(value) {
            field = value
            if (value) {
                start()
            } else {
                stop()
            }
        }
    var list: List<Any>? = null
        set(value) {
            field = value
            stop()
            adapter.notifyDataSetChanged()
            if (value.isNullOrEmpty()) {
                pageIndicatorView.count = value?.size ?: 0
            } else {
                pageIndicatorView.count = value.size
                viewPager.currentItem = 0
                start()
            }
        }


    private var isPause = false

    constructor(context: Context) : super(context) {
        initInflate()
        initInstance()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initInflate()
        initInstance()
        initAttrs(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initInflate()
        initInstance()
        initAttrs(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        initInflate()
        initInstance()
        initAttrs(attrs)
    }

    private fun initAttrs(attrs: AttributeSet?) {
        attrs?.let {
            val typeArray = context.obtainStyledAttributes(attrs, R.styleable.DtacMultiBannerView)
            interval = typeArray.getInt(R.styleable.DtacMultiBannerView_interval, 2000).toLong()
            isAutoScroll = typeArray.getBoolean(R.styleable.DtacMultiBannerView_isAutoScroll, false)
            isReverse = typeArray.getBoolean(R.styleable.DtacMultiBannerView_isReverse, false)
            typeArray.recycle()
        }
    }

    private fun initInflate() {
        View.inflate(context, R.layout.view_multi_hero_view, this)
    }

    private lateinit var viewPager: ViewPager2
    private lateinit var pageIndicatorView: PageIndicatorView
    private lateinit var adapter: ViewPagerAdapter
    private var pageChangeListener: ((Int) -> Unit)? = null
    private var onBannerClickListener: ((Int) -> Unit)? = null
    private var isFirstTime: Boolean = true
    private var mHandler: Handler = Handler(getMainLooper())

    private fun initInstance() {
        viewPager = findViewById(R.id.viewPager)
        pageIndicatorView = findViewById(R.id.pageIndicatorView)
        adapter = ViewPagerAdapter()
        viewPager.adapter = adapter
        pageIndicatorView.count = 0
        setPageIndicatorWithViewPager()
    }

    fun setupWithLifeCycle(lifecycle: LifecycleOwner?) {
        lifecycle?.lifecycle?.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                Log.d("DtacMultiBannerView", "onStateChanged: ${event}")
                when (event) {
                    Lifecycle.Event.ON_PAUSE -> {
                        onPause()
                    }
                    Lifecycle.Event.ON_RESUME -> {
                        onResume()
                    }
                    else -> {

                    }
                }
            }
        })
    }

    private fun setPageIndicatorWithViewPager() {
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                pageIndicatorView.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                pageIndicatorView.onPageSelected(position)
                if (isFirstTime) {
                    isFirstTime = false
                } else {
                    pageChangeListener?.invoke(position)
                }
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                pageIndicatorView.onPageScrollStateChanged(state)
                when (state) {
                    ViewPager2.SCROLL_STATE_DRAGGING -> {
                        stop()
                    }
                    ViewPager2.SCROLL_STATE_IDLE -> {
                        stop()
                        start()
                    }
                }
            }
        })
    }

    fun setPageChangeListener(listener: ((Int) -> Unit)?) {
        this.pageChangeListener = listener
    }

    fun setOnBannerClickListener(listener: ((Int) -> Unit)?) {
        this.onBannerClickListener = listener
    }

    private fun start() {
        if (interval > 0 && !isPause) {
            mHandler.postDelayed({
                changeBannerPosition(interval)
            }, 0)
        }
    }

    private fun stop() {
        mHandler.removeCallbacksAndMessages(null)
    }

    private fun changeBannerPosition(millisecond: Long) {
        if (adapter.itemCount > 1) {
            mHandler.postDelayed({
                if (isReverse) {
                    val nextItemPosition = viewPager.currentItem - 1
                    if (nextItemPosition < 0) {
                        viewPager.setCurrentItem(adapter.itemCount - 1, true)
                    } else {
                        viewPager.setCurrentItem(nextItemPosition, true)
                    }
                    changeBannerPosition(millisecond)
                } else {
                    val nextItemPosition = viewPager.currentItem + 1
                    if (nextItemPosition < adapter.itemCount) {
                        viewPager.setCurrentItem(nextItemPosition, true)
                    } else {
                        viewPager.setCurrentItem(0, true)
                    }
                    changeBannerPosition(millisecond)
                }
            }, millisecond)
        }
    }

    fun onPause() {
        isPause = true
        stop()
    }

    fun onResume() {
        stop()
        isPause = false
        if (isAutoScroll) {
            start()
        }
    }

    inner class ViewPagerAdapter : RecyclerView.Adapter<ViewPagerViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_multi_hero, parent, false)
            return ViewPagerViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
            holder.bindView(position, list!![position])
        }

        override fun getItemCount(): Int = list?.size ?: 0
    }

    inner class ViewPagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val ivImage: AppCompatImageView = itemView.findViewById(R.id.ivImage)
        private val lottieView: LottieAnimationView = itemView.findViewById(R.id.lottieView)

        fun bindView(position: Int, data: Any) {
            when (data) {
                is String, is Drawable, is Bitmap, is File -> {
                    setImageFromUrl(data, position)
                }
            }
        }

        private fun setImageFromUrl(url: Any, position: Int) {
            lottieView.visibility = View.GONE
            ivImage.visibility = View.VISIBLE
            Glide.with(itemView.context)
                .load(url)
                .placeholder(R.mipmap.ic_placeholder_large)
                .into(ivImage)
            ivImage.setOnClickListener {
                onBannerClickListener?.invoke(position)
            }
        }

        private fun setLottieFromUrl(lottieUrl: String, position: Int) {
            try {
                lottieView.repeatCount = LottieDrawable.INFINITE
                lottieView.setAnimationFromUrl(lottieUrl)
                lottieView.visibility = View.VISIBLE
                ivImage.visibility = View.GONE
                lottieView.setFailureListener {
                    lottieView.visibility = View.GONE
                }
                lottieView.playAnimation()
                ivImage.setOnClickListener {
                    onBannerClickListener?.invoke(position)
                }
            } catch (e: Exception) {

            }
        }
    }
}