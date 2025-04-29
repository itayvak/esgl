package com.itaypoo.esgl
import com.raylib.Raylib as rl

class Window(
    val initialSize: Vector2,
    val isResizable: Boolean = false,
    targetFPS: Int? = 60,
    var displayFPS: Boolean = false,
    val fullscreenToggleKey: Key? = null,
    val quitKey: Key? = null,
) {
    // Abstracts Raylib window creation logic.
    var lastUsedCamera: Camera? = null

    val currentSize: Vector2
        get() {
            return Vector2(rl.GetScreenWidth(), rl.GetScreenHeight())
        }

    init {
        if(isResizable) rl.SetConfigFlags(rl.FLAG_WINDOW_RESIZABLE)
        rl.InitWindow(initialSize.x.toInt(), initialSize.y.toInt(), "Raylib + Kotlin")
        rl.SetExitKey(quitKey?.code ?: 0)
        rl.SetTargetFPS(targetFPS ?: 0)  // 0 target fps means unlimited
    }

    fun shouldClose(): Boolean {
        return rl.WindowShouldClose()
    }

    fun close() {
        rl.CloseWindow()
    }

    fun beginDrawing(backgroundColor: Color = Color.BLACK, camera: Camera? = null) {
        rl.BeginDrawing()
        rl.ClearBackground(backgroundColor.toRayColor())
        lastUsedCamera = camera
        if (camera != null) rl.BeginMode2D(camera.rayCam)
        update()
    }

    fun endDrawing() {
        if (lastUsedCamera != null) rl.EndMode2D()
        if(displayFPS) rl.DrawFPS(10, 10)  // do this after drawing ends, so its always on top
        rl.EndDrawing()
    }

    private fun update() {
        // toggle fullscreen
        if (fullscreenToggleKey != null && Input.isKeyPressed(fullscreenToggleKey)) {
            val display = rl.GetCurrentMonitor()
            if (rl.IsWindowFullscreen()) {
                // go back to normal size
                rl.SetWindowSize(initialSize.x.toInt(), initialSize.y.toInt())
            } else {
                // set the window size to match the monitor we are on
                rl.SetWindowSize(rl.GetMonitorWidth(display), rl.GetMonitorHeight(display))
            }
            rl.ToggleFullscreen()
        }
    }
}