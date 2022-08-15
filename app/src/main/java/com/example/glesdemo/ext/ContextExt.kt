package com.example.glesdemo.ext

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.res.Resources
import android.graphics.BitmapFactory
import android.opengl.GLES20.*
import android.opengl.GLUtils
import androidx.annotation.RawRes
import com.example.glesdemo.utils.OpenGLES20
import com.example.glesdemo.utils.log
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.Reader


/**
 * 将着色器加载到内存中
 * @param resId  raw 目录下的 GLSL文件id
 */
fun Context.readStringFromRaw(@RawRes resId: Int): String {
    //通过Buffer流来读取GLSL文件
    return runCatching {
        val builder = StringBuilder()
        val reader = BufferedReader(InputStreamReader(resources.openRawResource(resId)))
        var nextLine: String? = reader.readLine()
        while (nextLine != null) {
            builder.append(nextLine).append("\n")
            nextLine = reader.readLine()
        }
        reader.close()
        builder.toString()
    }.onFailure {
        when (it) {
            is IOException -> {
                throw RuntimeException("Could not open resource: $resId", it)
            }
            is Resources.NotFoundException -> {
                throw RuntimeException("Resource not found: $resId", it)
            }
            else -> {

            }
        }
    }.getOrThrow()
}


/**
 * 在代码中创建投影矩阵
 */
object MatrixHelper{
    fun perspectivew(m:FloatArray,yFovInDegrees:Float,aspect:Float,n:Float,f:Float) {
        //计算弧度制的角度
        val angleInRadians=(yFovInDegrees*Math.PI/180.0).toFloat()
        //计算焦距
        val a=(1.0/ Math.tan(angleInRadians / 2.0)).toFloat()
        m[0] = a / aspect
        m[1] = 0f
        m[2] = 0f
        m[3] = 0f
        m[4] = 0f
        m[5] = a
        m[6] = 0f
        m[7] = 0f
        m[8] = 0f
        m[9] = 0f
        m[10] = -((f + n) / (f - n))
        m[11] = -1f
        m[12] = 0f
        m[13] = 0f
        m[14] = -((2f * f * n) / (f - n))
        m[15] = 0f

    }
}


fun Context.isDebugVersion(): Boolean =
    kotlin.runCatching {
        (applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE) != 0
    }.getOrDefault(false)

/**
 * Loads a texture from a resource ID, returning the OpenGL ID for that
 * texture. Returns 0 if the load failed.
 *
 * @param resourceId
 * @return
 */
fun Context.loadTexture(resourceId: Int): Int {
    val textureObjectIds = IntArray(1)
    OpenGLES20.gl2GenTextures(1, textureObjectIds)

    if (textureObjectIds[0] == 0) {
        log("Could not generate a new OpenGL texture object.")
        return 0
    }
    val options = BitmapFactory.Options().apply {
        inScaled = false
    }
    val bitmap = BitmapFactory.decodeResource(
        resources,
        resourceId,
        options
    )
    if (bitmap == null) {
        log("Resource ID $resourceId could not be decoded.")
        glDeleteTextures(
            1,
            textureObjectIds,
            0
        )
        return 0
    }
    glBindTexture(
        GL_TEXTURE_2D,
        textureObjectIds[0]
    )

    /*
    We set each filter with a call to glTexParameteri():
    GL_TEXTURE_MIN_FILTER refers to minification, while GL_TEXTURE_MAG_FILTER refers to magnification.
    For minification, we select GL_LINEAR_MIPMAP_LINEAR, which tells OpenGL
    to use trilinear filtering. We set the magnification filter to GL_LINEAR,
    which tells OpenGL to use bilinear filtering.
     */
    glTexParameteri(
        GL_TEXTURE_2D,
        GL_TEXTURE_MIN_FILTER,
        GL_LINEAR_MIPMAP_LINEAR
    )

    glTexParameteri(
        GL_TEXTURE_2D,
        GL_TEXTURE_MAG_FILTER,
        GL_LINEAR
    )

    /*
        This call tells OpenGL to read in the bitmap data defined by
        bitmap and copy it over into the texture object that is currently bound.
     */
    GLUtils.texImage2D(
        GL_TEXTURE_2D,
        0,
        bitmap,
        0
    )
    bitmap.recycle()
    // Generating mipmaps is also a cinch. We can tell OpenGL to generate
    // all of the necessary levels with a quick call to glGenerateMipmap():
    glGenerateMipmap(GL_TEXTURE_2D)

    /*
        Now that we’ve finished loading the texture, a good practice is to then unbind from the texture
        so that we don’t accidentally make further changes to this texture with other texture calls
     */
    glBindTexture(GL_TEXTURE_2D, 0)

    return textureObjectIds[0]
}