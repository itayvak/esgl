package com.itaypoo.exampleGame

import com.itaypoo.exampleGame.scenes.MainMenuScene
import com.itaypoo.esgl.*

object GameManager {
    val gameSize = Vector2(1080, 720)
    val window = Window(
        initialSize = gameSize,
        isResizable = true,
        targetFPS = null,
        displayFPS = true,
        fullscreenToggleKey = Key.F11,
        quitKey = Key.ESCAPE
    )
    val game = Game(window, MainMenuScene())
}

fun main() = GameManager.game.start()