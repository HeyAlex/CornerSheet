package com.github.heyalex.cornerdrawer.example.support

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.FragmentActivity
import com.github.heyalex.cornerdrawer.example.R
import com.github.heyalex.cornerdrawer.example.support.shop.ShopFragment

class ShopActivity : FragmentActivity() {

    lateinit var supportFragment: SupportFragment

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

        supportFragment =
            supportFragmentManager.findFragmentById(R.id.support_fragment) as SupportFragment

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(
                    R.id.fragment_container,
                    ShopFragment(), "shop"
                )
                .commit()
        }
    }

    override fun onBackPressed() {
        supportFragment.onBackPressed()
        super.onBackPressed()
    }
}