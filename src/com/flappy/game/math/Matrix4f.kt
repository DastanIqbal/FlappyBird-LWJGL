package com.flappy.game.math

import com.flappy.game.util.BufferUtils
import java.lang.Math.*
import java.nio.FloatBuffer

/**
 * Created by dastaniqbal on 23/09/2018.
 * dastanIqbal@marvelmedia.com
 * 23/09/2018 8:27
 */
class Matrix4f {
    constructor()

    val matrix = FloatArray(SIZE)

    companion object {
        val SIZE = 4 * 4
        fun identity(): Matrix4f {
            val result = Matrix4f()
            (0 until SIZE).forEach {
                result.matrix[it] = 0.0f
            }

            //         [row + col + width]
            result.matrix[0 + 0 * 4] = 1.0f
            result.matrix[1 + 1 * 4] = 1.0f
            result.matrix[2 + 2 * 4] = 1.0f
            result.matrix[3 + 3 * 4] = 1.0f
            return result
        }

        fun translate(vector3f: Vector3f): Matrix4f {
            val result = identity()
            result.matrix[0 + 3 * 4] = vector3f.x
            result.matrix[1 + 3 * 4] = vector3f.y
            result.matrix[2 + 3 * 4] = vector3f.z
            return result
        }

        fun rotate(angle: Float): Matrix4f {
            val result = identity()
            val r = toRadians(angle.toDouble())
            val cos = cos(r).toFloat()
            val sin = sin(r).toFloat()
            result.matrix[0 + 0 * 4] = cos
            result.matrix[1 + 0 * 4] = sin

            result.matrix[0 + 1 * 4] = -sin
            result.matrix[1 + 1 * 4] = cos
            return result
        }

        fun orthographic(left: Float, right: Float, top: Float, bottom: Float, near: Float, far: Float): Matrix4f {
            val result = identity()
            result.matrix[0 + 0 * 4] = 2.0f / (right - left)
            result.matrix[1 + 1 * 4] = 2.0f / (top - bottom)
            result.matrix[2 + 2 * 4] = 2.0f / (near - far)

            result.matrix[0 + 3 * 4] = (left + right) / (left - right)
            result.matrix[1 + 3 * 4] = (bottom + top) / (bottom - top)
            result.matrix[2 + 3 * 4] = (far + near) / (far - near)
            return result
        }
    }

    fun toFloatBuffer(): FloatBuffer {
        return BufferUtils.createFloatBuffer(matrix)
    }

    //OpenGL take column major ordering
    fun multiply(matrix4f: Matrix4f): Matrix4f {
        val result = Matrix4f()
        (0 until 4).forEach { y ->
            run {
                (0 until 4).forEach { x ->
                    run {
                        var sum = 0.0f
                        (0 until 4).forEach { e ->
                            run {
                                sum += this.matrix[e + y * 4] * matrix4f.matrix[x + e * 4]
                            }
                        }
                        result.matrix[x + y * 4] = sum
                    }
                }
            }
        }
        return result
    }
}