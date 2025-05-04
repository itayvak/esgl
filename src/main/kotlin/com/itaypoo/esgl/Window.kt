package com.itaypoo.esgl
import com.raylib.Raylib as rl

/**
 * Handle window management and the main game thread. Create a new window then start the game using
 * the [run] method.
 *
 * @property title the title of the window.
 * @property initialSize the initial size of the window. Can change if [isResizable] is true.
 * @property isResizable whether the window can be resized by the user.
 * @property enableVSync whether to enable VSync rendering.
 * @property displayFPS maximum FPS the window will hit. Set this to `null` for no FPS cap.
 * @property fullscreenToggleKey optional, a key that will enable fullscreen on press.
 * @property quitKey optional, a key that will close the game on press.
 * @property backgroundColor the color to clear the screen on every
 * @property camera optional, the camera to draw the screen with
 */
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

    /**
     * Start the game process and opens the window.
     * @param onTick a function that gets called every tick, [displayFPS] times per second.
     */
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