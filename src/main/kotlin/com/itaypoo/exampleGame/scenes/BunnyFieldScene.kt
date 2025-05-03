package com.itaypoo.exampleGame.scenes

import com.itaypoo.esgl.*
import com.itaypoo.exampleGame.Scene
import com.itaypoo.exampleGame.entities.FloorBackground
import com.itaypoo.exampleGame.entities.Player

class BunnyFieldScene : Scene {
    override lateinit var camera: Camera
    private lateinit var player: Player
    private lateinit var floorBackground: FloorBackground

    private val bunnyCount = 100000
    private var targetZoom = 1f
    private val zoomSpeed = 5.0f
    private val cameraPan = Vector2(0, 0)
    private val scaledOffset = Vector2(0, 0)
    private val inputVec = Vector2(0, 0)

    override fun load() {
        camera = Camera()
        player = Player()
        player.load()
        floorBackground = FloorBackground(bunnyCount)
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

        inputVec.set(0, 0)
        if (Input.isKeyHeld(Key.UP)) inputVec.y = 1f
        if (Input.isKeyHeld(Key.LEFT)) inputVec.x = 1f
        if (Input.isKeyHeld(Key.DOWN)) inputVec.y = -1f
        if (Input.isKeyHeld(Key.RIGHT)) inputVec.x = -1f

        inputVec *= 10f
        cameraPan += inputVec
        camera.offset += cameraPan
    }

    override fun draw(window: Window) {
        floorBackground.draw(window)
        player.draw(window)
    }
}