package com.google.android.material.bottomsheet

import android.animation.ValueAnimator
import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.IntDef
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import com.github.heyalex.R
import com.github.heyalex.lerp
import com.github.heyalex.lerpArgb
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel

class CornerSheetBehavior<V : View> :
    BottomSheetBehavior<V> {

    private var horizontalPeekWidth: Int = -1
    private var expandedWidth: Int = 0
    private var horizontalState: Int = STATE_EXPANDED

    private var sheetBackground: MaterialShapeDrawable? = null

    @ColorInt
    private var contentColor: Int = 0

    @ColorInt
    private var headerColor: Int = 0

    constructor() : super()
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CornerSheetBehavior_Layout)
        horizontalPeekWidth = typedArray.getDimensionPixelSize(
            R.styleable.CornerSheetBehavior_Layout_behavior_horizontal_peekHeight,
            -1
        )

        expandedWidth = typedArray.getDimensionPixelSize(
            R.styleable.CornerSheetBehavior_Layout_behavior_expanded_width,
            0
        )
//        maxTranslationX = expandedWidth.toFloat()

        headerColor = typedArray.getColor(
            R.styleable.CornerSheetBehavior_Layout_header_color,
            ContextCompat.getColor(context, R.color.corner_drawer_transparent)
        )

        contentColor = typedArray.getColor(
            R.styleable.CornerSheetBehavior_Layout_content_color,
            ContextCompat.getColor(context, R.color.corner_drawer_transparent)
        )

        sheetBackground = MaterialShapeDrawable(
            ShapeAppearanceModel.builder(
                context,
                attrs,
                R.attr.bottomSheetStyle,
                0
            ).build()
        ).apply {
            fillColor = ColorStateList.valueOf(headerColor)
        }

        typedArray.recycle()
    }

    fun setHorizontalPeekHeight(width: Int, animate: Boolean) {
        getView {
            horizontalPeekWidth = width

            if (state == BottomSheetBehavior.STATE_COLLAPSED || horizontalState != STATE_HIDDEN) {
                if (animate) {
                    setHorizontalState(STATE_COLLAPSED)
                } else {
                    expandedWidth = width
                    it.translationX = it.width - width.toFloat()
                }
            }
        }
    }

    fun getView(unit: ((V) -> Unit)) {
        if (viewRef != null && viewRef!!.get() != null) {
            unit.invoke(viewRef!!.get()!!)
        }
    }

    private var isInitialized: Boolean = false

    override fun onLayoutChild(parent: CoordinatorLayout, child: V, layoutDirection: Int): Boolean {
        val onLayoutChildResult = super.onLayoutChild(parent, child, layoutDirection)
        if (!isInitialized) {
            ViewCompat.setBackground(child, sheetBackground)
            isInitialized = true

            if(state == BottomSheetBehavior.STATE_EXPANDED) {
                child.translationX = 0f
                sheetBackground?.fillColor = ColorStateList.valueOf(contentColor)
                sheetBackground?.interpolation = 0f
            } else {
                child.translationX = expandedWidth.toFloat()
            }

            addBottomSheetCallback(object :
                BottomSheetBehavior.BottomSheetCallback() {
                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    child.translationX =
                        lerp(expandedWidth.toFloat(), 0f, 0f, 0.15f, slideOffset)
                    sheetBackground?.interpolation = lerp(1f, 0f, 0f, 0.15f, slideOffset)
                    sheetBackground?.fillColor = ColorStateList.valueOf(
                        lerpArgb(
                            headerColor,
                            contentColor,
                            0f,
                            0.3f,
                            slideOffset
                        )
                    )
                }

                override fun onStateChanged(bottomSheet: View, newState: Int) {
                }
            })
        }

        return onLayoutChildResult
    }

    override fun onTouchEvent(parent: CoordinatorLayout, child: V, event: MotionEvent): Boolean {
        return if (event.x < child.translationX) {
            if (state == BottomSheetBehavior.STATE_DRAGGING) {
                state = BottomSheetBehavior.STATE_COLLAPSED
            }
            false
        } else {
            super.onTouchEvent(parent, child, event)
        }
    }

    fun setHorizontalState(@HorizontalState state: Int) {
        getView {
            ValueAnimator.ofFloat(0f, 1f).apply {
                duration = 150
                val expandedState = (it.width - expandedWidth).toFloat()
                val collapsed = (it.width - horizontalPeekWidth).toFloat()

                val start = when(state) {
                    STATE_COLLAPSED -> collapsed
                    STATE_EXPANDED -> expandedState
                    else -> expandedState
                }

                val end = when(state) {
                    STATE_COLLAPSED -> expandedState
                    STATE_EXPANDED -> collapsed
                    else -> collapsed
                }

                addUpdateListener { animation ->
                    val value = animation.animatedValue as Float
                    it.translationX = lerp(start, end, 0f, 1f, value)
                }

                expandedWidth = end.toInt()
            }.start()
        }
    }

    @IntDef(
        STATE_EXPANDED,
        STATE_COLLAPSED,
        STATE_HIDDEN
    )
    @Retention(AnnotationRetention.SOURCE)
    annotation class HorizontalState

    companion object {
        const val STATE_EXPANDED = 3
        const val STATE_COLLAPSED = 4
        const val STATE_HIDDEN = 5
    }
}
