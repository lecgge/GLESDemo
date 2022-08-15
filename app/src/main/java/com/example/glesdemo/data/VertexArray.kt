import android.opengl.GLES20.*
import android.util.Log
import com.example.glesdemo.common.BYTES_PER_FLOAT
import com.example.glesdemo.utils.OpenGLES20
import com.example.glesdemo.utils.OpenGLES20.gl2VertexAttribPointer
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer


class VertexArray(
    vertexData: FloatArray
) {

    private val floatBuffer = ByteBuffer
        .allocateDirect(vertexData.size * BYTES_PER_FLOAT)
        .order(ByteOrder.nativeOrder())
        .asFloatBuffer()
        .put(vertexData)

    fun setVertexAttribPointer(
        dataOffset: Int,
        attributeLocation: Int,
        componentCount: Int,
        stride: Int
    ) {
        floatBuffer.position(dataOffset)

        OpenGLES20.gl2VertexAttribPointer(
            attributeLocation,
            componentCount,
            GL_FLOAT,
            false,
            stride,
            floatBuffer
        )

        OpenGLES20.gl2EnableVertexAttribArray(attributeLocation)

        floatBuffer.position(0)
    }

}
