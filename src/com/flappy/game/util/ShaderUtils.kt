package com.flappy.game.util

import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL20.*

/**
 * Created by dastaniqbal on 23/09/2018.
 * ask2iqbal@gmail.com
 * 23/09/2018 9:08
 */
object ShaderUtils {

    fun load(vertPath: String, fragPath: String): Int {
        val vss = FileUtils.loadAsString(vertPath)
        val fss = FileUtils.loadAsString(fragPath)
        return create(vss, fss)
    }

    fun create(vss: String, fss: String): Int {
        val program = glCreateProgram()
        val vertId = glCreateShader(GL_VERTEX_SHADER)
        val fragId = glCreateShader(GL_FRAGMENT_SHADER)

        glShaderSource(vertId, vss)
        glShaderSource(fragId, fss)

        glCompileShader(vertId)
        if (glGetShaderi(vertId, GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            System.err.println("Failed to compile vertex shader")
            System.err.println(glGetShaderInfoLog(vertId, 2048))
        }
        glCompileShader(fragId)
        if (glGetShaderi(fragId, GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            System.err.println("Failed to compile fragment shader")
            System.err.println(glGetShaderInfoLog(fragId, 2048))
        }

        glAttachShader(program, vertId)
        glAttachShader(program, fragId)

        glLinkProgram(program)
        glValidateProgram(program)

        glDeleteShader(vertId)
        glDeleteShader(fragId)
        return program
    }
}