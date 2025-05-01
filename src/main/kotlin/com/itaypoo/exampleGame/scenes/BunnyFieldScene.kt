package com.itaypoo.exampleGame.scenes

import com.itaypoo.esgl.*
import com.itaypoo.exampleGame.Scene
import com.itaypoo.exampleGame.entities.CameraBoundsRenderer
import com.itaypoo.exampleGame.entities.FloorBackground
import com.itaypoo.exampleGame.entities.Player

class BunnyFieldScene : Scene{
    override lateinit var camera: Camera
    private lateinit var player: Player
    private lateinit var floorBackground: FloorBackground
    private lateinit var boundsRenderer: CameraBoundsRenderer

    private var targetZoom = 1f
    private val zoomSpeed = 5.0f
    private val scaledOffset = Vector2(0, 0)

    override fun load() {
        camera = Camera()
        boundsRenderer = CameraBoundsRenderer()
        player = Player()
        player.load()
        floorBackground = FloorBackground()
        floorBackground.load()
    }

    override fun unload() {
        player.unload()
        floorBackground.unload()
    }

    override fun update(deltaTime: Float, window: Window) {
        player.update(deltaTime, window)
        floorBackground.update(deltaTime, window)

        camera.position.set(player.position)
        scaledOffset.set(window.currentSize)
        scaledOffset /= 2
        camera.offset.set(scaledOffset)

        targetZoom += Input.mouseWheelDelta.y / 10f
        targetZoom = targetZoom.coerceIn(0.1f, 10f)
        camera.zoom += (targetZoom - camera.zoom) * zoomSpeed * deltaTime
    }

    override fun draw(window: Window) {
        floorBackground.draw(window)
        player.draw(window)
//        boundsRenderer.draw(window)
    }
}