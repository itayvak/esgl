package com.itaypoo.exampleGame.scenes

import com.itaypoo.exampleGame.GameManager
import com.itaypoo.exampleGame.entities.TitleText
import com.itaypoo.esgl.*
import com.itaypoo.exampleGame.Scene

class MainMenuScene() : Scene {
    override lateinit var camera: Camera
    private lateinit var titleText: TitleText

    override fun load() {
        camera = Camera()
        titleText = TitleText()
        titleText.load()
    }

    override fun unload() {
        titleText.unload()
    }

    override fun update(deltaTime: Float, window: Window) {
        titleText.update(deltaTime, window)

        if (Input.isKeyPressed(Key.SPACE)) GameManager.switchScene(BunnyFieldScene())
    }

    override fun draw(window: Window) {
        titleText.draw(window)
    }

}