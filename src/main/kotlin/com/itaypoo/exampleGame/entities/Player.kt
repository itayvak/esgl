package com.itaypoo.exampleGame.entities

import com.itaypoo.esgl.collision.RectangleCollisionShape
import com.itaypoo.esgl.core.Vector2
import com.itaypoo.esgl.core.Window
import com.itaypoo.esgl.graphics.Drawable
import com.itaypoo.esgl.graphics.Sprite
import com.itaypoo.esgl.input.Input
import com.itaypoo.esgl.input.Key

class Player : Drawable {
    private lateinit var sprite: Sprite
    private lateinit var collider: RectangleCollisionShape
    private val position = Vector2(100, 100)
    private val inputVec = Vector2(0, 0)

    override fun load() {
        sprite = Sprite("resources/pictures/widebunny.png")
        sprite.load()
        collider = RectangleCollisionShape(position, sprite.textureSize)
        collider.load()
    }

    override fun unload() {
        collider.unload()
        sprite.unload()
    }

    override fun update(deltaTime: Float, window: Window) {
        inputVec.set(0,0)
        if(Input.isKeyHeld(Key.W)) inputVec.y = -1f
        if(Input.isKeyHeld(Key.A)) inputVec.x = -1f
        if(Input.isKeyHeld(Key.S)) inputVec.y = 1f
        if(Input.isKeyHeld(Key.D)) inputVec.x = 1f

        inputVec.normalize()
        inputVec *= 3
        position += inputVec

        val touchingWalls = collider.collidingWithGroup("wall")
        sprite.rotation = if(touchingWalls) 20f else 0f
    }

    override fun draw(window: Window) {
        sprite.position.set(position)
        collider.draw()
        sprite.draw(window)
    }
}