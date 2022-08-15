import android.content.Context
import android.opengl.GLES20.*
import android.util.Log
import com.example.glesdemo.R
import com.example.glesdemo.common.ShaderProgram

/**
 * 颜色着色器程序
 */
class ColorShaderProgram(
    context: Context
): ShaderProgram(context, R.raw.vertex_shader, R.raw.fragment_shader) {

    private val uMatrixLocation: Int = getUniformLocation(U_MATRIX)
    val aPositionLocation: Int = getAttribLocation(A_POSITION)
    val aColorLocation: Int = getAttribLocation(A_COLOR)

    fun setUniforms(matrix: FloatArray) {
        glUniformMatrix4fv(uMatrixLocation, 1, false, matrix, 0)
    }

    companion object {
        private const val U_MATRIX = "u_Matrix"

        private const val A_POSITION = "a_Position"
        private const val A_COLOR = "a_Color"
    }
}