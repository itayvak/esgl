package com.itaypoo.esgl

import com.raylib.Raylib

class Sprite(
    val texturePath: String,
    var position: Vector2 = Vector2(0, 0),
    val tint: Color = Color.WHITE,
) : Drawable {
    private lateinit var texture: Raylib.Texture

    override fun load() {
        texture = loadTexture(texturePath)
    }

    override fun unload() {
        unloadTexture(texturePath)
    }

    override fun update(deltaTime: Float, window: Window) {}

    override fun draw() {
        if(!isTextureLoaded(texturePath)) throw IllegalStateException("Sprite drawn without being loaded. Use the load() function before drawing a sprite.")
        Raylib.DrawTextureV(texture, position.toRayVec(), tint.toRayColor())
    }

    private companion object {
        val loadedTexturesByPath: MutableList<Pair<String, Raylib.Texture>> = mutableListOf()
        val pathsUsedBySprites: MutableList<String> = mutableListOf()  // a path can appear multiple times, meaning that it is used by more than one sprite.

        fun loadTexture(path: String): Raylib.Texture {
            pathsUsedBySprites.add(path)
            val existsTex = loadedTexturesByPath.find { it.first == path}
            if(existsTex != null) {
                return existsTex.second
            }
            val tex = Raylib.LoadTexture(path)
            loadedTexturesByPath.add(Pair(path, tex))
            return tex
        }

        fun unloadTexture(path: String) {
            val i = pathsUsedBySprites.indexOfFirst { it == path }
            if(i == -1) throw IllegalStateException("Trying to unload path: $path, not found to be used by any sprite")
            pathsUsedBySprites.removeAt(i)
            if(!pathsUsedBySprites.contains(path)) {
                val texI = loadedTexturesByPath.indexOfFirst { it.first == path }
                if (texI == -1) throw IllegalStateException("Trying to unload path: $path, loaded texture not found")
                Raylib.UnloadTexture(loadedTexturesByPath[texI].second)
                loadedTexturesByPath.removeAt(texI)
            }
        }

        fun isTextureLoaded(path: String): Boolean {
            return loadedTexturesByPath.find { it.first == path } != null
        }
    }
}