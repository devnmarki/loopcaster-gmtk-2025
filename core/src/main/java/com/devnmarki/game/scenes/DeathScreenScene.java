package com.devnmarki.game.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.devnmarki.engine.math.Vector2;
import com.devnmarki.engine.scene.Scene;
import com.devnmarki.engine.scene.SceneManager;
import com.devnmarki.engine.ui.Label;
import com.devnmarki.game.Assets;
import com.devnmarki.game.Globals;

public class DeathScreenScene extends Scene {

    private final Label label;

    public DeathScreenScene() {
        this.label = new Label(Globals.FONT).setFontSize(50).setColor(Globals.Colors.PRIMARY).setContent("U ded loser, press [SPACE] to restart");
    }

    @Override
    public void loadEntities() {
        addEntity(label);
        addEntity(Assets.Tilemaps.MAIN_MENU_BG);
    }

    @Override
    public void enter() {
        super.enter();

        addEntity(camera);

        Assets.Sounds.DEATH.play();
    }

    @Override
    public void update() {
        super.update();

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            SceneManager.loadScene("sample");
        }

        label.transform.localPosition = new Vector2(
            camera.getViewportWidth() / 2f - label.getWidth() / 2f,
            camera.getViewportHeight() / 2f + label.getHeight() / 2f
        );
    }
}
