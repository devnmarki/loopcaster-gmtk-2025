package com.devnmarki.engine.tilemap;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.devnmarki.engine.Debug;
import com.devnmarki.engine.Engine;
import com.devnmarki.engine.ecs.Entity;
import com.devnmarki.engine.ecs.components.Component;
import com.devnmarki.engine.math.Vector2;
import com.devnmarki.engine.scene.SceneManager;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class TilemapEntityLoader extends Component {

    private static final Map<String, Class<? extends Entity>> entityRegistry = new HashMap<>();

    private String layerName = "Entities";

    private TilemapComponent tilemapComp;

    public static void register(String name, Class<? extends Entity> entity) {
        entityRegistry.put(name, entity);
    }

    @Override
    public void start() {
        super.start();

        tilemapComp = entity.getComponent(TilemapComponent.class);

        loadEntities();
    }

    private void loadEntities() {
        if (tilemapComp == null || tilemapComp.getTiledMap() == null) return;

        MapLayer layer = tilemapComp.getTiledMap().getLayers().get(layerName);
        if (layer == null) return;

        for (MapObject obj : layer.getObjects()) {
            String objName = obj.getName();
            if (objName == null) {
                Debug.error("Skipping unnamed object.");
                continue;
            }

            Class<? extends Entity> clazz = entityRegistry.get(objName);
            if (clazz == null) {
                Debug.error("No entity registered for object: " + objName);
                continue;
            }

            float x = obj.getProperties().get("x", Float.class) * Engine.gameScale;
            float y = obj.getProperties().get("y", Float.class) * Engine.gameScale;

            Entity instance = instantiateEntity(clazz);
            if (instance != null) {
                instance.getTransform().localPosition = new Vector2(x, y);
                SceneManager.currentScene.addEntity(instance);
            }
        }
    }

    private Entity instantiateEntity(Class<? extends Entity> clazz) {
        try {
            return clazz.getConstructor().newInstance();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public TilemapEntityLoader setLayerName(String layerName) {
        this.layerName = layerName;

        return this;
    }

}
