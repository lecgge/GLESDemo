package com.example.glesdemo

import android.content.Context
import android.opengl.GLES20.*
import android.opengl.Matrix
import com.carlospinan.airhockeytouch.common.GLBaseRenderer
import com.example.glesdemo.ext.MatrixHelper
import com.example.glesdemo.ext.loadTexture
import com.example.glesdemo.objects.Car
import com.example.glesdemo.programs.TextureShaderProgram
import com.example.glesdemo.utils.OpenGLES20
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class CarRenderer(val context: Context): GLBaseRenderer() {

    /**
     * 透视投影矩阵
     */
    private val projectionMatrix: FloatArray = FloatArray(16)

    /**
     * 模型矩阵
     */
    private val modelMatrix = FloatArray(16)

    private lateinit var textureShaderProgram: TextureShaderProgram

    private lateinit var car: Car

    private var texture: Int = 0


    override fun onSurfaceCreated(unused: GL10?, eglConfig: EGLConfig?) {
        glClearColor(0f,0f,0f,1f)
        car = Car()

        textureShaderProgram = TextureShaderProgram(context)

        texture = context.loadTexture(R.drawable.img)
    }

    override fun onSurfaceChanged(unused: GL10?, width: Int, height: Int) {
        OpenGLES20.gl2ViewPort(0,0,width, height)

        // 创建透视投影
        MatrixHelper.perspectivew(
            projectionMatrix,
            55f,
            width.toFloat() / height.toFloat(),
            1f,
            10f
        )
        //调整模型矩阵
        // 定义模型矩阵
        Matrix.setIdentityM(modelMatrix, 0)
        //在上面基础上,在Z轴平移-2.5个单位
        Matrix.translateM(modelMatrix, 0, 0f, 0f, -2.5f)
        Matrix.rotateM(modelMatrix, 0, -50f, 1f, 0f, 0f)

        val temp = FloatArray(16)
        //矩阵相乘
        Matrix.multiplyMM(temp, 0, projectionMatrix, 0, modelMatrix, 0)
        System.arraycopy(temp, 0, projectionMatrix, 0, temp.size)


    }

    override fun onDrawFrame(unused: GL10?) {
        glClear(GL_COLOR_BUFFER_BIT)

        textureShaderProgram.useProgram()
        textureShaderProgram.setUniforms(projectionMatrix, texture)
        car.bindData(textureShaderProgram)
        car.draw()


    }
}