package com.devnmarki.engine.ecs.components;

import com.devnmarki.engine.ecs.Entity;

public abstract class Component {

    protected Entity entity = null;

    public void awake() { }
    public void start() { }
    public void update() { }
    public void debug() { }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public Entity getEntity() {
        return entity;
    }

}
