package com.flappy.game

import org.lwjgl.glfw.GLFW
import org.lwjgl.opengl.GL11
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
        if (!GLFW.glfwInit()) {
            //TODO: handle it
        }

        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GL11.GL_TRUE)
        window = GLFW.glfwCreateWindow(width, height, "Flappy", NULL, NULL)
        if (window == NULL) {
            //TODO: handle it
            return
        }

        val vidmode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor())
        GLFW.glfwSetWindowPos(window, (vidmode?.width()!! - width) / 2, (vidmode.height() - height) / 2)

        GLFW.glfwMakeContextCurrent(window)
        GLFW.glfwShowWindow(window)
    }

    override fun run() {
        init()
        while (running) {
            update()
            render()

            if (GLFW.glfwWindowShouldClose(window))
                running = false
        }
    }

    fun update() {
        GLFW.glfwPollEvents()
    }

    fun render() {
        GLFW.glfwSwapBuffers(window)
    }
}

fun main(args: Array<String>) {
    Main().start()
}