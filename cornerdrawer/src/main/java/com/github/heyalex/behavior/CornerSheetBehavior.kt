package com.github.heyalex.behavior

import android.animation.ValueAnimator
import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.IntDef
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import com.github.heyalex.R
import com.github.heyalex.lerp
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel
import java.lang.ref.WeakReference

class CornerSheetBehavior<V : View> :
    BottomSheetBehavior<V> {

    private var horizontalPeekWidth: Int = -1
    private var expandedWidth: Int = 0
    private var expandingRatio: Float = 0.2f
    private var horizontalState: Int = STATE_EXPANDED

    private var sheetBackground: MaterialShapeDrawable? = null

    var viewReference: WeakReference<V>? = null

    @ColorInt
    private var backgroundColor: Int = -1

    constructor() : super()
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.CornerSheetBehavior_Layout)
        horizontalPeekWidth = typedArray.getDimensionPixelSize(
            R.styleable.CornerSheetBehavior_Layout_behavior_horizontal_peekHeight,
            -1
        )

        expandingRatio = typedArray.getFloat(
            R.styleable.CornerSheetBehavior_Layout_behavior_horizontalExpandingRatio,
            0.2f
        )

        expandedWidth = typedArray.getDimensionPixelSize(
            R.styleable.CornerSheetBehavior_Layout_behavior_expanded_width,
            0
        )

        backgroundColor = typedArray.getColor(
            R.styleable.CornerSheetBehavior_Layout_background_shape_color,
            -1
        )

        sheetBackground = MaterialShapeDrawable(
            ShapeAppearanceModel.builder(
                context,
                attrs,
                R.attr.bottomSheetStyle,
                0
            ).build()
        )


        if (backgroundColor != -1) {
            sheetBackground?.fillColor = ColorStateList.valueOf(backgroundColor)
        } else {
            val defaultColor = TypedValue()
            context.theme.resolveAttribute(android.R.attr.colorBackground, defaultColor, true)
            sheetBackground?.setTint(defaultColor.data)
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
        if (viewReference != null && viewReference!!.get() != null) {
            unit.invoke(viewReference!!.get()!!)
        }
    }

    override fun onLayoutChild(parent: CoordinatorLayout, child: V, layoutDirection: Int): Boolean {
        val onLayoutChildResult = super.onLayoutChild(parent, child, layoutDirection)
        if (viewReference == null) {
            viewReference = WeakReference(child)

            ViewCompat.setBackground(child, sheetBackground)

            val invertExpandedValue = child.width - expandedWidth.toFloat()
            if (state == BottomSheetBehavior.STATE_EXPANDED) {
                child.translationX = 0f
                sheetBackground?.interpolation = 0f
            } else {
                child.translationX = invertExpandedValue
                sheetBackground?.interpolation = 1f
            }


            addBottomSheetCallback(object :
                BottomSheetCallback() {
                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    child.translationX =
                        lerp(invertExpandedValue, 0f, 0f, expandingRatio, slideOffset)
                    sheetBackground?.interpolation = lerp(1f, 0f, 0f, expandingRatio, slideOffset)
                }

                override fun onStateChanged(bottomSheet: View, newState: Int) {
                }
            })
        }

        return onLayoutChildResult
    }

    override fun onTouchEvent(parent: CoordinatorLayout, child: V, event: MotionEvent): Boolean {
        return if (event.x < child.translationX) {
            if (state == STATE_DRAGGING) {
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

                val start = when (state) {
                    STATE_COLLAPSED -> collapsed
                    STATE_EXPANDED -> expandedState
                    else -> expandedState
                }

                val end = when (state) {
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

    override fun onAttachedToLayoutParams(layoutParams: CoordinatorLayout.LayoutParams) {
        super.onAttachedToLayoutParams(layoutParams)
        viewReference = null
    }

    override fun onDetachedFromLayoutParams() {
        super.onDetachedFromLayoutParams()
        viewReference = null
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
