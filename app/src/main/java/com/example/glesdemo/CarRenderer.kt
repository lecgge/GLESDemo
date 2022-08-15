package com.example.glesdemo

import android.content.Context
import android.opengl.GLES20
import android.opengl.GLES20.*
import com.carlospinan.airhockeytouch.common.GLBaseRenderer
import com.example.glesdemo.utils.OpenGLES20
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class CarRenderer(val context: Context): GLBaseRenderer() {
    override fun onSurfaceCreated(unused: GL10?, eglConfig: EGLConfig?) {
        glClearColor(1f,1f,1f,1f)
    }

    override fun onSurfaceChanged(unused: GL10?, width: Int, height: Int) {
        OpenGLES20.gl2ViewPort(0,0,width, height)
    }

    override fun onDrawFrame(unused: GL10?) {
        glClear(GL_COLOR_BUFFER_BIT)
    }
}