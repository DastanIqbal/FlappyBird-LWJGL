package com.flappy.game.graphics

import com.flappy.game.math.Vector3f
import com.flappy.game.util.ShaderUtils
import org.lwjgl.opengl.GL20

/**
 * Created by dastaniqbal on 23/09/2018.
 * dastanIqbal@marvelmedia.com
 * 23/09/2018 9:52
 */
class Shader {
    var ID = 0

    companion object {
        lateinit var BASIC: Shader
        fun loadAll() {
            BASIC = Shader("shaders/shader.vert", "shaders/shader.frag")
        }
    }

    private constructor(vertex: String, fragment: String) {
        ID = ShaderUtils.load(vertex, fragment)
    }

    fun getUniform(name: String): Int {
        return GL20.glGetUniformLocation(ID, name)
    }

    fun setUniform3f(name: String, vector3f: Vector3f) {
        GL20.glUniform3f(getUniform(name), vector3f.x, vector3f.y, vector3f.z)
    }

    fun enable() {
        GL20.glUseProgram(ID)
    }

    fun disable() {
        GL20.glUseProgram(0)
    }
}