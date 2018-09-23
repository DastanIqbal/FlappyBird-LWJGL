package com.flappy.game.util

import org.lwjgl.opengl.GL11

/**
 * Created by dastaniqbal on 24/09/2018.
 * dastanIqbal@marvelmedia.com
 * 24/09/2018 1:12
 */
object OpenGLUtils {

    fun checkError(where: String) {
        val errorCode = GL11.glGetError()
        if (errorCode != GL11.GL_NO_ERROR)
            System.err.println("$where: $errorCode")
    }
}