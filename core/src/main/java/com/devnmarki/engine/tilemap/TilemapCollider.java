package com.devnmarki.engine.tilemap;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.devnmarki.engine.Engine;
import com.devnmarki.engine.ecs.Entity;
import com.devnmarki.engine.ecs.components.Component;
import com.devnmarki.engine.math.Vector2;
import com.devnmarki.engine.physics.BoxCollider;
import com.devnmarki.engine.scene.SceneManager;

public class TilemapCollider extends Component {

    private TilemapComponent tilemap;

    private String layerName;

    public TilemapCollider(String layerName) {
        this.layerName = layerName;
    }

    @Override
    public void start() {
        super.start();

        tilemap = entity.getComponent(TilemapComponent.class);

        if (tilemap == null) return;

        MapLayer collisionLayer = tilemap.getTiledMap().getLayers().get(layerName);
        MapObjects colliderObjects = collisionLayer.getObjects();

        for (MapObject colliderObj : colliderObjects) {
            float w = colliderObj.getProperties().get("width", Float.class);
            float h = colliderObj.getProperties().get("height", Float.class);
            float x = (colliderObj.getProperties().get("x", Float.class) * Engine.gameScale);
            float y = (colliderObj.getProperties().get("y", Float.class) * Engine.gameScale);

            Vector2 colliderPos = new Vector2(x, y);
            Vector2 colliderSize = new Vector2(w, h);

            Entity colliderEntity = new Entity();
            colliderEntity.addComponent(new BoxCollider().setSize(colliderSize));
            colliderEntity.transform.localPosition = colliderPos;
            SceneManager.currentScene.addEntity(colliderEntity);
        }
    }

}
