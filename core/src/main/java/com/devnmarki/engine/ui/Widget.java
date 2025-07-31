package com.devnmarki.engine.ui;

import com.devnmarki.engine.ecs.Entity;

public abstract class Widget extends Entity {

   protected int layer = 0;

    public Widget() {
        this.isUI = true;
    }

    public void onRender() { }

    public Widget setLayer(int layer) {
        this.layer = layer;

        return this;
    }

    public int getLayer() {
        return layer;
    }

}
