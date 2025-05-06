package com.itaypoo.esgl.collision

import com.itaypoo.esgl.core.Color
import com.itaypoo.esgl.core.Vector2
import com.raylib.Raylib

class CircleCollisionShape(
    position: Vector2,
    var radius: Float,
) : CollisionShape(position) {

    override fun draw() {
        Raylib.DrawCircleV(position.rayVec, radius, Color.SEETHROUGH_RED.rayColor)
    }

    override fun collidesWith(other: CollisionShape): Boolean {
        return when (other) {
            is CircleCollisionShape -> Raylib.CheckCollisionCircles(position.rayVec, radius, other.position.rayVec, other.radius)
            is RectangleCollisionShape -> Raylib.CheckCollisionCircleRec(position.rayVec, radius, other.rayRect)
            is LineCollisionShape -> Raylib.CheckCollisionCircleLine(position.rayVec, radius, other.position.rayVec, other.endPoint.rayVec)
            else -> throw Error("Collision shapes not compatible")
        }
    }
}