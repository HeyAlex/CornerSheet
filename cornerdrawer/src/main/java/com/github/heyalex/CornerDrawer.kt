package com.github.heyalex

import android.content.Context
import android.content.res.ColorStateList
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.WindowInsets
import android.widget.FrameLayout
import androidx.annotation.ColorInt
import androidx.annotation.LayoutRes
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.core.view.doOnLayout
import com.github.heyalex.behavior.CornerSheetBehavior
import com.github.heyalex.behavior.CornerSheetHeaderBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior

open class CornerDrawer : FrameLayout {//, CoordinatorLayout.AttachedBehavior {

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
//    private var attributeSet: AttributeSet? = null

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int)
        : super(context, attrs, defStyleAttr) {
//        attributeSet = attrs
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

    override fun onApplyWindowInsets(insets: WindowInsets): WindowInsets {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            bottomInset = insets.systemWindowInsetBottom
            topInset = insets.systemWindowInsetTop
        }
        return super.onApplyWindowInsets(insets)
    }

//    override fun getBehavior(): CoordinatorLayout.Behavior<*> {
//        return attributeSet?.let {
//            val  attrs = layoutParams as CoordinatorLayout.LayoutParams
//            CornerSheetHeaderBehavior<View>(context, it).also { behavior ->
//                behavior.container = container
//                behavior.content = content
//                behavior.header = header
//                behavior.contentColor = contentColor
//                behavior.headerColor = headerColor
////                this.setTag(R.id.behavior_tag, behavior)
//            }
//        } ?:CornerSheetHeaderBehavior<View>()
//    }

//    override fun onSaveInstanceState(): Parcelable {
//        val superState = super.onSaveInstanceState()
//        superState?.let {
//            val customViewSavedState = CornerDrawerSavedState(superState)
//            val bottomSheetBehavior = BottomSheetBehavior.from(this)
//            customViewSavedState.isExpanded = bottomSheetBehavior.state ==
//                BottomSheetBehavior.STATE_EXPANDED
//            return customViewSavedState
//        }
//        return superState
//    }
//
//    override fun onRestoreInstanceState(state: Parcelable) {
//        val customViewSavedState = state as CornerDrawerSavedState
//        if (customViewSavedState.isExpanded) {
//            isExpanded = true
//            BottomSheetBehavior.from(this).state = BottomSheetBehavior.STATE_EXPANDED
//        }
//        super.onRestoreInstanceState(customViewSavedState.superState)
//    }
//
//    protected class CornerDrawerSavedState : BaseSavedState {
//
//        internal var isExpanded: Boolean = false
//
//        constructor(superState: Parcelable) : super(superState)
//
//        private constructor(source: Parcel) : super(source) {
//            isExpanded = source.readInt() == 1
//        }
//
//        override fun writeToParcel(out: Parcel, flags: Int) {
//            super.writeToParcel(out, flags)
//            out.writeInt(if (isExpanded) 1 else 0)
//        }
//
//        companion object CREATOR : Parcelable.Creator<CornerDrawerSavedState> {
//            override fun createFromParcel(source: Parcel): CornerDrawerSavedState {
//                return CornerDrawerSavedState(source)
//            }
//
//            override fun newArray(size: Int): Array<CornerDrawerSavedState?> {
//                return arrayOfNulls(size)
//            }
//        }
//
//        override fun describeContents(): Int {
//            return 0
//        }
//    }
}