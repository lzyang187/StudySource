package com.lzy.studysource.materialdesign.transition

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.util.Pair
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.lzy.studysource.R

/**
 * Activity过渡动画
 * author: zyli44
 * date: 2021/8/21 19:44
 */
class TransitionActivityA : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transition_activity)
    }

    fun explode(view: View) {
        val intent = Intent(this, TransitionActivityB::class.java)
        intent.putExtra("flag", 0)
        //
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
    }

    fun slide(view: View) {
        val intent = Intent(this, TransitionActivityB::class.java)
        intent.putExtra("flag", 1)
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())

    }

    fun fade(view: View) {
        val intent = Intent(this, TransitionActivityB::class.java)
        intent.putExtra("flag", 2)
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())

    }

    fun share(view: View) {
        val fab = findViewById<Button>(R.id.fab_btn)
        val intent = Intent(this, TransitionActivityB::class.java)
        intent.putExtra("flag", "3")
        startActivity(
            intent, ActivityOptions.makeSceneTransitionAnimation(
                this,
                // 创建多个共享元素
                Pair.create(view, "share"),
                Pair.create(fab, "fab")
            ).toBundle()
        )
    }
}