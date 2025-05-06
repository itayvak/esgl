package com.itaypoo.esgl.core

import com.raylib.Raylib

data class Color(
    val red: Int,
    val green: Int,
    val blue: Int,
    val alpha: Int,
) {
    val rayColor = Raylib.Color().r(red.toByte()).g(green.toByte()).b(blue.toByte()).a(alpha.toByte())
        get() {
            field.r(red.toByte()).g(green.toByte()).b(blue.toByte()).a(alpha.toByte())
            return field
        }

    companion object {
        val WHITE = Color(255, 255, 255, 255)
        val BLACK = Color(0, 0, 0, 255)
        val RED = Color(255, 0, 0, 255)
        val SEETHROUGH_RED = Color(255, 0, 0, 150)
        val GREEN = Color(0, 255, 0, 255)
        val BLUE = Color(0, 0, 255, 255)
    }
}