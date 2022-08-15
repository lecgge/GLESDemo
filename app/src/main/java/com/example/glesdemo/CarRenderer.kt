package com.example.glesdemo

import android.content.Context
import android.opengl.GLES20
import android.opengl.GLES20.*
import com.carlospinan.airhockeytouch.common.GLBaseRenderer
import com.example.glesdemo.common.BYTES_PER_FLOAT
import com.example.glesdemo.ext.isDebugVersion
import com.example.glesdemo.ext.loadTexture
import com.example.glesdemo.ext.readStringFromRaw
import com.example.glesdemo.objects.Car
import com.example.glesdemo.programs.TextureShaderProgram
import com.example.glesdemo.utils.OpenGLES20
import com.example.glesdemo.utils.ShaderHelper
import java.nio.ByteBuffer
import java.nio.ByteOrder
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class CarRenderer(val context: Context): GLBaseRenderer() {
    private var programId = 0

    /**
     * 透视投影矩阵
     */
    private val projectionMatrix: FloatArray = FloatArray(16)

    /**
     * 模型矩阵
     */
    private val modelMatrix = FloatArray(16)

    private lateinit var textureShaderProgram: TextureShaderProgram
    private lateinit var colorShaderProgram: ColorShaderProgram

    private lateinit var car: Car

    private var texture: Int = 0

//    private val vertexData = ByteBuffer
//        .allocateDirect(shape.size * BYTES_PER_FLOAT)
//        .order(ByteOrder.nativeOrder())
//        .asFloatBuffer()
//        .put(shape)

    private var uColorLocation = 0

    private var aPositionLocation = 0

//    companion object{
//
//        private const val POSITION_COMPONENT_COUNT = 2
//
//        private const val U_COLOR = "u_Color"
//
//        private const val A_POSITION = "a_Position"
//
//        private const val TEXTURE_COORDINATES_COMPONENT_COUNT = 2
//
//        private const val STRIDE: Int = (POSITION_COMPONENT_COUNT
//                + TEXTURE_COORDINATES_COMPONENT_COUNT) * BYTES_PER_FLOAT
//
//        val shape = floatArrayOf(
//            // 数据顺序: X, Y, S, T
//            // 三角形扇形
//            0f, 0f, 0.5f, 0.5f,
//            -0.5f, -0.8f, 0f, 0.9f,
//            0.5f, -0.8f, 1f, 0.9f,
//            0.5f, 0.8f, 1f, 0.1f,
//            -0.5f, 0.8f, 0f, 0.1f,
//            -0.5f, -0.8f, 0f, 0.9f
//        )
//    }

    override fun onSurfaceCreated(unused: GL10?, eglConfig: EGLConfig?) {
        glClearColor(0f,0f,0f,1f)
        car = Car()

        textureShaderProgram = TextureShaderProgram(context)
        colorShaderProgram = ColorShaderProgram(context)

        texture = context.loadTexture(R.drawable.img)
    }

    override fun onSurfaceChanged(unused: GL10?, width: Int, height: Int) {
        OpenGLES20.gl2ViewPort(0,0,width, height)
    }

    override fun onDrawFrame(unused: GL10?) {
        glClear(GL_COLOR_BUFFER_BIT)

        textureShaderProgram.useProgram()
        textureShaderProgram.setUniforms(projectionMatrix,texture)
        car.bindData(textureShaderProgram)
        glGetError()
        car.draw()
    }
}