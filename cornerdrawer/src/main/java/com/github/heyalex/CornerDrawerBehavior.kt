package com.github.heyalex

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior

class CornerDrawerBehavior<T : View>(context: Context, attrs: AttributeSet?) :
    BottomSheetBehavior<T>(context, attrs) {

    //TODO MAKE ALTERNATIVE OF BEHAIVOR SET HORIZONTAL PEEK HEIGHT FROM THIS SIDE
    //TODO MAKE ANALOG OF viewRef

    override fun onTouchEvent(parent: CoordinatorLayout, child: T, event: MotionEvent): Boolean {
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
