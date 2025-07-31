package com.devnmarki.engine.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.devnmarki.engine.Engine;
import com.devnmarki.engine.math.Vector2;

public class Box extends Widget {

    private Vector2 size = new Vector2(1f);
    private Color color = Color.WHITE;

    @Override
    public void onRender() {
        super.onRender();

        Engine.SHAPE_RENDERER.begin(ShapeRenderer.ShapeType.Filled);
        Engine.SHAPE_RENDERER.setColor(color);
        Engine.SHAPE_RENDERER.rect(transform.localPosition.x, transform.localPosition.y, size.x, size.y);
        Engine.SHAPE_RENDERER.end();
    }

    public Box setSize(Vector2 size) {
        this.size = size;

        return this;
    }

    public Box setColor(Color color) {
        this.color = color;

        return this;
    }

    public Vector2 getSize() {
        return size;
    }

}
