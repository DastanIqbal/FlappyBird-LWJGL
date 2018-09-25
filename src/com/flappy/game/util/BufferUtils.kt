package com.flappy.game.util

import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import java.nio.IntBuffer

/**
 * Created by dastaniqbal on 23/09/2018.
 * ask2iqbal@gmail.com
 * 23/09/2018 9:28
 */
object BufferUtils {

    fun createByteBuffer(array: ByteArray): ByteBuffer {
        val result = ByteBuffer.allocateDirect(array.size).order(ByteOrder.nativeOrder())
        result.put(array).flip()
        return result
    }

    fun createFloatBuffer(array: FloatArray): FloatBuffer {
        //                                      lef shift << 2 or multiply by 4 mathematically same
        val result = ByteBuffer.allocateDirect(array.size.shl(2)).order(ByteOrder.nativeOrder()).asFloatBuffer()
        result.put(array).flip()
        return result
    }

    fun createIntBuffer(array: IntArray): IntBuffer {
        //                                      lef shift << 2 or multiply by 4 mathematically same
        val result = ByteBuffer.allocateDirect(array.size.shl(2)).order(ByteOrder.nativeOrder()).asIntBuffer()
        result.put(array).flip()
        return result
    }
}