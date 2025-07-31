package com.devnmarki.engine;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.devnmarki.engine.scene.SceneManager;

public class Engine {

    private static final Engine INSTANCE = new Engine();

    public static final SpriteBatch SPRITE_BATCH = new SpriteBatch();
    public static final SpriteBatch UI_SPRITE_BATCH = new SpriteBatch();
    public static final ShapeRenderer SHAPE_RENDERER = new ShapeRenderer();
    public static final float PPM = 100f;

    public static float gameScale = 1f;
    public static float gravity = -9.81f;
    public static boolean debugMode = false;

    public void update() {
        if (SceneManager.currentScene == null) return;

        SceneManager.updateCurrentScene();
    }

    public void dispose() {
        SPRITE_BATCH.dispose();

        if (SceneManager.currentScene != null)
            SceneManager.currentScene.getPhysicsWorld().dispose();
    }

    public void resize(int width, int height) {
        if (SceneManager.currentScene == null || SceneManager.currentScene.getCamera().getViewport() == null) return;

        SceneManager.currentScene.getCamera().getViewport().update(width, height, true);
        SceneManager.currentScene.getUiViewport().update(width, height, false);
    }

    public void clear(float r, float g, float b) {
        ScreenUtils.clear(r / 255f, g / 255f, b / 255f, 1f);
    }

    public static Engine getInstance() {
        return INSTANCE;
    }

}
