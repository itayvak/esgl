package com.itaypoo.esgl.graphics

import com.itaypoo.esgl.core.Loadable
import com.itaypoo.esgl.core.Window

interface Drawable : Loadable {
    fun update(deltaTime: Float, window: Window)
    fun draw(window: Window)
}