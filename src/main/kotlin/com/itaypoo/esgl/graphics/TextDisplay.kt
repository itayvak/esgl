package com.itaypoo.esgl.graphics

import com.itaypoo.esgl.core.Color
import com.itaypoo.esgl.core.Vector2
import com.itaypoo.esgl.core.Window
import com.raylib.Raylib

class TextDisplay(
    var text: String,
    val fontPath: String,
    val position: Vector2,
    var size: Float,
    var color: Color = Color.BLACK,
) : Drawable {
    private lateinit var font: Raylib.Font
    private var isFontLoaded = false

    override fun load() {
        isFontLoaded = true
        // if the font is already loaded, use it.
        // else, load it with Raylib and save it in the font pool.
        usedFontPaths.add(fontPath)  // mark this font as used, so it won't get removed from the pool
        val existingTexture = loadedFontPool[fontPath]
        if (existingTexture != null) {
            font = existingTexture
        } else {
            font = Raylib.LoadFont(fontPath)
            loadedFontPool[fontPath] = font
        }

        Raylib.SetTextureFilter(font.texture(), 1)
    }

    override fun unload() {
        isFontLoaded = false
        usedFontPaths.remove(fontPath)  // mark this font as not used by this textDisplay anymore
        // usedFontPaths can have the same path more than once
        if (!usedFontPaths.contains(fontPath)) {
            // this font is not used by any textDisplays anymore; unload it from memory
            if (!loadedFontPool.contains(fontPath)) throw IllegalStateException("ESGL Error: Trying to unload the font $fontPath, but it was not found in the loaded font pool.")
            Raylib.UnloadFont(loadedFontPool[fontPath]!!)
            loadedFontPool.remove(fontPath)
        }
    }

    override fun update(deltaTime: Float, window: Window) {}

    override fun draw(window: Window) {
        Raylib.DrawTextEx(font, text, position.rayVec, size, 0f, color.rayColor)
    }

    private companion object {
        val loadedFontPool: HashMap<String, Raylib.Font> =
            HashMap()  // save all loaded fonts to a pool, so using the same font in more than one textDisplay won't load it multiple times.
        val usedFontPaths: MutableList<String> =
            mutableListOf()  // a path can appear multiple times, meaning that it is used by more than one textDisplay.
    }
}