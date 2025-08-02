package com.devnmarki.game.scenes;

import com.devnmarki.engine.scene.Scene;
import com.devnmarki.game.Assets;
import com.devnmarki.game.GameManager;
import com.devnmarki.game.components.CameraController;
import com.devnmarki.game.ui.ManaBarUI;
import com.devnmarki.game.ui.ScoreUI;

public class WorldScene extends Scene {

    @Override
    public void loadEntities() {
        addEntity(Assets.Tilemaps.WORLD);

        addEntity(new GameManager());
        addEntity(new ManaBarUI());
        addEntity(new ScoreUI());
    }

    @Override
    public void enter() {
        super.enter();

        camera.addComponent(new CameraController());
        addEntity(camera);
    }
}
