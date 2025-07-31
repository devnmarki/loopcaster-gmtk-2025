package com.devnmarki.engine.tilemap;


import com.devnmarki.engine.ecs.Entity;

public class Tilemap extends Entity {

    public Tilemap(String mapPath, String collisionLayer, String entityLayer) {
        this.addComponent(new TilemapComponent(mapPath));
        this.addComponent(new TilemapCollider(collisionLayer));
        this.addComponent(new TilemapEntityLoader().setLayerName(entityLayer));
    }

    public Tilemap(String mapPath) {
        this(mapPath, "Colliders", "Entities");
    }
}
