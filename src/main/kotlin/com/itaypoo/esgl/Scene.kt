package com.itaypoo.esgl

open class Scene(
    val actors: MutableList<Drawable>,
    var backgroundColor: Color = Color.BLACK,
    var camera: Camera? = null
) {
    open fun update(deltaTime: Float, window: Window) {
        actors.forEach { it.update(deltaTime, window) }
    }

    fun load() {
        actors.forEach { it.load() }
    }

    fun unload() {
        actors.forEach { it.unload() }
    }

    fun draw() {
        actors.forEach { it.draw() }
    }
}