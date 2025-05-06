package com.itaypoo.esgl.collision

import com.itaypoo.esgl.core.Loadable
import com.itaypoo.esgl.core.Vector2

abstract class CollisionShape(
    val position: Vector2
) : Loadable {
    val groups: MutableList<String> = mutableListOf()

    val collidingShapes: List<CollisionShape>
        get() {
            val list = mutableListOf<CollisionShape>()
            for(collider in allColliders) {
                if(collidesWith(collider)) list.add(collider)
            }
            return list.toList()
        }

    override fun unload() {
        allColliders.remove(this)
    }

    override fun load() {
        allColliders.add(this)
    }

    abstract fun collidesWith(other: CollisionShape): Boolean
    abstract fun draw()

    fun collidingWithGroup(group: String): Boolean {
        return collidingShapes.find { c -> c.groups.contains(group) } != null
    }

    companion object {
        val allColliders: MutableList<CollisionShape> = mutableListOf()
    }
}