package com.itaypoo.exampleGame.scenes

import com.itaypoo.exampleGame.entities.FloorBackground
import com.itaypoo.exampleGame.entities.Player
import com.itaypoo.esgl.Camera
import com.itaypoo.esgl.Color
import com.itaypoo.esgl.Scene
import com.itaypoo.esgl.Window

class ShootingScene: Scene(
    actors = mutableListOf(),
    backgroundColor = Color.WHITE,
    camera = Camera()
) {
    val player = Player()

    init {
        actors.add(FloorBackground())
        actors.add(player)
    }

    override fun update(deltaTime: Float, window: Window) {
        super.update(deltaTime, window)
        if(camera != null) {
            camera!!.position = player.position
            camera!!.offset = window.currentSize / 2
        }
    }
}