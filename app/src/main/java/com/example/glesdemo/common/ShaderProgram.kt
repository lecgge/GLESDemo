package com.example.glesdemo.common

import android.content.Context
import android.opengl.GLES20.*
import android.util.Log
import com.example.glesdemo.ext.readStringFromRaw
import com.example.glesdemo.utils.ShaderHelper

abstract class ShaderProgram(
    context: Context,
    vertexShaderResId: Int,
    fragmentShaderResId: Int,
) {
    protected val programId: Int

    init {
        with(context) {
            programId = ShaderHelper.buildProgram(
                readStringFromRaw(vertexShaderResId),
                readStringFromRaw(fragmentShaderResId)
            )
        }
    }

    fun getAttribLocation(name: String): Int = glGetAttribLocation(programId, name)

    fun getUniformLocation(name: String): Int = glGetUniformLocation(programId, name)

    fun useProgram() {
        glUseProgram(programId)
        Log.d("TAG", "glUseProgram: ${glGetError()}")

    }
}
