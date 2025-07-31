package com.devnmarki.engine.ecs;

import com.badlogic.gdx.physics.box2d.Contact;
import com.devnmarki.engine.Debug;
import com.devnmarki.engine.ecs.components.Component;
import com.devnmarki.engine.ecs.components.Transform;
import com.devnmarki.engine.math.Vector2;
import com.devnmarki.engine.scene.SceneManager;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Entity {

    private Map<Class<? extends Component>, Component> components = new HashMap<>();

    public Transform transform;

    public Entity() {
        transform = new Transform();
        addComponent(transform);
    }

    public void onAwake() {
        for (Component c : components.values()) {
            c.awake();
        }
    }

    public void onStart() {
        for (Component c : components.values()) {
            c.start();
        }
    }

    public void onUpdate() {
        for (Component c : components.values()) {
            c.update();
        }
    }

    public void onDebug() {
        for (Component c : components.values()) {
            c.debug();
        }
    }

    public void onDestroy() {
        components.clear();
    }

    public void onCollisionEnter(Entity actor, Vector2 normal, Contact contact) { }
    public void onCollisionExit(Entity actor) { }
    public void onPreCollision(Entity actor, Vector2 normal, Contact contact) { }
    public void onPostCollision(Entity actor, Vector2 normal, Contact contact) { }

    protected void instantiate(Entity newEntity, Vector2 newPosition) {
        newEntity.transform.localPosition = newPosition;
        SceneManager.currentScene.addEntity(newEntity);
    }

    protected void destroy(Entity entity) {
        SceneManager.currentScene.removeEntity(entity);
    }

    protected void destroySelf() {
        SceneManager.currentScene.removeEntity(this);
    }

    public <T extends Component> void addComponent(T component) {
        Class<? extends Component> type = component.getClass();
        if (components.containsKey(type)) {
            Debug.error("Component of type " + type + "already exists on entity.");
        }
        component.setEntity(this);

        components.put(type, component);
    }

    @SuppressWarnings("unchecked")
    public <T extends Component> T getComponent(Class<T> type) {
        Component component = components.get(type);
        if (component != null) {
            return (T) component;
        }
        throw new RuntimeException("Component of type " + type.getName() + " not found on entity.");
    }

    public <T extends Component> boolean hasComponent(T component) {
        return components.containsKey(component.getClass());
    }

    public Collection<Component> getAllComponents() {
        return components.values();
    }

    @SuppressWarnings("unchecked")
    public List<Entity> query(Class<? extends Entity> type) {
        return (List<Entity>) SceneManager.currentScene.query(type);
    }

    @SuppressWarnings("unchecked")
    public <T extends Entity> T queryFirst(Class<? extends Entity> type) {
        return (T) SceneManager.currentScene.queryFirst(type);
    }

    public Transform getTransform() {
        return transform;
    }

}
