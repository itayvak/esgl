package com.itaypoo.esgl
import com.raylib.Raylib as rl

class Window(
    val title: String,
    val initialSize: Vector2,
    val isResizable: Boolean = false,
    val targetFPS: Int? = 60,
    private val enableVSync: Boolean = false,
    var displayFPS: Boolean = false,
    var fullscreenToggleKey: Key? = null,
    var quitKey: Key? = null,
    var backgroundColor: Color = Color.BLACK,
    var camera: Camera? = null
) {
    // Abstracts Raylib window creation logic.
    val currentSize: Vector2 = Vector2(0, 0)
        get() {
            field.set(rl.GetScreenWidth(), rl.GetScreenHeight())
            return field
        }

    init {
        if(isResizable) rl.SetConfigFlags(rl.FLAG_WINDOW_RESIZABLE)
        rl.InitWindow(initialSize.x.toInt(), initialSize.y.toInt(), title)
        if(enableVSync) rl.SetWindowState(rl.FLAG_VSYNC_HINT)
        rl.SetExitKey(quitKey?.code ?: 0)
        rl.SetTargetFPS(targetFPS ?: 0)  // 0 target fps means unlimited
    }

    fun run(onTick: (deltaTime: Float, window: Window) -> Unit) {
        // main loop
        while (!rl.WindowShouldClose()) {
            // -- start drawing
            rl.BeginDrawing()
            rl.ClearBackground(backgroundColor.rayColor)
            if (camera != null) rl.BeginMode2D(camera!!.rayCam)
            // -- update and draw
            update()
            onTick(rl.GetFrameTime(), this)
            // -- end drawing
            if(camera != null) rl.EndMode2D()
            if(displayFPS) rl.DrawFPS(10, 10)  // do this after drawing ends, so its always on top
            rl.EndDrawing()
        }
        rl.CloseWindow()
    }

    private fun update() {
        // toggle fullscreen
        if (fullscreenToggleKey != null && Input.isKeyPressed(fullscreenToggleKey!!)) {
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