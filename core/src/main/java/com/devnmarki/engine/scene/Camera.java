package com.devnmarki.engine.scene;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.devnmarki.engine.ecs.Entity;

public class Camera extends Entity {

    private float viewportWidth;
    private float viewportHeight;

    private OrthographicCamera orthoCamera;
    private FitViewport viewport;

    public Camera(float viewportWidth, float viewportHeight) {
        this.viewportWidth = viewportWidth;
        this.viewportHeight = viewportHeight;
    }

    @Override
    public void onStart() {
        super.onStart();

        orthoCamera = new OrthographicCamera(viewportWidth, viewportHeight);
        viewport = new FitViewport(viewportWidth, viewportHeight, orthoCamera);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        orthoCamera.position.x = Math.round(transform.localPosition.x);
        orthoCamera.position.y = Math.round(transform.localPosition.y);
        orthoCamera.update();
    }

    public void setViewportWidth(float viewportWidth) {
        this.viewportWidth = viewportWidth;
    }

    public void setViewportHeight(float viewportHeight) {
        this.viewportHeight = viewportHeight;
    }

    public void setZoom(float zoom) {
        orthoCamera.zoom = zoom;
        orthoCamera.update();
    }

    public OrthographicCamera getOrthoCamera() {
        return orthoCamera;
    }

    public Viewport getViewport() {
        return viewport;
    }

    public Matrix4 getProjection() {
        return orthoCamera.combined;
    }

    public float getViewportWidth() {
        return viewportWidth;
    }

    public float getViewportHeight() {
        return viewportHeight;
    }

}
