package com.itaypoo.esgl

import com.raylib.Raylib
import kotlin.math.sqrt

data class Vector2(
    var x: Float, var y: Float
) {
    constructor(x: Int, y: Int) : this(x.toFloat(), y.toFloat())
    constructor(rayVec: Raylib.Vector2) : this(rayVec.x(), rayVec.y())

    val rayVec: Raylib.Vector2 = Raylib.Vector2().x(x).y(y)
        get() {
            field.x(x).y(y)
            return field
        }

    fun normalize() {
        val length = sqrt(x * x + y * y)
        if (length != 0f) {
            x /= length
            y /= length
        }
    }

    fun distanceTo(other: Vector2): Float {
        val dx = other.x - x
        val dy = other.y - y
        return sqrt(dx * dx + dy * dy)
    }

    operator fun plusAssign(other: Vector2) {
        x += other.x
        y += other.y
    }

    fun set(x: Float, y: Float) {
        this.x = x
        this.y = y
    }

    fun set(x: Int, y: Int) {
        this.x = x.toFloat()
        this.y = y.toFloat()
    }

    fun set(other: Vector2) {
        this.x = other.x
        this.y = other.y
    }

    fun set(other: Raylib.Vector2) {
        this.x = other.x()
        this.y = other.y()
    }

    operator fun divAssign(i: Int) {
        x /= i
        y /= i
    }

    operator fun divAssign(i: Float) {
        x /= i
        y /= i
    }

    operator fun timesAssign(i: Int) {
        x *= i
        y *= i
    }

    operator fun timesAssign(i: Float) {
        x *= i
        y *= i
    }

    operator fun minusAssign(other: Vector2) {
        x -= other.x
        y -= other.y
    }

    fun min(min: Float) {
        if(x < min) x = min
        if(y < min) y = min
    }
}