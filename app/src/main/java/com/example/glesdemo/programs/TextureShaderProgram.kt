package com.example.glesdemo.programs

import android.content.Context
import android.opengl.GLES20
import com.example.glesdemo.R
import com.example.glesdemo.common.ShaderProgram

/**
 * 纹理着色器程序
 */
class TextureShaderProgram(
    context: Context
): ShaderProgram(context, R.raw.texture_vertex_shader, R.raw.texture_fragment_shader) {

    private val uMatrixLocation: Int = getUniformLocation(U_MATRIX)

    private val uTextureUnitLocation: Int = getUniformLocation(U_TEXTURE_UNIT)

    val aPositionLocation: Int = getAttribLocation(A_POSITION)

    val aTextureCoordinatesLocation: Int = getAttribLocation(A_TEXTURE_COORDINATES)


    companion object {
        private const val U_MATRIX = "u_Matrix"
        private const val U_TEXTURE_UNIT = "u_TextureUnit"

        private const val A_POSITION = "a_Position"
        private const val A_TEXTURE_COORDINATES = "a_TextureCoordinates"
    }

    fun setUniforms(matrix: FloatArray, textureId: Int) {
        //传递矩阵的值
        GLES20.glUniformMatrix4fv(uMatrixLocation, 1, false, matrix, 0)

        //将活动纹理单位设置为纹理单位0
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0)
        //将纹理绑定到该单元上
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId)
        //指定sampler对应的的纹理单元
        GLES20.glUniform1i(uTextureUnitLocation, 0)
    }
}
