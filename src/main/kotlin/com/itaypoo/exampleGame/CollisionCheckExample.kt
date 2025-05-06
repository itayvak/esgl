package com.itaypoo.exampleGame

import com.itaypoo.esgl.collision.CircleCollisionShape
import com.itaypoo.esgl.collision.LineCollisionShape
import com.itaypoo.esgl.collision.RectangleCollisionShape
import com.itaypoo.esgl.core.Vector2
import com.itaypoo.esgl.core.Window
import com.itaypoo.exampleGame.entities.Player

fun main() {
    val windowSize = Vector2(1080,720)
    val window = Window("Simple Movement Game", windowSize)

    // create a player and load it
    val player = Player()
    player.load()

    // create some collision shapes
    // load them all and add them to a collision group named "wall"
    val wall1 = RectangleCollisionShape(Vector2(300, 300), Vector2(100, 100))
    wall1.load()
    wall1.groups.add("wall")
    val wall2 = CircleCollisionShape(Vector2(500, 300), 50f)
    wall2.load()
    wall2.groups.add("wall")
    val wall3 = LineCollisionShape(Vector2(700, 150), Vector2(40, 100))
    wall3.load()
    wall3.groups.add("wall")
    val wall4 = LineCollisionShape(Vector2(800, 100), Vector2(10, 10))
    wall4.load()
    wall4.groups.add("wall")

    // run the game
    window.run { deltaTime, window ->
        player.update(deltaTime, window)
        ////
        wall1.draw()
        wall2.draw()
        wall3.draw()
        wall4.draw()
        player.draw(window)
    }
}