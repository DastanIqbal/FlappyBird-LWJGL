package com.flappy.game.graphics

import com.flappy.game.math.Matrix4f
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
    val locationCache = HashMap<String, Int>()

    companion object {
        val VERTEX_ATTRIB = 0
        val TEXTURE_ATTRIB = 1
        lateinit var BASIC: Shader
        fun loadAll() {
            BASIC = Shader("shaders/shader.vert", "shaders/shader.frag")
        }
    }

    private constructor(vertex: String, fragment: String) {
        ID = ShaderUtils.load(vertex, fragment)
    }

    fun getUniform(name: String): Int {
        if (locationCache.containsKey(name))
            return locationCache[name]!!
        val result = GL20.glGetUniformLocation(ID, name)
        if (result == -1) {
            System.err.println("Could not found uniform variable $name")
        } else {
            locationCache[name] = result
        }
        return result
    }

    fun setUniform1i(name: String, value: Int) {
        GL20.glUniform1i(getUniform(name), value)
    }

    fun setUniform1f(name: String, value: Float) {
        GL20.glUniform1f(getUniform(name), value)
    }

    fun setUniform2f(name: String, x: Float, y: Float) {
        GL20.glUniform2f(getUniform(name), x, y)
    }

    fun setUniform3f(name: String, vector3f: Vector3f) {
        GL20.glUniform3f(getUniform(name), vector3f.x, vector3f.y, vector3f.z)
    }

    fun setUniformMat4(name: String, matrix: Matrix4f) {
        GL20.glUniformMatrix4fv(getUniform(name), false, matrix.toFloatBuffer())
    }

    fun enable() {
        GL20.glUseProgram(ID)
    }

    fun disable() {
        GL20.glUseProgram(0)
    }
}