package com.flappy.game.level

import com.flappy.game.graphics.Texture
import com.flappy.game.graphics.VertexArray
import com.flappy.game.math.Matrix4f
import com.flappy.game.math.Vector3f

/**
 * Created by dastaniqbal on 24/09/2018.
 * dastanIqbal@marvelmedia.com
 * 24/09/2018 10:01
 */
class Pipe {
    var position = Vector3f()
    var ml_matrix = Matrix4f()

    companion object {
        val WIDTH = 1.5f
        val HEIGHT = 8.0f
        lateinit var texture: Texture
        lateinit var mesh: VertexArray

        fun create() {
            val vertices = floatArrayOf(
                    0.0f, 0.0f, 0.1f,
                    0.0f, HEIGHT, 0.1f,
                    WIDTH, HEIGHT, 0.1f,
                    WIDTH, 0.0f, 0.1f
            )

            val indices = byteArrayOf(
                    0, 1, 2,
                    2, 3, 0
            )

            val tcs = floatArrayOf(
                    0f, 1f,
                    0f, 0f,
                    1f, 0f,
                    1f, 1f
            )

            mesh = VertexArray(vertices, indices, tcs)
            texture = Texture("res/pipe.png")
        }


    }

    constructor()
    constructor(x: Float, y: Float) {
        position.x = x
        position.y = y
        ml_matrix = Matrix4f.translate(position)
    }
}