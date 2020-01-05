package com.github.heyalex

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import androidx.core.view.doOnLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.shape.CornerFamily
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

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    private val bottomSheetBehavior = CornerDrawerBehaivor<FrameLayout>()
    private var appearanceModel: ShapeAppearanceModel = ShapeAppearanceModel()

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int)
        : super(context, attrs, defStyleAttr) {
//        val params = layoutParams as CoordinatorLayout.LayoutParams
//        val imageView = ImageView(context)
//        params.behavior = bottomSheetBehavior

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CornerDrawer)
        headerViewRes =
            typedArray.getResourceId(R.styleable.CornerDrawer_header_view, View.NO_ID)

        contentViewRes =
            typedArray.getResourceId(R.styleable.CornerDrawer_content_view, View.NO_ID)
        typedArray.recycle()

        header = LayoutInflater.from(context).inflate(headerViewRes, null).apply {
            layoutParams =
                FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        }

        content = LayoutInflater.from(context).inflate(contentViewRes, null).apply {
            layoutParams =
                FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        }

        val color = Color.RED
        val sheetBackground = MaterialShapeDrawable().apply {
            shapeAppearanceModel = appearanceModel.toBuilder().apply {
                setTopLeftCorner(CornerFamily.CUT, 50f)
            }.build()
            setTint(color)
            paintStyle = Paint.Style.FILL
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
            bottomSheetBehavior.peekHeight = header.height
            val maxTranslationX = (width - header.width).toFloat()
            translationX =
                lerp(maxTranslationX, 0f, 0f, 0.15f, 0f)
            container.alpha = lerp(0f, 1f, 0.2f, 0.8f, 0f)

            bottomSheetBehavior.addBottomSheetCallback(object :
                BottomSheetBehavior.BottomSheetCallback() {
                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    translationX =
                        lerp(maxTranslationX, 0f, 0f, 0.15f, slideOffset)
                    sheetBackground.interpolation = lerp(1f, 0f, 0f, 0.15f, slideOffset)
                    sheetBackground.fillColor = ColorStateList.valueOf(
                        lerpArgb(
                            color,
                            Color.TRANSPARENT,
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

//    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
//        return super.onInterceptTouchEvent(ev)
//    }
//
//    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
//        if(event != null && event.x < translationX) {
//            Log.d("check", "onTouchEvent")
//            return true
//        }
//        return super.dispatchTouchEvent(event)
//    }

    override fun addView(child: View?) {
        container.addView(child)
    }
}