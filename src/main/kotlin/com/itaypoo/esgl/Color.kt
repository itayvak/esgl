package com.itaypoo.esgl

import com.raylib.Raylib

data class Color(
    val red: Int,
    val green: Int,
    val blue: Int,
    val alpha: Int,
) {
    companion object {
        val WHITE = Color(255, 255, 255, 255)
        val BLACK = Color(0, 0, 0, 255)
        val RED = Color(255, 0, 0, 255)
        val GREEN = Color(0, 255, 0, 255)
        val BLUE = Color(0, 0, 255, 255)
    }

    fun toRayColor(): Raylib.Color {
        return (Raylib.Color()).r(red.toByte()).g(green.toByte()).b(blue.toByte()).a(alpha.toByte())
    }
}