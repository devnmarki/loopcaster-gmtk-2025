package com.devnmarki.game.ui;

import com.devnmarki.engine.assets.ResourceManager;
import com.devnmarki.engine.math.Vector2;
import com.devnmarki.engine.scene.SceneManager;
import com.devnmarki.engine.ui.Image;
import com.devnmarki.engine.ui.Label;
import com.devnmarki.engine.ui.Widget;
import com.devnmarki.game.Globals;
import com.devnmarki.game.characters.Player;

public class ScoreUI extends Widget {

    private Label scoreText;
    private Image scoreIcon;

    @Override
    public void onStart() {
        super.onStart();

        scoreText =
            new Label(Globals.FONT)
                .setColor(Globals.Colors.PRIMARY)
                .setFontSize(50)
                .setBorderColor(Globals.Colors.SECONDARY)
                .setBorderWidth(2f)
                .setContent(Integer.toString(Player.getInstance().getScore()));
        scoreIcon =
            new Image()
                .setTexture(ResourceManager.loadTexture("sprites/ui/score_icon.png"));

        instantiate(scoreText, transform.localPosition);
        instantiate(scoreIcon, transform.localPosition);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        float paddingX = 32f;
        float paddingY = 110f;

        scoreIcon.transform.localPosition = new Vector2(
            transform.localPosition.x + paddingX,
            SceneManager.currentScene.getCamera().getViewportHeight() - scoreText.getHeight() / 2f - paddingY - scoreIcon.getHeight() / 2f
        );
        scoreText.transform.localPosition = new Vector2(
            transform.localPosition.x + paddingX + scoreIcon.getWidth() + 16f,
            SceneManager.currentScene.getCamera().getViewportHeight() - scoreText.getHeight() / 2f - paddingY + 16f
        );

        scoreText.setContent(Integer.toString(Player.getInstance().getScore()));
    }
}
