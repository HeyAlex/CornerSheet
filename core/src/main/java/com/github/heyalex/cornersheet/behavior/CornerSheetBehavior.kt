package com.github.heyalex.cornersheet.behavior

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.google.android.material.bottomsheet.CornerMaterialSheetBehavior

open class CornerSheetBehavior<V : View> : CornerMaterialSheetBehavior<V> {
    constructor() : super()
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    companion object {
        const val STATE_EXPANDED = 3
        const val STATE_COLLAPSED = 4
        const val STATE_HIDDEN = 5
    }
}