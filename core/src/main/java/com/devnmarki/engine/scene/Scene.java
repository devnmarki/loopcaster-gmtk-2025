package com.devnmarki.engine.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.devnmarki.engine.Engine;
import com.devnmarki.engine.ecs.ECSWorld;
import com.devnmarki.engine.ecs.Entity;
import com.devnmarki.engine.ecs.EntityDestroyer;
import com.devnmarki.engine.math.Vector2;
import com.devnmarki.engine.physics.Collider;
import com.devnmarki.engine.physics.CollisionContactListener;

import java.util.List;

public abstract class Scene {

    private ECSWorld ecsWorld = new ECSWorld();
    private World physicsWorld = new World(new com.badlogic.gdx.math.Vector2(0f, Engine.gravity), true);

    protected Camera camera;
    protected OrthographicCamera uiCamera;

    private FitViewport uiViewport;

    public void enter() {
        ecsWorld = new ECSWorld();

        if (physicsWorld != null) {
            physicsWorld.dispose();
        }
        physicsWorld = new World(new com.badlogic.gdx.math.Vector2(0f, Engine.gravity), true);
        physicsWorld.setContactListener(new CollisionContactListener());

        loadEntities();

        camera = new Camera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.transform.localPosition = new Vector2(camera.getViewportWidth() / 2f, camera.getViewportHeight() / 2f);

        uiCamera = new OrthographicCamera(camera.getViewportWidth(), camera.getViewportHeight());
        uiViewport = new FitViewport(camera.getViewportWidth(), camera.getViewportHeight(), uiCamera);
        uiCamera.position.x = camera.getViewportWidth() / 2f;
        uiCamera.position.y = camera.getViewportHeight() / 2f;
        uiCamera.update();
    }

    public void update() {
        physicsWorld.step(1 / 60f, 6, 2);

        EntityDestroyer.flush();
    }

    public void debug() { }
    public void leave() { }

    public abstract void loadEntities();

    public void addEntity(Entity entity) {
        ecsWorld.addEntity(entity);
    }

    public void addEntity(Entity entity, Vector2 position) {
        entity.transform.localPosition = position;
        ecsWorld.addEntity(entity);
    }

    public void removeEntity(Entity entity) {
        ecsWorld.removeEntity(entity);
    }

    public <T extends Entity> List<T> query(Class<T> type) {
        return ecsWorld.query(type);
    }

    public <T extends Entity> T queryFirst(Class<T> type) {
        return ecsWorld.queryFirst(type);
    }

    public ECSWorld getEcsWorld() {
        return ecsWorld;
    }

    public World getPhysicsWorld() {
        return physicsWorld;
    }

    public List<Entity> getEntities() {
        return ecsWorld.getEntities();
    }

    public Camera getCamera() {
        return camera;
    }

    public OrthographicCamera getUICamera() {
        return uiCamera;
    }

    public Viewport getUiViewport() {
        return uiViewport;
    }

}
