package com.lzy.studysource.drawable

import android.graphics.Color
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.lzy.studysource.R

class DrawableActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drawable)
        val iv = findViewById<ImageView>(R.id.iv)
//        iv.setImageLevel(1)
        iv.setImageLevel(8)

        val tv = findViewById<TextView>(R.id.tv)
        tv.background.level = 2

        val custom = findViewById<TextView>(R.id.custom)
        custom.background = CustomDrawable(Color.BLUE)
    }
}