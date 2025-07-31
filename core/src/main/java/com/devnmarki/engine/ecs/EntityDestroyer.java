package com.devnmarki.engine.ecs;

import com.devnmarki.engine.scene.SceneManager;

import java.util.ArrayList;
import java.util.List;

public class EntityDestroyer {

    private static final List<Entity> pendingDestroy = new ArrayList<>();

    public static void queue(Entity e) {
        pendingDestroy.add(e);
    }

    public static void flush() {
        for (Entity e : pendingDestroy) {
            SceneManager.currentScene.removeEntity(e);
        }
        pendingDestroy.clear();
    }

}
