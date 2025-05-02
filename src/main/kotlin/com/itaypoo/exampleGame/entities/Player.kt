package com.itaypoo.exampleGame.entities

import com.itaypoo.exampleGame.GameManager
import com.itaypoo.esgl.*

class Player : Drawable {
    val sprite = Sprite(
        "resources/widebunny.png",
        pivotMode = Sprite.PivotMode.NORMALIZED,
        pivotPoint = Vector2(0.5f, 0.5f),
    )
    val position = Vector2(GameManager.gameSize.x / 2, GameManager.gameSize.y / 2)
    private val inputVec = Vector2(0, 0)
    private val velocity = Vector2(0, 0)
    private val scaledVelocity = Vector2(0, 0)
    private val acceleration = 10f

    companion object {
        const val BASE_RUN_SPEED = 1000
        const val CRAWL_SPEED = 100
    }

    override fun load() {
        sprite.load()
    }

    override fun unload() {
        sprite.unload()
    }

    override fun update(deltaTime: Float, window: Window) {
        inputVec.set(0, 0)
        if (Input.isKeyHeld(Key.W)) inputVec.y = -1f
        if (Input.isKeyHeld(Key.A)) inputVec.x = -1f
        if (Input.isKeyHeld(Key.S)) inputVec.y = 1f
        if (Input.isKeyHeld(Key.D)) inputVec.x = 1f

        inputVec.normalize()

        if(Input.isKeyHeld(Key.LEFT_SHIFT)) {
            // crawl
            scaledVelocity.set(inputVec)
            scaledVelocity *= (CRAWL_SPEED * deltaTime)
            position += scaledVelocity
        } else {
            inputVec *= BASE_RUN_SPEED
            // Smoothly move velocity towards target velocity
            velocity.x += (inputVec.x - velocity.x) * acceleration * deltaTime
            velocity.y += (inputVec.y - velocity.y) * acceleration * deltaTime
            scaledVelocity.set(velocity)
            scaledVelocity *= deltaTime
            position += scaledVelocity
        }

        sprite.position.set(position)
    }

    override fun draw(window: Window) {
        sprite.draw(window)
    }

}