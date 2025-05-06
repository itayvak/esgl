package com.itaypoo.esgl.graphics

import com.itaypoo.esgl.core.*
import com.raylib.Raylib
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin


class Sprite(
    val texturePath: String,
    var position: Vector2 = Vector2(0, 0),
    val scale: Vector2 = Vector2(1, 1),
    var rotation: Float = 0f,
    var tint: Color = Color.WHITE,
    var pivotPoint: Vector2 = Vector2(0.5f, 0.5f),
    var pivotMode: PivotMode = PivotMode.NORMALIZED,
    var flipX: Boolean = false,
    var flipY: Boolean = false,
) : Drawable {

    private lateinit var texture: Raylib.Texture
    val textureSize: Vector2 = Vector2(0, 0)
        get() {
            field.set(texture.width(), texture.height())
            return field
        }
    private var isTextureLoaded = false

    private val textureBoundsRect = Raylib.Rectangle()
    private val textureDrawRect = Raylib.Rectangle()
    private val drawPositionRect = Raylib.Rectangle()
    private val textureOrigin = Vector2(0, 0)

    var isInCamera: Boolean = true
        private set

    override fun load() {
        isTextureLoaded = true
        texture = TextureManager.loadTexture(texturePath)
    }

    override fun unload() {
        isTextureLoaded = false
        TextureManager.unloadTexture(texturePath)
    }

    override fun update(deltaTime: Float, window: Window) {}

    override fun draw(window: Window) {
        if (!isTextureLoaded) throw IllegalStateException("ESGL: Sprite drawn without being loaded. Use the load() function before drawing a sprite.")

        computeTextureBounds()
        checkInCamera(window)

        if (isInCamera) {
            drawTexture()
            if(Debug.drawSpritesTextureBounds) drawTextureBounds()
            if(Debug.drawSpritesPositions) drawPositionPoint()
        }
    }

    private fun computeTextureBounds() {
        // This function is entirely written by ChatGPT. I'm sorry
        // 1. Scaled size
        val width  = texture.width()  * scale.x
        val height = texture.height() * scale.y

        // 2. Pivot in pixel space
        val px = pivotPoint.x * if (pivotMode == PivotMode.NORMALIZED) texture.width().toFloat()  else 1f
        val py = pivotPoint.y * if (pivotMode == PivotMode.NORMALIZED) texture.height().toFloat() else 1f
        val pivotX = px * scale.x
        val pivotY = py * scale.y

        // 3. Center relative to pivot
        //    rectangle center is half‐size offset from pivot
        val centerOffsetX = (width  * 0.5f) - pivotX
        val centerOffsetY = (height * 0.5f) - pivotY

        // 4. Rotate the center offset
        val rad = Math.toRadians(rotation.toDouble()).toFloat()
        val cos = cos(rad)
        val sin = sin(rad)
        val worldCenterX = position.x + centerOffsetX * cos - centerOffsetY * sin
        val worldCenterY = position.y + centerOffsetX * sin + centerOffsetY * cos

        // 5. Compute axis‐aligned half‐extents of rotated rect
        val halfW = width  * 0.5f
        val halfH = height * 0.5f
        val extentX = abs(cos) * halfW + abs(sin) * halfH
        val extentY = abs(sin) * halfW + abs(cos) * halfH

        // 6. Set final bounds
        textureBoundsRect.x(worldCenterX - extentX)
        textureBoundsRect.y(worldCenterY - extentY)
        textureBoundsRect.width( extentX * 2f )
        textureBoundsRect.height(extentY * 2f)
    }

    private fun checkInCamera(window: Window) {
        isInCamera = true
        if (window.camera == null) {
            if (
                textureBoundsRect.x() + textureBoundsRect.width() < -VIEWPORT_CHECK_GRACE_PIXELS ||  // left border
                textureBoundsRect.x() > window.currentSize.x + VIEWPORT_CHECK_GRACE_PIXELS ||  // right border
                textureBoundsRect.y() + textureBoundsRect.height() < -VIEWPORT_CHECK_GRACE_PIXELS ||  // top border
                textureBoundsRect.y() > window.currentSize.y + VIEWPORT_CHECK_GRACE_PIXELS  // bottom border
            ) {
                isInCamera = false
            }
        } else {
            if (
                textureBoundsRect.x() + textureBoundsRect.width() < window.camera!!.worldViewportTopLeft.x - VIEWPORT_CHECK_GRACE_PIXELS || // left border
                textureBoundsRect.x() > window.camera!!.worldViewPortBottomRight.x + VIEWPORT_CHECK_GRACE_PIXELS ||  // right border
                textureBoundsRect.y() + textureBoundsRect.height() < window.camera!!.worldViewportTopLeft.y - VIEWPORT_CHECK_GRACE_PIXELS ||  // top border
                textureBoundsRect.y() > window.camera!!.worldViewPortBottomRight.y + VIEWPORT_CHECK_GRACE_PIXELS  // bottom border
            ) {
                isInCamera = false
            }
        }
    }

    private fun drawTexture() {
        // texture draw rect is which part of the texture to draw. since we want to draw all of it, always make this take up all the texture.
        // negative values apply flipping
        textureDrawRect
            .x(0f)
            .y(0f)
            .width(if (flipX) -texture.width().toFloat() else texture.width().toFloat())
            .height(if (flipY) -texture.height().toFloat() else texture.height().toFloat())

        // draw position rect is where in the world to draw the texture.
        drawPositionRect
            .x(position.x)
            .y(position.y)
            .width(texture.width() * scale.x)
            .height(texture.height() * scale.y)

        // texture origin is the pivot point in pixels of the texture
        if (pivotMode == PivotMode.NORMALIZED) {
            textureOrigin.set(texture.width() * pivotPoint.x * scale.x, texture.height() * pivotPoint.y * scale.y)
        } else if (pivotMode == PivotMode.PIXELS) {
            textureOrigin.set(pivotPoint.x * scale.x, pivotPoint.y * scale.y)
        }

        // draw the texture!
        Raylib.DrawTexturePro(
            texture,
            textureDrawRect,
            drawPositionRect,
            textureOrigin.rayVec,
            rotation,
            tint.rayColor
        )
    }

    private fun drawPositionPoint() {
        Raylib.DrawCircleV(position.rayVec, 2f, Color.WHITE.rayColor)
    }

    private fun drawTextureBounds() {
        Raylib.DrawRectangleRec(textureBoundsRect, Color.SEETHROUGH_RED.rayColor)
    }

    private companion object {
        const val VIEWPORT_CHECK_GRACE_PIXELS =
            50f  // the sprite will only render if it is inside the camera viewport. during calculation, the viewport is expanded by this amount, to render sprites that are just a bit out of the viewport.
    }
}