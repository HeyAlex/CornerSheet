package com.github.heyalex.cornerdrawer.example

import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatSeekBar
import com.github.heyalex.behavior.CornerSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.CornerMaterialSheetBehavior
import kotlinx.android.synthetic.main.activity_main.*

class BehaviorSampleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val cornerDrawer = findViewById<View>(R.id.corner_behavior_container)
        val behavior = BottomSheetBehavior.from(cornerDrawer) as CornerMaterialSheetBehavior

        expand.setOnClickListener {
            behavior.horizontalState = CornerSheetBehavior.STATE_EXPANDED
        }

        collapsed.setOnClickListener {
            behavior.horizontalState = CornerSheetBehavior.STATE_COLLAPSED
        }

        hidden.setOnClickListener {
            behavior.horizontalState = CornerSheetBehavior.STATE_HIDDEN
        }

        val seekbar = findViewById<AppCompatSeekBar>(R.id.seek_peek_height)
        seekbar.max = 800
        seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                behavior.setHorizontalPeekHeight(progress, false)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })
    }
}