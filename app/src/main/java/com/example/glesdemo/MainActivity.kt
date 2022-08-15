package com.example.glesdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.carlospinan.airhockeytouch.common.GLBaseActivity
import com.carlospinan.airhockeytouch.common.GLBaseRenderer

class MainActivity : GLBaseActivity() {
    override fun setRenderer(): GLBaseRenderer = CarRenderer(this)

    override fun onReady() {

    }

}