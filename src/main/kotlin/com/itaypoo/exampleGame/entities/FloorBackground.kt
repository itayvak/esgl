package com.itaypoo.exampleGame.entities

import com.itaypoo.esgl.*
import com.itaypoo.exampleGame.GameManager

class FloorBackground : Drawable {
    val sprites: MutableList<Sprite> = mutableListOf()

    override fun load() {
        for (i in (0..100000)) {
            sprites.add(Sprite("resources/bunny.png", position = Vector2(
                (-GameManager.gameSize.x.toInt() * 2 .. GameManager.gameSize.x.toInt() * 4).random(),
                (-GameManager.gameSize.y.toInt() * 2 .. GameManager.gameSize.y.toInt()* 4).random()
            ),
                tint = Color((0..255).random(), (0..255).random(), (0..255).random(), 150),
                pivotPoint = Vector2(0.5f, 0.5f)
            ))
        }
        sprites.forEach { it.load() }
    }

    override fun unload() {
        sprites.forEach { it.unload() }
    }

    override fun update(deltaTime: Float, window: Window) {
    }

    override fun draw(window: Window) {
        sprites.forEach { it.draw(window) }
    }
}