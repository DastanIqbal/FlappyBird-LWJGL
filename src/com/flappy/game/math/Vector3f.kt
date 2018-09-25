package com.flappy.game.math

/**
 * Created by dastaniqbal on 23/09/2018.
 * ask2iqbal@gmail.com
 * 23/09/2018 8:26
 */
class Vector3f {
    var x = 0f
    var y = 0f
    var z = 0f

    constructor() {
        x = 0f
        y = 0f
        z = 0f
    }

    constructor(x: Float, y: Float, z: Float) {
        this.x = x
        this.y = y
        this.z = z
    }
}