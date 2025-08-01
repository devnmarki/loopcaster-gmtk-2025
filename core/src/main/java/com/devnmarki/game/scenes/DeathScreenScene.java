package com.devnmarki.game.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.Json;
import com.devnmarki.engine.Debug;
import com.devnmarki.engine.math.Vector2;
import com.devnmarki.engine.scene.Scene;
import com.devnmarki.engine.scene.SceneManager;
import com.devnmarki.engine.ui.Label;
import com.devnmarki.game.Assets;
import com.devnmarki.game.Globals;
import com.devnmarki.game.PlayerData;
import com.devnmarki.game.characters.Player;

public class DeathScreenScene extends Scene {

    private final Label label;
    private Label scoreText;
    private Label highscoreText;

    public DeathScreenScene() {
        this.label = new Label(Globals.FONT).setFontSize(50).setColor(Globals.Colors.PRIMARY).setContent("U ded loser, press [SPACE] to restart");
        this.scoreText = new Label(Globals.FONT).setFontSize(50).setColor(Globals.Colors.PRIMARY).setContent("Score: 0");
        this.highscoreText = new Label(Globals.FONT).setFontSize(50).setColor(Globals.Colors.PRIMARY).setContent("Highscore: 0");
    }

    @Override
    public void loadEntities() {
        addEntity(label);
        addEntity(scoreText);
        addEntity(highscoreText);
        addEntity(Assets.Tilemaps.MAIN_MENU_BG);
    }

    @Override
    public void enter() {
        super.enter();

        addEntity(camera);

        Json json = new Json();
        String playerJson = Gdx.files.local("save/player.json").readString();
        PlayerData data = json.fromJson(PlayerData.class, playerJson);
        scoreText.setContent("Score: " + data.score);
        highscoreText.setContent("Highscore: " + data.highscore);


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

        scoreText.transform.localPosition = new Vector2(
            camera.getViewportWidth() / 2f - scoreText.getWidth() / 2f,
            camera.getViewportHeight() - 64f
        );

        highscoreText.transform.localPosition = new Vector2(
            camera.getViewportWidth() / 2f - highscoreText.getWidth() / 2f,
            camera.getViewportHeight() - 64f - scoreText.getHeight() - 32f
        );
    }
}
