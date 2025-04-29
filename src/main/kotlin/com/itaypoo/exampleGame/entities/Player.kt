package com.itaypoo.exampleGame.entities

import com.itaypoo.exampleGame.GameManager
import com.itaypoo.esgl.*
import com.raylib.Raylib.DrawCircleV

class Player : Drawable {
    val sprite = Sprite("resources/bunny.png")
    var position = Vector2(GameManager.gameSize.x / 2, GameManager.gameSize.y / 2)
    private var velocity = Vector2(0f, 0f)
    var distanceFromMouse = 0f
    var ballRadius = 30

    companion object {
        const val BASE_RUN_SPEED = 500
    }

    override fun load() {
        sprite.load()
    }

    override fun unload() {
        sprite.unload()
    }

    override fun update(deltaTime: Float, window: Window) {
        val inputVec = Vector2(0, 0)
        if (Input.isKeyHeld(Key.W)) inputVec.y = -1f
        if (Input.isKeyHeld(Key.A)) inputVec.x = -1f
        if (Input.isKeyHeld(Key.S)) inputVec.y = 1f
        if (Input.isKeyHeld(Key.D)) inputVec.x = 1f

        val targetVelocity = inputVec.normalized * BASE_RUN_SPEED

        // Smoothly move velocity towards target velocity
        val acceleration = 10f // tweak this for more/less smoothness
        velocity.x += (targetVelocity.x - velocity.x) * acceleration * deltaTime
        velocity.y += (targetVelocity.y - velocity.y) * acceleration * deltaTime

        position += velocity * deltaTime

        distanceFromMouse = Input.mousePosition.distanceTo(position)
        if (distanceFromMouse < ballRadius) {
            Input.mouseCursor = MouseCursor.POINTING_HAND
            if (Input.isMouseButtonPressed(MouseButton.LEFT)) {
                ballRadius += 5
            }
        } else Input.mouseCursor = MouseCursor.DEFAULT

        sprite.position = position
    }

    override fun draw() {
        DrawCircleV(position.toRayVec(), ballRadius.toFloat(), Color(255, 0, 0, 100).toRayColor())
        sprite.draw()
    }

}