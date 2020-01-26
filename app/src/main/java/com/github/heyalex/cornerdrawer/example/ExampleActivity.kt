package com.github.heyalex.cornerdrawer.example

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button

class ExampleActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.example_activity)

        findViewById<Button>(R.id.main).setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.photo_picker).setOnClickListener {
            val intent = Intent(this, PhotoPickerSampleActivity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.support_sample).setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}