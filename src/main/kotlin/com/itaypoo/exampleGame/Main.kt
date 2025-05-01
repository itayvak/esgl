package com.itaypoo.exampleGame

import com.itaypoo.esgl.*
import com.itaypoo.exampleGame.scenes.BunnyFieldScene

interface Scene : Drawable {
    val camera: Camera
}

object GameManager {
    val gameSize = Vector2(1080, 720)
    lateinit var currentScene: Scene
        private set
    val window = Window(
        title = "Bunny Game",
        targetFPS = 60,
        isResizable = true,
        enableVSync = true,
        fullscreenToggleKey = Key.F11,
        quitKey = Key.ESCAPE,
        initialSize = gameSize,
        backgroundColor = Color(10, 10, 20, 255),
    )

    init {
        window.init()
        switchScene(BunnyFieldScene())
    }

    fun switchScene(newScene: Scene) {
        if (::currentScene.isInitialized) currentScene.unload()
        newScene.load()
        window.camera = newScene.camera
        currentScene = newScene
    }

    fun start() = window.run { deltaTime, window ->
        currentScene.update(deltaTime, window)
        currentScene.draw(window)
    }
}

fun main() = GameManager.start()