package com.github.heyalex

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.doOnLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel

class CornerDrawer : FrameLayout {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

//    private val bottomSheetBehavior = BottomSheetBehavior<FrameLayout>()
    private var appearanceModel: ShapeAppearanceModel = ShapeAppearanceModel()

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int)
        : super(context, attrs, defStyleAttr) {
//        val params = layoutParams as CoordinatorLayout.LayoutParams
//        val imageView = ImageView(context)
//        params.behavior = bottomSheetBehavior
        val color = Color.RED
        val sheetBackground = MaterialShapeDrawable().apply {
            shapeAppearanceModel = appearanceModel.toBuilder().apply {
                setTopLeftCorner(CornerFamily.CUT, 150f)
            }.build()
            setTint(color)
            paintStyle = Paint.Style.FILL
        }
        background = sheetBackground
//        bottomSheetBehavior.peekHeight = 400


        doOnLayout {
            val bottomSheetBehavior = BottomSheetBehavior.from(this)
            Log.d("check", "doOnLayout")
            val maxTranslationX = (width - bottomSheetBehavior.peekHeight).toFloat()
            translationX =
                lerp(maxTranslationX, 0f, 0f, 0.15f, 0f)

            bottomSheetBehavior.addBottomSheetCallback(object :
                BottomSheetBehavior.BottomSheetCallback() {
                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    Log.d("check", "onSlide")
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
                }

                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    Log.d("check", "onStateChanged")
                }
            })
        }
    }
}