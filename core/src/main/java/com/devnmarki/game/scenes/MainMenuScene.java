package com.devnmarki.game.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.Align;
import com.devnmarki.engine.Engine;
import com.devnmarki.engine.assets.ResourceManager;
import com.devnmarki.engine.math.Vector2;
import com.devnmarki.engine.scene.Scene;
import com.devnmarki.engine.scene.SceneManager;
import com.devnmarki.engine.ui.Image;
import com.devnmarki.engine.ui.Label;
import com.devnmarki.game.Assets;
import com.devnmarki.game.Globals;

public class MainMenuScene extends Scene {

    private Label welcomeText;
    private Image title;

    @Override
    public void loadEntities() {
        welcomeText = new Label(Globals.FONT).setFontSize(50).setColor(Globals.Colors.PRIMARY).setAlignment(Align.left).setContent("Press [ANY KEY] to start");
        title = new Image().setTexture(ResourceManager.loadTexture("sprites/ui/title.png"));

        addEntity(title);
        addEntity(welcomeText);
        addEntity(Assets.Tilemaps.MAIN_MENU_BG);
    }

    @Override
    public void enter() {
        super.enter();

        addEntity(camera);
    }

    @Override
    public void update() {
        super.update();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)) {
            SceneManager.queueScene("world");
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }

        welcomeText.transform.localPosition = new Vector2(
            camera.getViewportWidth() / 2f - welcomeText.getWidth() / 2f,
            camera.getViewportHeight() / 2f + welcomeText.getHeight() / 2f
        );

        title.transform.localPosition = new Vector2(
            camera.getViewportWidth() / 2f - title.getWidth() / 2f,
            camera.getViewportHeight() - 48 * Engine.gameScale
        );
    }
}
