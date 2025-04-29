package com.itaypoo.esgl

import com.raylib.Raylib

class Camera {
    var position: Vector2 = Vector2(0f, 0f)
        set(value) {
            rayCam.target(value.toRayVec())
            field = value
        }
    var offset: Vector2 = Vector2(0f, 0f)
        set(value) {
            rayCam.offset(value.toRayVec())
            field = value
        }
    var rotation: Float = 0f
        set(value) {
            rayCam.rotation(value)
            field = value
        }
    var zoom: Float = 1f
        set(value) {
            rayCam.zoom(value)
            field = value
        }

    val rayCam = Raylib.Camera2D().apply {
        target(position.toRayVec())
        offset(offset.toRayVec())
        rotation(rotation)
        zoom(zoom)
    }
}