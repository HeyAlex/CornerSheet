package com.github.heyalex

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import java.lang.ref.WeakReference

class CornerDrawerBehavior<V : View>(context: Context, attrs: AttributeSet?) :
    BottomSheetBehavior<V>(context, attrs) {

    //TODO MAKE ALTERNATIVE OF BEHAIVOR SET HORIZONTAL PEEK HEIGHT FROM THIS SIDE
    private var cornerDrawerRef: WeakReference<CornerDrawer>? = null
    private var horizontalPeekHeight: Int = -1

    fun setHorizontalPeekHeight(height: Int, animate: Boolean) {
        safeCornerDrawerCall {
            it.horizontalPeekHeight = height

            if (state == BottomSheetBehavior.STATE_COLLAPSED || it.) {
                if (animate) {
                    it.setState(COLLAPSED)
                } else {
                    it.maxTranslationX = height.toFloat()
                    it.translationX = it.width - height.toFloat()
                }
            }
        }
    }

    private fun safeCornerDrawerCall(unit: ((CornerDrawer) -> Unit)) {
        if (cornerDrawerRef != null && cornerDrawerRef!!.get() != null) {
            unit.invoke(cornerDrawerRef!!.get()!!)
        }
    }

    override fun onLayoutChild(parent: CoordinatorLayout, child: V, layoutDirection: Int): Boolean {
        val isDefaultBehavior = super.onLayoutChild(parent, child, layoutDirection)
        if (cornerDrawerRef == null && child is CornerDrawer) {
            cornerDrawerRef = WeakReference(child)
        }
        return isDefaultBehavior
    }

    override fun onAttachedToLayoutParams(layoutParams: CoordinatorLayout.LayoutParams) {
        super.onAttachedToLayoutParams(layoutParams)
        cornerDrawerRef = null
    }

    override fun onDetachedFromLayoutParams() {
        super.onDetachedFromLayoutParams()
        cornerDrawerRef = null
    }

    override fun onTouchEvent(parent: CoordinatorLayout, child: V, event: MotionEvent): Boolean {
        return if (event.x < child.translationX) {
            if (state == STATE_DRAGGING) {
                state = STATE_COLLAPSED
            }
            false
        } else {
            super.onTouchEvent(parent, child, event)
        }
    }

    companion object {
        const val EXPANDED = 0
        const val COLLAPSED = 1
    }
}
