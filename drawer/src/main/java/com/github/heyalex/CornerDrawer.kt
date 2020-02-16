package com.github.heyalex

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.WindowInsets
import android.widget.FrameLayout
import androidx.annotation.ColorInt
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat

open class CornerDrawer : FrameLayout {

    @LayoutRes
    protected var headerViewRes: Int = 0

    @LayoutRes
    private var contentViewRes: Int = 0

    @ColorInt
    internal var contentColor: Int = 0

    @ColorInt
    internal var headerColor: Int = 0

    internal val container: FrameLayout
    internal var header: View? = null
    internal var content: View? = null

    internal var bottomInset: Int = 0
    internal var topInset: Int = 0

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int)
        : super(context, attrs, defStyleAttr) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CornerDrawer)

        headerViewRes =
            typedArray.getResourceId(R.styleable.CornerDrawer_header_view, View.NO_ID)

        contentViewRes =
            typedArray.getResourceId(R.styleable.CornerDrawer_content_view, View.NO_ID)

        headerColor = typedArray.getColor(
            R.styleable.CornerDrawer_header_color,
            ContextCompat.getColor(context, R.color.corner_drawer_transparent)
        )

        contentColor = typedArray.getColor(
            R.styleable.CornerDrawer_content_color,
            ContextCompat.getColor(context, R.color.corner_drawer_transparent)
        )

        typedArray.recycle()

        if (headerViewRes != View.NO_ID) {
            header = LayoutInflater.from(context).inflate(headerViewRes, null).apply {
                layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
            }
        }

        if (contentViewRes != View.NO_ID) {
            content = LayoutInflater.from(context).inflate(contentViewRes, null)
        }

        container = FrameLayout(context).apply {
            layoutParams =
                LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        }
        super.addView(header)
        super.addView(container)
        addView(content)
    }

    override fun addView(child: View?) {
        container.addView(child)
    }

    /**
     * Handle all touches, to not let intercept touches views behind
     */
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return true
    }

    override fun onApplyWindowInsets(insets: WindowInsets): WindowInsets {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            bottomInset = insets.systemWindowInsetBottom
            topInset = insets.systemWindowInsetTop
        }
        return super.onApplyWindowInsets(insets)
    }
}