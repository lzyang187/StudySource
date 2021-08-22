package com.lzy.studysource.colormatrix.matrix

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.lzy.studysource.R

class ColorMatrixActivity : AppCompatActivity(), SeekBar.OnSeekBarChangeListener {

    companion object {
        private const val TAG = "ColorMatrixActivity"

        const val MAX_VALUE = 255
        const val MID_VALUE = 127
    }

    private val mBitmap by lazy {
        BitmapFactory.decodeResource(resources, R.mipmap.test_filter)
    }
    private lateinit var mImageView: ImageView
    private var mRotate = 0f
    private var mSaturation = 0f
    private var mScale = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_color_matrix)

        mImageView = findViewById(R.id.iv)
        mImageView.setImageBitmap(mBitmap)

        val rotateSeekBar = findViewById<SeekBar>(R.id.rotate_seekbar)
        val saturationSeekBar = findViewById<SeekBar>(R.id.saturation_seekbar)
        val scaleSeekBar = findViewById<SeekBar>(R.id.scale_seekbar)

        rotateSeekBar.setOnSeekBarChangeListener(this)
        saturationSeekBar.setOnSeekBarChangeListener(this)
        scaleSeekBar.setOnSeekBarChangeListener(this)
        rotateSeekBar.max = MAX_VALUE
        saturationSeekBar.max = MAX_VALUE
        scaleSeekBar.max = MAX_VALUE
        rotateSeekBar.progress = MID_VALUE
        saturationSeekBar.progress = MID_VALUE
        scaleSeekBar.progress = MID_VALUE


    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        seekBar?.let {
            when (seekBar.id) {
                R.id.rotate_seekbar -> {
                    mRotate = (progress - MID_VALUE) * 1f / MID_VALUE * 180
                }
                R.id.saturation_seekbar -> {
                    mSaturation = progress * 1f / MID_VALUE
                }
                R.id.scale_seekbar -> {
                    mScale = progress * 1f / MID_VALUE
                }
            }
            Log.e(TAG, "onStopTrackingTouch: $mRotate $mSaturation $mScale")
            mImageView.setImageBitmap(
                ColorMatrixUtil.handleImage(
                    mBitmap,
                    mRotate.toFloat(), mSaturation.toFloat(), mScale.toFloat()
                )
            )
            Log.e(TAG, "onStopTrackingTouch: 处理完成")
        }
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {

    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {

    }


}