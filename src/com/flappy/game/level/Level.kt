package com.flappy.game.level

import com.flappy.game.graphics.Shader
import com.flappy.game.graphics.Texture
import com.flappy.game.graphics.VertexArray
import com.flappy.game.math.Matrix4f
import com.flappy.game.math.Vector3f
import java.util.*

/**
 * Created by dastaniqbal on 24/09/2018.
 * ask2iqbal@gmail.com
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
    var offset = 5.0f //quarter screen
    var control = true

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
            pipe[it] = Pipe(offset + index * 3.0f, random.nextFloat() * 4.0f)
            pipe[it + 1] = Pipe(pipe[it].position.x, pipe[it].position.y - 11.5f)
            index += 2
        }
    }

    fun updatePipes() {
        pipe[index.rem(10)] = Pipe(offset + index * 3.0f, random.nextFloat() * 4.0f) //x
        pipe[(index + 1).rem(10)] = Pipe(pipe[index.rem(10)].position.x, pipe[index.rem(10)].position.y - 11.5f) //y
        index += 2
    }


    fun update() {
        if(control) {
            xscroll--
            if (-xscroll.rem(335) == 0) {
                map++
            }

            if (-xscroll > 250 && -xscroll.rem(120) == 0) {
                updatePipes()
            }
        }

        bird.update()
        if (control && collision()) {
            bird.fall()
            control = false
            println("Collision")
        }
    }

    fun renderPipes() {
        Shader.PIPE.enable()
        Shader.PIPE.setUniformMat4("vw_matrix", Matrix4f.translate(Vector3f(xscroll * 0.05f, 0f, 0f)))
        Pipe.texture.bind()
        Pipe.mesh.bind()
        (0 until 5 * 2).forEach {
            Shader.PIPE.setUniformMat4("ml_matrix", pipe[it].ml_matrix)
            Shader.PIPE.setUniform1i("top", if (it.rem(2) == 0) 1 else 0)
            Pipe.mesh.draw()
        }
        Pipe.texture.unbind()
        Pipe.mesh.unbind()
        //Shader.PIPE.disable()
    }


    fun render() {
        bgtexture.bind()
        Shader.BG.enable()
        background.bind()
        (0 until map + 4).forEach {
            Shader.BG.setUniformMat4("vw_matrix", Matrix4f.translate(Vector3f((it * 10 + xscroll * 0.03f), 0f, 0f)))
            background.draw()
        }
        Shader.BG.disable()
        bgtexture.unbind()

        renderPipes()
        bird.render()
    }

    fun collision(): Boolean {
        (0 until 5 * 2).forEach {
            var bx = -xscroll * 0.05f
            var by = bird.position.y
            var px = pipe[it].position.x
            var py = pipe[it].position.y

            var bx0 = bx - bird.SIZE / 2f
            var bx1 = bx + bird.SIZE / 2f
            var by0 = bx - bird.SIZE / 2f
            var by1 = bx + bird.SIZE / 2f

            var px0 = px
            var px1 = px + Pipe.WIDTH
            var py0 = py
            var py1 = py + Pipe.HEIGHT

            if (bx1 > px0 && bx0 < px1) {
                if (by1 > py0 && by0 < py1) {
                    return true
                }
            }
        }
        return false
    }
}