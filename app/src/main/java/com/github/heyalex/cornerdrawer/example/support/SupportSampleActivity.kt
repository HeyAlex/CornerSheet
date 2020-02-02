package com.github.heyalex.cornerdrawer.example.support

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.widget.Toolbar
import com.github.heyalex.behavior.CornerSheetHeaderBehavior
import com.github.heyalex.cornerdrawer.example.R
import com.google.android.material.bottomsheet.BottomSheetBehavior

class SupportSampleActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.support_activity)
        window.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                // for drawing behind status bar
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                //make system bar to be translucent
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE

                //make status bar color transparent
                window.statusBarColor = Color.TRANSPARENT

                var flags = it.decorView.systemUiVisibility
                // make dark status bar icons
                flags =
                    flags xor View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

                window.decorView.systemUiVisibility = flags
            }
        }

        val drawer = findViewById<ViewGroup>(R.id.corner_drawer)
        val behavior = BottomSheetBehavior.from(drawer) as CornerSheetHeaderBehavior
        drawer.findViewById<ViewGroup>(R.id.header_root).setOnClickListener {
            behavior.setState(BottomSheetBehavior.STATE_EXPANDED)
        }

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setNavigationOnClickListener {
                behavior.setState(BottomSheetBehavior.STATE_COLLAPSED)
            }
        }

        var isLight = false
        behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                if (slideOffset < 0.98) {
                    if (!isLight) {
                        changeStatusBarIconColor(false)
                        isLight = true
                    }
                } else if (isLight) {
                    changeStatusBarIconColor(true)
                    isLight = false
                }
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {

            }
        })
    }

    fun changeStatusBarIconColor(isLight: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window?.let {
                var flags = it.decorView.systemUiVisibility
                flags = if (isLight) {
                    flags xor View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                } else {
                    flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                }
                it.decorView.systemUiVisibility = flags
            }
        }
    }
}