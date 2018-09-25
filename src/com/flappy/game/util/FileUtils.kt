package com.flappy.game.util

import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException

/**
 * Created by dastaniqbal on 23/09/2018.
 * ask2iqbal@gmail.com
 * 23/09/2018 9:04
 */

object FileUtils {

    @Throws(IOException::class)
    fun loadAsString(file: String): String {
        var result = ""
        val bufferReader = BufferedReader(FileReader(file))
        var buffer = bufferReader.readLine()
        while (buffer != null) {
            result += buffer + "\n"
            buffer = bufferReader.readLine()
        }
        bufferReader.close()
        return result
    }
}