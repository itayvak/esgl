package com.itaypoo.exampleGame.scenes

import com.itaypoo.esgl.*
import com.itaypoo.exampleGame.entities.Player

class SimpleMovementScene : Drawable {
    private lateinit var camera: Camera
    private lateinit var player: Player

    private val inputVec = Vector2(0, 0)

    override fun load() {
        Debug.drawSpritesPositions = true
        Debug.drawSpritesTextureBounds = true

        camera = Camera()
        player = Player()
        player.sprite.pivotPoint = Vector2(0.8f, 2f)
        player.sprite.flipY = false
        player.load()
    }

    override fun unload() {
        player.unload()
    }

    override fun update(deltaTime: Float, window: Window) {
        player.update(deltaTime, window)
        camera.zoom += Input.mouseWheelDelta.y / 50
        player.sprite.rotation += 1

        inputVec.set(0, 0)
        if (Input.isKeyHeld(Key.UP)) inputVec.y = 1f
        if (Input.isKeyHeld(Key.LEFT)) inputVec.x = 1f
        if (Input.isKeyHeld(Key.DOWN)) inputVec.y = -1f
        if (Input.isKeyHeld(Key.RIGHT)) inputVec.x = -1f

        inputVec *= 10f
        camera.offset += inputVec
    }

    override fun draw(window: Window) {
        player.draw(window)
    }
}