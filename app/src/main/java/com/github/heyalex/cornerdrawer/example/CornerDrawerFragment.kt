package com.github.heyalex.cornerdrawer.example

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.heyalex.CornerDrawer
import com.google.android.material.bottomsheet.BottomSheetBehavior

class CornerDrawerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.corner_drawer_fragment, container, false)
        view.findViewById<View>(R.id.header_root).setOnClickListener {
            BottomSheetBehavior.from(view.findViewById<CornerDrawer>(R.id.corner_drawer)).state = BottomSheetBehavior.STATE_EXPANDED
        }
        return view
    }
}