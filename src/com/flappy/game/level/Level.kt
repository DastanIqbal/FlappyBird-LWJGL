package com.flappy.game.level

import com.flappy.game.graphics.Shader
import com.flappy.game.graphics.Texture
import com.flappy.game.graphics.VertexArray
import com.flappy.game.math.Matrix4f
import com.flappy.game.math.Vector3f
import java.util.*

/**
 * Created by dastaniqbal on 24/09/2018.
 * dastanIqbal@marvelmedia.com
 * 24/09/2018 12:07
 */
class Level {
    var background: VertexArray
    var bgtexture: Texture
    var xscroll = 0
    var map = 0

    var bird: Bird

    init {
        val vertices = floatArrayOf(
                -10f, -10f * 9f / 16f, 0f,
                -10f, 10f * 9f / 16f, 0f,
                0f, 10f * 9f / 16f, 0f,
                0f, -10f * 9f / 16f, 0f
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

        background = VertexArray(vertices, indices, tcs)
        bgtexture = Texture("res/bg.jpeg")

        bird = Bird()
    }

    fun update() {
        xscroll--
        if (-xscroll.rem(335) == 0) {
            map++
        }

        bird.update()
    }


    fun render() {
        bgtexture.bind()
        Shader.BG.enable()
        background.bind()
        (0 until map + 3).forEach {
            Shader.BG.setUniformMat4("vw_matrix", Matrix4f.translate(Vector3f((it * 10 + xscroll * 0.03f).toFloat(), 0f, 0f)))
            background.draw()
        }
        Shader.BG.disable()
        bgtexture.unbind()

        bird.render()
    }
}