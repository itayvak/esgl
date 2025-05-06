package com.itaypoo.esgl.graphics

import com.raylib.Raylib

object TextureManager {
    private val loadedTextures: HashMap<String, Raylib.Texture> = HashMap()
    private val pathsInUse: MutableList<String> = mutableListOf()

    fun loadTexture(path: String): Raylib.Texture {
        pathsInUse.add(path)  // mark this texture as used, so it won't get removed from the pool
        // if the texture is already loaded, use it.
        // else, load it with Raylib and save it in the texture pool.
        val existingTexture = loadedTextures[path]
        if (existingTexture != null) {
            return existingTexture
        } else {
            val texture = Raylib.LoadTexture(path)
            loadedTextures[path] = texture
            return texture
        }
    }

    fun unloadTexture(path: String): Boolean {
        pathsInUse.remove(path)  // mark this texture as not used by this sprite anymore
        // pathsInUse can have the same path more than once
        if (!pathsInUse.contains(path)) {
            // this texture is not used by any sprites anymore; unload it from memory
            if (!loadedTextures.contains(path)) throw IllegalStateException("ESGL Error: Trying to unload the texture $path, but it was not found in the loaded texture pool.")
            Raylib.UnloadTexture(loadedTextures[path]!!)
            loadedTextures.remove(path)
            return true
        }
        return false
    }
}