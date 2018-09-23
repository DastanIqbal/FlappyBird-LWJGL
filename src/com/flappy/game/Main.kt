package com.flappy.game

import com.flappy.game.input.Input
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11.*
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

    fun start() {
        running = true
        mainThread = Thread(this, "Game")
        mainThread?.start()
    }

    private fun init() {
        if (!glfwInit()) {
            //TODO: handle it
        }

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
        println("OpenGL ${glGetString(GL_VERSION)}")
    }

    override fun run() {
        init()
        while (running) {
            update()
            render()

            if (glfwWindowShouldClose(window))
                running = false
        }
    }

    fun update() {
        glfwPollEvents()
        if (Input.keys[GLFW_KEY_SPACE]) {
            println("FLAP !!")
        }
    }

    fun render() {
        glClear(GL_COLOR_BUFFER_BIT.or(GL_DEPTH_BUFFER_BIT))
        glfwSwapBuffers(window)
    }
}

fun main(args: Array<String>) {
    Main().start()
}