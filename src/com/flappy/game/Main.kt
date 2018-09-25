package com.flappy.game

import com.flappy.game.graphics.Shader
import com.flappy.game.input.Input
import com.flappy.game.level.Level
import com.flappy.game.math.Matrix4f
import com.flappy.game.util.OpenGLUtils
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL13.GL_TEXTURE1
import org.lwjgl.opengl.GL13.glActiveTexture
import org.lwjgl.system.MemoryUtil.NULL


/**
 * Created by dastaniqbal on 23/09/2018.
 * dastanIqbal@marvelmedia.com
 * 23/09/2018 8:04
 */
class Main : Runnable {

    val width = 640//1280
    val height = 480

    var running = false
    var mainThread: Thread? = null
    var window = 0L
    var level: Level? = null

    fun start() {
        running = true
        mainThread = Thread(this, "Game")
        mainThread?.start()
    }

    private fun init() {
        if (!glfwInit()) {
            //TODO: handle it
        }

        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3)
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3)
        glfwWindowHint(GLFW_RESIZABLE, GL_TRUE)
        window = glfwCreateWindow(width, height, "Flappy", NULL, NULL)

        if (window == NULL) {
            //TODO: handle it
            return
        }

        val vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor())
        glfwSetWindowPos(window, (vidmode?.width()!! - width) / 2, (vidmode.height() - height) / 2)

        glfwSetKeyCallback(window, Input())
        glfwMakeContextCurrent(window)
        GL.createCapabilities()
        glfwShowWindow(window)

        glClearColor(1.0f, 1.0f, 1.0f, 1.0f)
        glEnable(GL_DEPTH_TEST)
        glActiveTexture(GL_TEXTURE1)
        println("OpenGL ${glGetString(GL_VERSION)}")
        Shader.LoadAll()

        val pr_matrix = Matrix4f.orthographic(-10f, 10f, -10f * 9f / 16f, 10f * 9f / 16f, -1f, 1f)
        Shader.BG.setUniformMat4("pr_matrix", pr_matrix)
        Shader.BG.setUniform1i("tex", 1)

        Shader.BIRD.setUniformMat4("pr_matrix", pr_matrix)
        Shader.BIRD.setUniform1i("tex", 1)

        level = Level()
    }

    override fun run() {
        init()

        var lastTime = System.nanoTime()
        var delta = 0.0
        val ns = 1000000000 / 60.0
        var timer = System.currentTimeMillis()
        var updates = 0
        var frames = 0

        while (running) {
            val now = System.nanoTime()
            delta += (now - lastTime) / ns
            lastTime = now

            if (delta >= 1.0) {
                update()
                updates++
                delta--
            }
            render()
            frames++
            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000
                println("$updates ups $frames fps")
                updates = 0
                frames = 0
            }
            if (glfwWindowShouldClose(window))
                running = false
        }
    }

    fun update() {
        glfwPollEvents()
        level?.update()
    }

    fun render() {
        glClear(GL_COLOR_BUFFER_BIT.or(GL_DEPTH_BUFFER_BIT))
        level?.render()
        OpenGLUtils.checkError("Main::render")
        glfwSwapBuffers(window)
    }
}

fun main(args: Array<String>) {
    Main().start()
}