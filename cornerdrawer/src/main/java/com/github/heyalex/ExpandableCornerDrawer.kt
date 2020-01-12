package com.github.heyalex

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.doOnLayout

class ExpandableCornerDrawer : CornerDrawer {

    private var headerIconRes: Drawable?
    private var headerTextRes: CharSequence?

    private val icon: ImageView
    private val text: TextView

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int)
        : super(context, attrs, defStyleAttr) {

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ExpandableCornerDrawer)

        //TODO api for icon
        headerIconRes =
            typedArray.getDrawable(R.styleable.ExpandableCornerDrawer_header_icon)

        //TODO api for text
        headerTextRes =
            typedArray.getText(R.styleable.ExpandableCornerDrawer_header_text)

        typedArray.recycle()


        icon = header.findViewById(R.id.header_icon)
        text = header.findViewById(R.id.header_text)

        icon.setImageDrawable(headerIconRes)
        text.text = headerTextRes
        //doOnLayout не отрабатывает на header в CornerDrawer
    }

    override fun getHeaderStub():Int = R.layout.advanced_header


}