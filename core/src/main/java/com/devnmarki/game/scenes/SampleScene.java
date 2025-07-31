package com.devnmarki.game.scenes;

import com.devnmarki.engine.scene.Scene;
import com.devnmarki.game.Assets;

public class SampleScene extends Scene {

    @Override
    public void loadEntities() {
        addEntity(Assets.Tilemaps.TEST_LEVEL);
    }

    @Override
    public void enter() {
        super.enter();

        addEntity(camera);
    }
}
