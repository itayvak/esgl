package com.itaypoo.exampleGame.entities

import com.itaypoo.esgl.Color
import com.itaypoo.esgl.Drawable
import com.itaypoo.esgl.Window
import com.raylib.Raylib

class TitleText: Drawable {
    override fun load() {}

    override fun unload() {}

    override fun update(deltaTime: Float, window: Window) {}

    override fun draw() {
        Raylib.DrawText("Turtle Fucker", 100, 100, 50, Color.WHITE.toRayColor())
        Raylib.DrawText("Press [Space] to start", 100, 150, 20, Color.WHITE.toRayColor())
    }
}