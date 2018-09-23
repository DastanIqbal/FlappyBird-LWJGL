package com.flappy.game.graphics

import com.flappy.game.util.BufferUtils
import org.lwjgl.opengl.GL15
import org.lwjgl.opengl.GL30.*

/**
 * Created by dastaniqbal on 23/09/2018.
 * dastanIqbal@marvelmedia.com
 * 23/09/2018 11:19
 */
class VertexArray {

    var vao = 0
    var vbo = 0
    var tbo = 0
    var ibo = 0
    var count = 0

    constructor(vertices: FloatArray, indices: ByteArray, textureCoordinates: FloatArray) {
        count = indices.size

        vao = glGenVertexArrays()
        glBindVertexArray(vao)

        vbo = glGenBuffers()
        glBindBuffer(GL_ARRAY_BUFFER, vbo)
        glBufferData(GL_ARRAY_BUFFER, BufferUtils.createFloatBuffer(vertices), GL15.GL_STATIC_DRAW)
        glVertexAttribPointer(Shader.VERTEX_ATTRIB, 3, GL_FLOAT, false, 0, 0)
        glEnableVertexAttribArray(Shader.VERTEX_ATTRIB)

        tbo = glGenBuffers()
        glBindBuffer(GL_ARRAY_BUFFER, tbo)
        glBufferData(GL_ARRAY_BUFFER, BufferUtils.createFloatBuffer(textureCoordinates), GL15.GL_STATIC_DRAW)
        glVertexAttribPointer(Shader.TEXTURE_ATTRIB, 2, GL_FLOAT, false, 0, 0)
        glEnableVertexAttribArray(Shader.TEXTURE_ATTRIB)

        ibo = glGenBuffers()
        glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ibo)
        glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, BufferUtils.createByteBuffer(indices), GL15.GL_STATIC_DRAW)

        glBindBuffer(GL15.GL_ARRAY_BUFFER, 0)
        glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0)
        glBindVertexArray(0)
    }

    fun bind() {
        glBindBuffer(GL15.GL_ARRAY_BUFFER, vao)
        glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ibo)
    }

    fun unbind() {
        glBindBuffer(GL15.GL_ARRAY_BUFFER, 0)
        glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0)
    }

    fun draw() {
        glDrawElements(GL_TRIANGLES, count, GL_UNSIGNED_BYTE, 0)
    }

    fun render() {
        bind()
        draw()
    }
}