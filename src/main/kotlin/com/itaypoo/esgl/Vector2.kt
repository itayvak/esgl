package com.itaypoo.esgl

import kotlin.math.sqrt

data class Vector2(
    var x: Float,
    var y: Float
) {
    companion object {
        fun fromRayVec(rayVec: com.raylib.Raylib.Vector2): Vector2 {
            return Vector2(rayVec.x(), rayVec.y())
        }
    }

    val normalized: Vector2
        get() {
            val length = kotlin.math.sqrt(x * x + y * y)
            return if (length != 0f) {
                Vector2(x / length, y / length)
            } else {
                Vector2(0f, 0f)
            }
        }

    constructor(x: Int, y: Int) : this(x.toFloat(), y.toFloat())

    fun toRayVec(): com.raylib.Raylib.Vector2 {
        return com.raylib.Raylib.Vector2().x(x).y(y)
    }

    fun distanceTo(other: Vector2): Float {
        val dx = other.x - x
        val dy = other.y - y
        return sqrt(dx * dx + dy * dy)
    }

    operator fun times(num: Int): Vector2 {
        return Vector2(x * num, y * num)
    }

    operator fun times(num: Float): Vector2 {
        return Vector2(x * num, y * num)
    }

    operator fun plusAssign(other: Vector2) {
        x += other.x
        y += other.y
    }

    override fun toString(): String {
        return "Vector2(x=$x, y=$y)"
    }

    fun clamped(to: Float): Vector2 {
        val length = sqrt(x * x + y * y)
        return if (length > to && length != 0f) {
            val scale = to / length
            Vector2(x * scale, y * scale)
        } else {
            Vector2(x, y)
        }
    }

    operator fun div(i: Int): Vector2 {
        return Vector2(x / i, y / i)
    }
}