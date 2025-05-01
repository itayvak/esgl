package com.itaypoo.exampleGame.entities

import com.itaypoo.esgl.Color
import com.itaypoo.esgl.Drawable
import com.itaypoo.esgl.Vector2
import com.itaypoo.esgl.Window
import com.raylib.Raylib

class CameraBoundsRenderer : Drawable {
    private val viewportSize = Vector2(0, 0)
    private var borderSize = BASE_BORDER_SIZE

    companion object {
        const val BASE_BORDER_SIZE = 30f
    }

    override fun load() {}

    override fun unload() {}

    override fun update(deltaTime: Float, window: Window) {}

    override fun draw(window: Window) {
        if (window.camera == null) return
        borderSize = BASE_BORDER_SIZE / window.camera!!.zoom
        viewportSize.set(window.camera!!.worldViewPortBottomRight)
        viewportSize -= window.camera!!.worldViewportTopLeft
        viewportSize.x -= borderSize * 2
        viewportSize.y -= borderSize * 2

        Raylib.DrawRectangleV(
            window.camera!!.worldViewportTopLeft.apply {
                x += borderSize
                y += borderSize
            }.rayVec, viewportSize.rayVec, Color.SEETHROUGH_RED.rayColor
        )
    }
}