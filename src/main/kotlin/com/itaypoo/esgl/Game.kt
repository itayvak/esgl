package com.itaypoo.esgl

import com.raylib.Raylib

class Game(
    val window: Window,
    currentScene: Scene
) {
    var currentScene: Scene = currentScene
        set(value) {
            currentScene.unload()
            value.load()
            field = value
        }

    fun start() {
        while(!window.shouldClose()) {
            window.beginDrawing(currentScene.backgroundColor, currentScene.camera)
            val deltaTime = Raylib.GetFrameTime()
            currentScene.update(deltaTime, window)
            currentScene.draw()
            window.endDrawing()
        }
        currentScene.unload()
        window.close()
    }
}