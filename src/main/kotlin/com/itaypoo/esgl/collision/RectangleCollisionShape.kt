package com.itaypoo.esgl.collision

import com.itaypoo.esgl.core.Color
import com.itaypoo.esgl.core.PivotMode
import com.itaypoo.esgl.core.Vector2
import com.raylib.Raylib

class RectangleCollisionShape(
    position: Vector2,
    val size: Vector2,
    val pivotMode: PivotMode = PivotMode.NORMALIZED,
    val pivotPoint: Vector2 = Vector2(0.5f, 0.5f)
) : CollisionShape(position) {
    val topLeftCorner = Vector2(0, 0)
        get() {
            field.set(position)
            field.x -= if (pivotMode == PivotMode.PIXELS) pivotPoint.x else pivotPoint.x * size.x
            field.y -= if (pivotMode == PivotMode.PIXELS) pivotPoint.y else pivotPoint.x * size.y
            return field
        }
    val bottomRightCorner = Vector2(0, 0)
        get() {
            field.set(topLeftCorner)
            field += size
            return field
        }
    val rayRect = Raylib.Rectangle()
        get() {
            field.x(topLeftCorner.x).y(topLeftCorner.y).width(size.x).height(size.y)
            return field
        }

    override fun draw() {
        Raylib.DrawRectangleRec(rayRect, Color.SEETHROUGH_RED.rayColor)
    }

    override fun collidesWith(other: CollisionShape): Boolean {
        return when (other) {
            is RectangleCollisionShape -> Raylib.CheckCollisionRecs(rayRect, other.rayRect)
            is CircleCollisionShape -> Raylib.CheckCollisionCircleRec(other.position.rayVec, other.radius, rayRect)
            is LineCollisionShape -> other.collidesWith(this) // LineCollisionShape implements collision between it and other shapes
            else -> throw Error("Collision shapes not compatible")
        }
    }
}