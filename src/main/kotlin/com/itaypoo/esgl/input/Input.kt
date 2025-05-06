package com.itaypoo.esgl.input

import com.itaypoo.esgl.core.Vector2
import com.raylib.Raylib

/**
 * Handles user input of all kinds.
 *
 * @property mousePosition the current position of the mouse cursor, relative to the window size.
 * @property mouseWheelDelta how much the mouse wheel moved on this frame, vertically and horizontally. Horizontal scrolling is only available on certain input methods, like trackpads.
 * @property mouseCursor the look of the mouse cursor. (pointing hand, I-beam, cross-hair...)
 */
object Input {
    val mousePosition: Vector2 = Vector2(0, 0)
        get() {
            field.set(Raylib.GetMousePosition())
            return field
        }
    val mouseWheelDelta: Vector2 = Vector2(0, 0)
        get() {
            field.set(Raylib.GetMouseWheelMoveV())
            return field
        }


    var mouseCursor = MouseCursor.DEFAULT
        set(value) {
            Raylib.SetMouseCursor(value.code)
            field = value
        }

    /** Checks whether a key has been pressed in this frame. */
    fun isKeyPressed(key: Key) = Raylib.IsKeyPressed(key.code)
    /** Checks whether a key has been released in this frame. */
    fun isKeyReleased(key: Key) = Raylib.IsKeyReleased(key.code)
    /** Checks whether a key is currently held. */
    fun isKeyHeld(key: Key) = Raylib.IsKeyDown(key.code)

    /** Checks whether a mouse button has been pressed in this frame. */
    fun isMouseButtonPressed(button: MouseButton) = Raylib.IsMouseButtonPressed(button.code)
    /** Checks whether a mouse button has been released in this frame. */
    fun isMouseButtonReleased(button: MouseButton) = Raylib.IsMouseButtonReleased(button.code)
    /** Checks whether a mouse button is currently held. */
    fun isMouseButtonHeld(button: MouseButton) = Raylib.IsMouseButtonDown(button.code)
}