package com.flappy.game

import com.flappy.game.graphics.Shader
import com.flappy.game.math.Vector3f
import org.lwjgl.opengl.*
import org.lwjgl.opengl.GL11.*

/**
 * Created by dastaniqbal on 23/09/2018.
 * dastanIqbal@marvelmedia.com
 * 23/09/2018 8:04
 */
class Main : Runnable {

    val width = 640//1280
    val height = 480
    val title = "Flappy"
    var running = false
    var mainThread: Thread? = null

    fun start() {
        running = true
        mainThread = Thread(this, "Display")
        mainThread?.start()
    }

    private fun init() {
        val version = glGetString(GL_VERSION)
        println(version)
        glClearColor(1.0f, 1.0f, 1.0f, 1.0f)

        Shader.loadAll()
    }

    override fun run() {
        Display.setDisplayMode(DisplayMode(width, height))
        Display.setTitle(title)
        val context = if (System.getProperty("os.name") == "Mac")
            ContextAttribs(3, 2)
        else
            ContextAttribs(3, 3)
        Display.create(PixelFormat(), context.withProfileCore(true))

        init()

        val vao = GL30.glGenVertexArrays()
        GL30.glBindVertexArray(vao)

        val shader = Shader.BASIC
        shader.enable()
        shader.setUniform3f("col", Vector3f(0.8f, 0.2f, 0.3f))

        while (running) {
            render()
            Display.update()
            if (Display.isCloseRequested()) running = false
        }
        Display.destroy()
    }

    fun render() {
        glClear(GL_COLOR_BUFFER_BIT)
        glDrawArrays(GL_TRIANGLES, 0, 3)
    }
}

fun main(args: Array<String>) {
    Main().start()
}