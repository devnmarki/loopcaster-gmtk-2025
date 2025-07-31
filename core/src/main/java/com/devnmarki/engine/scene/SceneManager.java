package com.devnmarki.engine.scene;

import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.devnmarki.engine.Engine;

import java.util.*;

public class SceneManager {

    public static Map<String, Scene> scenes = new HashMap<>();
    public static Scene currentScene = null;

    private static Scene previousScene = null;
    private static Box2DDebugRenderer physicsDebugRenderer = new Box2DDebugRenderer();

    public static void addScene(String sceneName, Scene scene) {
        scenes.put(sceneName, scene);
    }

    public static void updateCurrentScene() {
        if (currentScene == null) return;

        Engine.SPRITE_BATCH.begin();

        currentScene.getEcsWorld().update();
        currentScene.update();

        if (Engine.debugMode) {
            currentScene.getEcsWorld().debug();
            currentScene.debug();
        }

        Engine.SPRITE_BATCH.end();

        if (Engine.debugMode) {
            physicsDebugRenderer.render(currentScene.getPhysicsWorld(), Engine.SPRITE_BATCH.getProjectionMatrix().cpy().scale(Engine.PPM, Engine.PPM, 1));
        }
    }

    public static void loadScene(String sceneName) {
        Scene newScene = scenes.get(sceneName);

        if (newScene == null) return;

        if (currentScene != newScene) {
            previousScene = currentScene;
            currentScene = newScene;

            if (previousScene != null)
                previousScene.leave();

            currentScene.enter();
        }
    }

}
