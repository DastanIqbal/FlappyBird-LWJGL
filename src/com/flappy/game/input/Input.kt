package com.flappy.game.input

import org.lwjgl.glfw.GLFW
import org.lwjgl.glfw.GLFWKeyCallback

/**
 * Created by dastaniqbal on 23/09/2018.
 * dastanIqbal@marvelmedia.com
 * 23/09/2018 10:41
 */
class Input : GLFWKeyCallback() {
    companion object {
        val keys = BooleanArray(65536)
    }

    override fun invoke(window: Long, key: Int, scancode: Int, action: Int, mods: Int) {
        keys[key] = action != GLFW.GLFW_RELEASE
    }
}