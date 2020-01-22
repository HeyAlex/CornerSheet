package com.github.heyalex.behavior

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.google.android.material.bottomsheet.ExtendedBehavior

class CornerSheetBehavior<V: View> :
    ExtendedBehavior<V> {
    constructor() : super()
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
}