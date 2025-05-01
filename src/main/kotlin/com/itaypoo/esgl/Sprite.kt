package com.itaypoo.esgl

import com.raylib.Raylib

enum class PivotMode {
    NORMALIZED, PIXELS
}

class Sprite(
    val texturePath: String,
    var position: Vector2 = Vector2(0, 0),
    val tint: Color = Color.WHITE,
    val pivotPoint: Vector2 = Vector2(0, 0),
    val pivotMode: PivotMode = PivotMode.NORMALIZED,
    val drawPivotPoint: Boolean = false,
) : Drawable {
    private lateinit var texture: Raylib.Texture
    private val drawPosition = Vector2(0, 0)
    private var skipDraw = false

    override fun load() {
        // if the texture is already loaded, use it.
        // else, load it with Raylib and save it in the texture pool.
        usedTexturePaths.add(texturePath)  // mark this texture as used, so it won't get removed from the pool
        val existingTexture = loadedTexturePool[texturePath]
        if (existingTexture != null) {
            texture = existingTexture
        } else {
            texture = Raylib.LoadTexture(texturePath)
            loadedTexturePool[texturePath] = texture
        }
    }

    override fun unload() {
        usedTexturePaths.remove(texturePath)  // mark this texture as not used by this sprite anymore
        // usedTexturePaths can have the same path more than once
        if (!usedTexturePaths.contains(texturePath)) {
            // this texture is not used by any sprites anymore; unload it from memory
            if (!loadedTexturePool.contains(texturePath)) throw IllegalStateException("ESGL Error: Trying to unload the texture $texturePath, but it was not found in the loaded texture pool.")
            Raylib.UnloadTexture(loadedTexturePool[texturePath]!!)
            loadedTexturePool.remove(texturePath)
        }
    }

    override fun update(deltaTime: Float, window: Window) {}

    override fun draw(window: Window) {
        if (!isTextureLoaded(texturePath)) throw IllegalStateException("Sprite drawn without being loaded. Use the load() function before drawing a sprite.")
        drawPosition.set(position)
        if (pivotMode == PivotMode.NORMALIZED) {
            drawPosition.x -= texture.width() * pivotPoint.x
            drawPosition.y -= texture.height() * pivotPoint.y
        } else if (pivotMode == PivotMode.PIXELS) {
            drawPosition.x -= pivotPoint.x
            drawPosition.y -= pivotPoint.y
        }

        skipDraw = false
        if (
            position.x < window.camera!!.worldViewportTopLeft.x - (texture.width() / 2) - VIEWPORT_CHECK_GRACE_PIXELS ||
            position.x > window.camera!!.worldViewPortBottomRight.x + (texture.width() / 2) + VIEWPORT_CHECK_GRACE_PIXELS ||
            position.y < window.camera!!.worldViewportTopLeft.y - (texture.height() / 2) - VIEWPORT_CHECK_GRACE_PIXELS ||
            position.y > window.camera!!.worldViewPortBottomRight.y + (texture.height() / 2) + VIEWPORT_CHECK_GRACE_PIXELS
        ) skipDraw = true
        if (!skipDraw) Raylib.DrawTextureV(texture, drawPosition.rayVec, tint.rayColor)

        if (drawPivotPoint) {
            Raylib.DrawCircleV(position.rayVec, 4f, Color.RED.rayColor)
            Raylib.DrawRectangle(
                position.x.toInt() - 10, position.y.toInt() - 1, 20, 2, Color.RED.rayColor
            )
            Raylib.DrawRectangle(
                position.x.toInt() - 1, position.y.toInt() - 10, 2, 20, Color.RED.rayColor
            )
        }
    }

    private companion object {
        const val VIEWPORT_CHECK_GRACE_PIXELS =
            50f  // the sprite will only render if it is inside the camera viewport. during calculation, the viewport is expanded by this amount, to render sprites that are just a bit out of the viewport.

        val loadedTexturePool: HashMap<String, Raylib.Texture> =
            HashMap()  // save all loaded textures to a pool, so using the same texture in more than one sprite won't load it multiple times.
        val usedTexturePaths: MutableList<String> =
            mutableListOf()  // a path can appear multiple times, meaning that it is used by more than one sprite.

        fun isTextureLoaded(path: String): Boolean {
            return loadedTexturePool.contains(path)
        }
    }
}