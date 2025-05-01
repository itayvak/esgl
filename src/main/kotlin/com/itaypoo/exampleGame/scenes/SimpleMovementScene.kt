package com.itaypoo.exampleGame.scenes

import com.itaypoo.esgl.Camera
import com.itaypoo.esgl.Input
import com.itaypoo.esgl.Window
import com.itaypoo.exampleGame.Scene
import com.itaypoo.exampleGame.entities.CameraBoundsRenderer
import com.itaypoo.exampleGame.entities.Player

class SimpleMovementScene : Scene {
    override lateinit var camera: Camera
    private lateinit var player: Player
    private lateinit var boundsRenderer: CameraBoundsRenderer

    override fun load() {
        camera = Camera()
        boundsRenderer = CameraBoundsRenderer()
        player = Player()
        player.load()
    }

    override fun unload() {
        player.unload()
    }

    override fun update(deltaTime: Float, window: Window) {
        player.update(deltaTime, window)
        camera.zoom += Input.mouseWheelDelta.y / 50
    }

    override fun draw(window: Window) {
        player.draw(window)
        boundsRenderer.draw(window)
    }
}