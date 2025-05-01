package com.itaypoo.esgl

import com.raylib.Raylib

class Camera {
    val position: Vector2 = Vector2(0f, 0f)
    val offset: Vector2 = Vector2(0f, 0f)
    var rotation: Float = 0f
    var zoom: Float = 1f

    val rayCam = Raylib.Camera2D().apply {
        target(position.rayVec)
        offset(offset.rayVec)
        rotation(rotation)
        zoom(zoom)
    }
        get() {
            field.apply {
                target(position.rayVec)
                offset(offset.rayVec)
                rotation(rotation)
                zoom(zoom)
            }
            return field
        }

    val worldViewportTopLeft: Vector2 = Vector2(0, 0)
        get() {
            field.x = position.x - (offset.x / zoom)
            field.y = position.y - (offset.y / zoom)
            return field
        }

    val worldViewPortBottomRight: Vector2 = Vector2(0, 0)
        get() {
            // Calculate the bottom-right corner of camera's view in world coordinates
            // First, get the screen dimensions
            val screenWidth = Raylib.GetScreenWidth().toFloat()
            val screenHeight = Raylib.GetScreenHeight().toFloat()

            // Then calculate: position + ((screenSize - offset) / zoom)
            field.x = position.x + ((screenWidth - offset.x) / zoom)
            field.y = position.y + ((screenHeight - offset.y) / zoom)
            return field
        }
}