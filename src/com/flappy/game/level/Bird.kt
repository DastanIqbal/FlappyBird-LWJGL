package com.flappy.game.level

import com.flappy.game.graphics.Shader
import com.flappy.game.graphics.Texture
import com.flappy.game.graphics.VertexArray
import com.flappy.game.input.Input
import com.flappy.game.math.Matrix4f
import com.flappy.game.math.Vector3f
import org.lwjgl.glfw.GLFW

/**
 * Created by dastaniqbal on 24/09/2018.
 * dastanIqbal@marvelmedia.com
 * 24/09/2018 12:07
 */
class Bird {
    val SIZE = 1.0f
    var mesh: VertexArray
    var texture: Texture

    var position = Vector3f()
    var rot = 0f
    var delta = 0.0f

    init {
        val vertices = floatArrayOf(
                -SIZE / 2f, -SIZE / 2f, 0.2f, //Why?
                -SIZE / 2f, SIZE / 2f, 0.2f,
                SIZE / 2f, SIZE / 2f, 0.2f,
                SIZE / 2f, -SIZE / 2f, 0.2f
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
        texture = Texture("res/bird.png")
    }

    private fun fall() {
        delta = -0.15f
    }

    fun update() {
        /*if(Input.keys[GLFW.GLFW_KEY_UP]){
            position.y+=0.1f
        }
        if(Input.keys[GLFW.GLFW_KEY_DOWN]){
            position.y-=0.1f
        }
        if(Input.keys[GLFW.GLFW_KEY_LEFT]){
            position.x-=0.1f
        }
        if(Input.keys[GLFW.GLFW_KEY_RIGHT]){
            position.x+=0.1f
        }*/
        position.y -= delta
        if (Input.isKeyDown(GLFW.GLFW_KEY_SPACE)) {
            delta = -0.15f
        } else {
            delta += 0.01f
        }

        rot = -delta + 90.0f
    }

    fun render() {
        Shader.BIRD.enable()
        Shader.BIRD.setUniformMat4("ml_matrix", Matrix4f.translate(position).multiply(Matrix4f.rotate(rot)))
        texture.bind()
        mesh.render()
        Shader.BIRD.disable()
    }
}