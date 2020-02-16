package com.github.heyalex.cornerdrawer.example.support

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.github.heyalex.CornerDrawer
import com.github.heyalex.cornerdrawer.example.R
import com.github.heyalex.cornersheet.behavior.CornerSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.support_content.view.*
import kotlinx.android.synthetic.main.support_header.view.*

class SupportFragment : Fragment() {

    private lateinit var backPressedCallback: OnBackPressedCallback
    lateinit var behavior: CornerSheetBehavior<CornerDrawer>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.support_fragment, container, false)
        behavior =
            BottomSheetBehavior.from(view.findViewById<CornerDrawer>(R.id.corner_drawer)) as CornerSheetBehavior<CornerDrawer>

        backPressedCallback = object :
            OnBackPressedCallback(behavior.state == BottomSheetBehavior.STATE_COLLAPSED) {
            override fun handleOnBackPressed() {
                behavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }
        activity?.onBackPressedDispatcher?.addCallback(this, backPressedCallback)

        view.header_root.setOnClickListener {
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.support_toolbar.setNavigationOnClickListener {
                behavior.state = BottomSheetBehavior.STATE_COLLAPSED
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

        return view
    }

    fun changeStatusBarIconColor(isLight: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity?.window?.let {
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

    fun onBackPressed(): Boolean {
        backPressedCallback.isEnabled = behavior.state == BottomSheetBehavior.STATE_EXPANDED
        backPressedCallback.handleOnBackPressed()
        return true
    }
}