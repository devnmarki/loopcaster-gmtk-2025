package com.devnmarki.game.scenes;

import com.badlogic.gdx.graphics.Color;
import com.devnmarki.engine.scene.Scene;
import com.devnmarki.engine.ui.Label;
import com.devnmarki.game.Assets;
import com.devnmarki.game.components.CameraController;

import java.awt.*;

public class SampleScene extends Scene {

    @Override
    public void loadEntities() {
        addEntity(Assets.Tilemaps.TEST_LEVEL);
    }

    @Override
    public void enter() {
        super.enter();

        camera.addComponent(new CameraController());
        addEntity(camera);
    }
}
