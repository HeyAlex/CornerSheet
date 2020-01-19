package com.github.heyalex.cornerdrawer.example

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatSeekBar
import androidx.appcompat.widget.Toolbar
import com.github.heyalex.CornerDrawer
import com.google.android.material.bottomsheet.CornerSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        R.styleable.BottomSheetBehavior_Layout
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
                window.navigationBarColor = Color.TRANSPARENT

                var flags = it.decorView.systemUiVisibility
                // make dark status bar icons
                flags =
                    flags xor View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    // make dark navigation bar icons
                    flags =
                        flags xor View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
                }
                window.decorView.systemUiVisibility = flags
            }
        }

//        val cornerDrawer = findViewById<CornerDrawer>(R.id.corner_drawer)
//        val behavior = BottomSheetBehavior.from(cornerDrawer) as CornerSheetBehavior

//        val header = findViewById<LinearLayout>(R.id.header_root)
//
//        header.setOnClickListener {
//            behavior.state = BottomSheetBehavior.STATE_EXPANDED
//        }

//        var state = false
//
//        val hello_world = findViewById<TextView>(R.id.hellow_world)
//        hello_world.setOnClickListener {
//            val state1 = if (state) 0 else 1
//            cornerDrawer.setState(state1)
//            state = !state
//        }
//
//        val seekbar = findViewById<AppCompatSeekBar>(R.id.seek_peek_height)
//        seekbar.max = 500
//        seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
//            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
//                behavior.setHorizontalPeekHeight(progress, false)
//            }
//
//            override fun onStartTrackingTouch(seekBar: SeekBar?) {
//            }
//
//            override fun onStopTrackingTouch(seekBar: SeekBar?) {
//            }
//
//        })
//        val toolbar = findViewById<Toolbar>(R.id.toolbar)
//        toolbar.setNavigationOnClickListener {
//            behavior.horizontalState = BottomSheetBehavior.STATE_COLLAPSED
//        }
//
//        var isLight = false
//        behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
//            override fun onSlide(bottomSheet: View, slideOffset: Float) {
//                if (slideOffset < 0.98) {
//                    if (!isLight) {
//                        changeStatusBarIconColor(false)
//                        isLight = true
//                    }
//                } else if (isLight) {
//                    changeStatusBarIconColor(true)
//                    isLight = false
//                }
//            }
//
//            override fun onStateChanged(bottomSheet: View, newState: Int) {
//
//            }
//        })
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