package com.itaypoo.exampleGame.scenes

import com.itaypoo.exampleGame.GameManager
import com.itaypoo.exampleGame.entities.TitleText
import com.itaypoo.esgl.*

class MainMenuScene: Scene(
    actors = mutableListOf(TitleText()),
    backgroundColor = Color.BLACK
) {
    override fun update(deltaTime: Float, window: Window) {
        super.update(deltaTime, window)
        if (Input.isKeyPressed(Key.SPACE)) {
            GameManager.game.currentScene = ShootingScene()
        }
    }
}