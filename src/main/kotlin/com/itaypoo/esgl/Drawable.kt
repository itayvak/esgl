package com.itaypoo.esgl

interface Drawable {
    fun load()
    fun unload()
    fun update(deltaTime: Float, window: Window)
    fun draw()
}