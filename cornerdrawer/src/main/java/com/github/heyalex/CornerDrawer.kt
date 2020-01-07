package com.github.heyalex

import android.content.Context
import android.content.res.ColorStateList
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.core.view.doOnLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.SAVE_ALL
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel

class CornerDrawer : FrameLayout {

    @LayoutRes
    private var headerViewRes: Int = 0

    @LayoutRes
    private var contentViewRes: Int = 0

    private val container: FrameLayout
    private val header: View
    private val content: View

    private var isExpanded: Boolean = false

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int)
        : super(context, attrs, defStyleAttr) {

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CornerDrawer)
        headerViewRes =
            typedArray.getResourceId(R.styleable.CornerDrawer_header_view, View.NO_ID)

        contentViewRes =
            typedArray.getResourceId(R.styleable.CornerDrawer_content_view, View.NO_ID)

        val headerColor = typedArray.getColor(
            R.styleable.CornerDrawer_header_color,
            ContextCompat.getColor(context, R.color.corner_drawer_transparent)
        )

        val contentColor = typedArray.getColor(
            R.styleable.CornerDrawer_content_color,
            ContextCompat.getColor(context, R.color.corner_drawer_transparent)
        )

        typedArray.recycle()

        header = LayoutInflater.from(context).inflate(headerViewRes, null).apply {
            layoutParams =
                FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        }

        content = LayoutInflater.from(context).inflate(contentViewRes, null).apply {
            layoutParams =
                FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        }

        val sheetBackground = MaterialShapeDrawable(
            ShapeAppearanceModel.builder(
                context,
                attrs,
                R.attr.bottomSheetStyle,
                0
            ).build()
        ).apply {
            fillColor = ColorStateList.valueOf(headerColor)
        }

        background = sheetBackground

        container = FrameLayout(context).apply {
            layoutParams =
                FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        }
        super.addView(header)
        super.addView(container)
        addView(content)

        doOnLayout {
            val bottomSheetBehavior = BottomSheetBehavior.from(this)
            bottomSheetBehavior.saveFlags = SAVE_ALL
            bottomSheetBehavior.peekHeight = header.height
            val maxTranslationX = (width - header.width).toFloat()
            if (isExpanded) {
                translationX = 0f
                container.alpha = 1f
                header.alpha = 0f
                sheetBackground.fillColor = ColorStateList.valueOf(contentColor)
                sheetBackground.interpolation = 0f
            } else {
                translationX = maxTranslationX
                container.alpha = 0f
            }

            bottomSheetBehavior.addBottomSheetCallback(object :
                BottomSheetBehavior.BottomSheetCallback() {
                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    translationX =
                        lerp(maxTranslationX, 0f, 0f, 0.15f, slideOffset)
                    sheetBackground.interpolation = lerp(1f, 0f, 0f, 0.15f, slideOffset)
                    sheetBackground.fillColor = ColorStateList.valueOf(
                        lerpArgb(
                            headerColor,
                            contentColor,
                            0f,
                            0.3f,
                            slideOffset
                        )
                    )

                    header.alpha = lerp(1f, 0f, 0f, 0.15f, slideOffset)
                    header.visibility = if (slideOffset < 0.5) View.VISIBLE else View.GONE
                    container.alpha = lerp(0f, 1f, 0.2f, 0.8f, slideOffset)
                }

                override fun onStateChanged(bottomSheet: View, newState: Int) {
                }
            })
        }
    }

    override fun onSaveInstanceState(): Parcelable {
        val superState = super.onSaveInstanceState()
        superState?.let {
            val customViewSavedState = CornerDrawerSavedState(superState)
            customViewSavedState.isExpanded = BottomSheetBehavior.from(this).state ==
                BottomSheetBehavior.STATE_EXPANDED
            return customViewSavedState
        }
        return superState
    }


    override fun onRestoreInstanceState(state: Parcelable) {
        val customViewSavedState = state as CornerDrawerSavedState
        if (customViewSavedState.isExpanded) {
            isExpanded = true
            BottomSheetBehavior.from(this).state = BottomSheetBehavior.STATE_EXPANDED
        }
        super.onRestoreInstanceState(customViewSavedState.superState)
    }

    private class CornerDrawerSavedState : BaseSavedState {

        internal var isExpanded: Boolean = false

        constructor(superState: Parcelable) : super(superState)

        private constructor(source: Parcel) : super(source) {
            isExpanded = source.readInt() == 1
        }

        override fun writeToParcel(out: Parcel, flags: Int) {
            super.writeToParcel(out, flags)
            out.writeInt(if (isExpanded) 1 else 0)
        }

        companion object CREATOR : Parcelable.Creator<CornerDrawerSavedState> {
            override fun createFromParcel(source: Parcel): CornerDrawerSavedState {
                return CornerDrawerSavedState(source)
            }

            override fun newArray(size: Int): Array<CornerDrawerSavedState?> {
                return arrayOfNulls(size)
            }
        }

        override fun describeContents(): Int {
            return 0
        }
    }

    override fun addView(child: View?) {
        container.addView(child)
    }
}