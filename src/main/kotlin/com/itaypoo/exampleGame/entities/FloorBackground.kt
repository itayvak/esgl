package com.itaypoo.exampleGame.entities

import com.itaypoo.exampleGame.GameManager
import com.itaypoo.esgl.Drawable
import com.itaypoo.esgl.Sprite
import com.itaypoo.esgl.Vector2
import com.itaypoo.esgl.Window

class FloorBackground : Drawable {
    val sprites: MutableList<Sprite> = mutableListOf()
    var didCreateSprites = false

    override fun load() {
        for (i in (0..20)) {
            sprites.add(Sprite("resources/bunny.png", Vector2(
                (0..GameManager.gameSize.x.toInt()).random(),
                (0..GameManager.gameSize.y.toInt()).random()
            )))
        }
        sprites.forEach { it.load() }
    }

    override fun unload() {
        sprites.forEach { it.unload() }
    }

    override fun update(deltaTime: Float, window: Window) {
    }

    override fun draw() {
        sprites.forEach { it.draw() }
    }
}