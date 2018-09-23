package com.flappy.game.level

import com.flappy.game.graphics.Shader
import com.flappy.game.graphics.Texture
import com.flappy.game.graphics.VertexArray

/**
 * Created by dastaniqbal on 24/09/2018.
 * dastanIqbal@marvelmedia.com
 * 24/09/2018 12:07
 */
class Level {
    var background: VertexArray
    var bgtexture: Texture

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
    }

    fun render() {
        bgtexture.bind()
        Shader.BG.enable()
        background.render()
        Shader.BG.disable()
        bgtexture.unbind()
    }
}