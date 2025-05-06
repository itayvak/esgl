package com.itaypoo.esgl.collision

import com.itaypoo.esgl.core.Color
import com.itaypoo.esgl.core.Vector2
import com.raylib.Raylib

class LineCollisionShape(
    position: Vector2,
    val length: Vector2,
) : CollisionShape(position) {
    val endPoint: Vector2 = Vector2(0, 0)
        get() {
            field.set(position)
            field += length
            return field
        }

    val tmpRectTopRight = Vector2(0, 0)
    val tmpRectBottomLeft = Vector2(0, 0)

    override fun collidesWith(other: CollisionShape): Boolean {
        return when (other) {
            is LineCollisionShape -> Raylib.CheckCollisionLines(position.rayVec, endPoint.rayVec, other.position.rayVec, other.endPoint.rayVec, null)
            is CircleCollisionShape -> Raylib.CheckCollisionCircleLine(other.position.rayVec, other.radius, position.rayVec, endPoint.rayVec)
            is RectangleCollisionShape -> collidesWithRectangle(other)
            else -> throw Error("Collision shapes not compatible")
        }
    }

    override fun draw() {
        Raylib.DrawLineV(position.rayVec, endPoint.rayVec, Color.SEETHROUGH_RED.rayColor)
    }

    fun collidesWithRectangle(rect: RectangleCollisionShape): Boolean {
        tmpRectTopRight.set(rect.bottomRightCorner.x, rect.topLeftCorner.y)
        tmpRectBottomLeft.set(rect.topLeftCorner.x, rect.bottomRightCorner.y)

        val intersects =
            Raylib.CheckCollisionLines(position.rayVec, endPoint.rayVec, rect.topLeftCorner.rayVec, tmpRectTopRight.rayVec, null) ||
                    Raylib.CheckCollisionLines(position.rayVec, endPoint.rayVec, tmpRectTopRight.rayVec, rect.bottomRightCorner.rayVec, null) ||
                    Raylib.CheckCollisionLines(position.rayVec, endPoint.rayVec, rect.bottomRightCorner.rayVec, tmpRectBottomLeft.rayVec, null) ||
                    Raylib.CheckCollisionLines(position.rayVec, endPoint.rayVec, tmpRectBottomLeft.rayVec, rect.topLeftCorner.rayVec, null)

        val inside =
            Raylib.CheckCollisionPointRec(position.rayVec, rect.rayRect) &&
                    Raylib.CheckCollisionPointRec(endPoint.rayVec, rect.rayRect)

        return intersects || inside
    }

}