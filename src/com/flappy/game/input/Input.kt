package com.flappy.game.input

import org.lwjgl.glfw.GLFW
import org.lwjgl.glfw.GLFWKeyCallback

/**
 * Created by dastaniqbal on 23/09/2018.
 * ask2iqbal@gmail.com
 * 23/09/2018 10:41
 */
class Input : GLFWKeyCallback() {
    companion object {

        fun isKeyDown(keycode: Int): Boolean {
            return keys[keycode]
        }

        val keys = BooleanArray(65536)
    }

    override fun invoke(window: Long, key: Int, scancode: Int, action: Int, mods: Int) {
        keys[key] = action != GLFW.GLFW_RELEASE
    }
}