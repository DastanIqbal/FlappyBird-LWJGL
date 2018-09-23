package com.flappy.game.graphics

import com.flappy.game.util.BufferUtils
import org.lwjgl.opengl.GL11
import java.io.FileInputStream
import javax.imageio.ImageIO

/**
 * Created by dastaniqbal on 23/09/2018.
 * dastanIqbal@marvelmedia.com
 * 23/09/2018 9:15
 */
class Texture {
    var width = 0
    var height = 0
    var texture = 0

    constructor(path: String) {
        texture = load(path)
    }

    private fun load(path: String): Int {
        val image = ImageIO.read(FileInputStream(path))
        width = image.width
        height = image.height
        val pixels = intArrayOf(width * height)
        image.getRGB(0, 0, width, height, pixels, 0, width)

        val data = intArrayOf(width * height)
        (0 until width * height).forEach {
            val a = (pixels[it].and(0xff000000.toInt())).shr(24)
            val r = (pixels[it].and(0xff0000)).shr(16)
            val g = (pixels[it].and(0xff00)).shr(8)
            val b = (pixels[it].and(0xff))

            data[it] = a.shl(24).or(b.shl(16)).or(g.shl(8)).or(r)
        }

        val tex = GL11.glGenTextures()
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, tex)
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST)
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST)

        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, BufferUtils.createIntBuffer(data))
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0)
        return tex
    }

    fun bind() {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture)
    }

    fun unbind() {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0)
    }
}