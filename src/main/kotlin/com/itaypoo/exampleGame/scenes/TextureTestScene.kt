package com.itaypoo.exampleGame.scenes

import com.itaypoo.esgl.*
import com.itaypoo.exampleGame.Scene
import com.itaypoo.exampleGame.entities.Player
import com.raylib.Raylib

class TextureTestScene : Scene {
    override lateinit var camera: Camera
    private lateinit var player: Player

    private val instructions = listOf(
        "WASD: Move position",
        "IJKL: Move pivot point",
        "ARROW KEYS: Change scale",
    )
    private val testDisplays: MutableList<TextDisplay> = mutableListOf()

    override fun load() {
        camera = Camera()
        player = Player()
        player.load()
        player.sprite.pivotMode = Sprite.PivotMode.PIXELS

        for (inst in instructions) {
            testDisplays += TextDisplay(
                text = inst,
                position = Vector2(10, 30 + instructions.indexOf(inst) * 20),
                size = 20f,
                fontPath = "resources/fonts/Fredoka.ttf",
                color = Color.WHITE
            )
            testDisplays.last().load()
        }

        Debug.drawSpritesTextureBounds = true
        Debug.drawSpritesPositions = true
    }

    override fun unload() {
        player.unload()
    }

    override fun update(deltaTime: Float, window: Window) {
        player.update(deltaTime, window)
    }

    override fun draw(window: Window) {
        player.draw(window)

        if(Input.isKeyHeld(Key.I)) player.sprite.pivotPoint.y -= 1
        if(Input.isKeyHeld(Key.J)) player.sprite.pivotPoint.x -= 1
        if(Input.isKeyHeld(Key.K)) player.sprite.pivotPoint.y += 1
        if(Input.isKeyHeld(Key.L)) player.sprite.pivotPoint.x += 1

        if(Input.isKeyHeld(Key.UP)) player.sprite.scale.y += 0.1f
        if(Input.isKeyHeld(Key.LEFT)) player.sprite.scale.x -= 0.1f
        if(Input.isKeyHeld(Key.DOWN)) player.sprite.scale.y -= 0.1f
        if(Input.isKeyHeld(Key.RIGHT)) player.sprite.scale.x += 0.1f

        testDisplays.forEach { it.draw(window) }
    }
}