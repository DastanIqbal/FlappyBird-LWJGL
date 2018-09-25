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
    var pipe = Array(5 * 2, { Pipe() })
    var index = 0
    var random = Random()

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
        createPipes()
    }

    private fun createPipes() {
        Pipe.create()
        (0 until 5 * 2).step(2).forEach {
            pipe[it] = Pipe(index * 3.0f, random.nextFloat() * 4.0f)
            pipe[it + 1] = Pipe(pipe[it].position.x, pipe[it].position.y - 11.5f)
            index += 2
        }
    }

    fun updatePipes() {

    }

    fun renderPipes() {
        Shader.PIPE.enable()
        Shader.PIPE.setUniformMat4("vw_matrix", Matrix4f.translate(Vector3f(xscroll * 0.05f, 0f, 0f)))
        Pipe.texture.bind()
        Pipe.mesh.bind()
        (0 until 5 * 2).forEach {
            Shader.PIPE.setUniformMat4("ml_matrix", pipe[it].ml_matrix)
            Shader.PIPE.setUniform1i("top",if(it.rem(2)==0) 1 else 0)
            Pipe.mesh.draw()
        }
        Pipe.texture.unbind()
        Pipe.mesh.unbind()
        //Shader.PIPE.disable()
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

        renderPipes()
        bird.render()
    }
}