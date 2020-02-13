package com.github.heyalex.cornersheet

import androidx.annotation.ColorInt
import androidx.annotation.FloatRange
import com.google.android.material.animation.ArgbEvaluatorCompat

fun interpolate(
    startValue: Float,
    endValue: Float,
    @FloatRange(from = 0.0, to = 1.0) fraction: Float
): Float {
    return startValue + fraction * (endValue - startValue)
}

fun interpolate(
    startValue: Float,
    endValue: Float,
    @FloatRange(
        from = 0.0,
        fromInclusive = true,
        to = 1.0,
        toInclusive = true
    ) startFraction: Float,
    @FloatRange(from = 0.0, to = 1.0) endFraction: Float,
    @FloatRange(from = 0.0, to = 1.0) fraction: Float
): Float {
    if (fraction < startFraction) return startValue
    if (fraction > endFraction) return endValue

    return interpolate(
        startValue,
        endValue,
        (fraction - startFraction) / (endFraction - startFraction)
    )
}

@ColorInt
fun interpolateArgb(
    @ColorInt startColor: Int,
    @ColorInt endColor: Int,
    @FloatRange(
        from = 0.0,
        fromInclusive = true,
        to = 1.0,
        toInclusive = true
    ) startFraction: Float,
    @FloatRange(from = 0.0, to = 1.0) endFraction: Float,
    @FloatRange(from = 0.0, to = 1.0) fraction: Float
): Int {
    if (fraction < startFraction) return startColor
    if (fraction > endFraction) return endColor

    return interpolateArgb(
        startColor,
        endColor,
        (fraction - startFraction) / (endFraction - startFraction)
    )
}

@ColorInt
fun interpolateArgb(
    @ColorInt startColor: Int,
    @ColorInt endColor: Int,
    @FloatRange(from = 0.0, to = 1.0) fraction: Float
): Int {
    return ArgbEvaluatorCompat.getInstance().evaluate(
        fraction,
        startColor,
        endColor
    )
}
