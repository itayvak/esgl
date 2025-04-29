package com.itaypoo.esgl

import com.raylib.Raylib

object Input {
    val mousePosition: Vector2
        get() = Vector2.fromRayVec(Raylib.GetMousePosition())

    var mouseCursor = MouseCursor.DEFAULT
        set(value) {
            Raylib.SetMouseCursor(value.code)
            field = value
        }

    fun isKeyPressed(key: Key) = Raylib.IsKeyPressed(key.code)
    fun isKeyReleased(key: Key) = Raylib.IsKeyReleased(key.code)
    fun isKeyHeld(key: Key) = Raylib.IsKeyDown(key.code)

    fun isMouseButtonPressed(button: MouseButton) = Raylib.IsMouseButtonPressed(button.code)
    fun isMouseButtonReleased(button: MouseButton) = Raylib.IsMouseButtonReleased(button.code)
    fun isMouseButtonHeld(button: MouseButton) = Raylib.IsMouseButtonDown(button.code)
}