package com.devnmarki.game.ui;

import com.devnmarki.engine.Engine;
import com.devnmarki.engine.assets.ResourceManager;
import com.devnmarki.engine.math.Vector2;
import com.devnmarki.engine.scene.SceneManager;
import com.devnmarki.engine.ui.Box;
import com.devnmarki.engine.ui.Image;
import com.devnmarki.engine.ui.Label;
import com.devnmarki.engine.ui.Widget;
import com.devnmarki.game.Globals;
import com.devnmarki.game.characters.Player;

public class ManaBarUI extends Widget {

    private Box manaBarFill;
    private Box manaBarBg;
    private Label currentManaText;
    private Image manaIcon;

    private final float width = 300f;

    @Override
    public void onStart() {
        super.onStart();

        manaBarFill =
            (Box) new Box()
                .setSize(new Vector2(width, 32))
                .setColor(Globals.Colors.PRIMARY)
                .setRenderingLayer(200);
        manaBarBg =
            (Box) new Box()
                .setSize(manaBarFill.getSize())
                .setColor(Globals.Colors.SECONDARY)
                .setRenderingLayer(100);
        currentManaText =
            (Label) new Label(Globals.FONT)
                .setColor(Globals.Colors.PRIMARY)
                .setFontSize(50)
                .setBorderColor(Globals.Colors.SECONDARY)
                .setBorderWidth(2f)
                .setContent("10s")
                .setRenderingLayer(300);
        manaIcon =
            new Image()
                .setTexture(ResourceManager.loadTexture("sprites/ui/mana_icon.png"));

        instantiate(currentManaText, transform.localPosition);
        instantiate(manaBarBg, manaBarFill.transform.localPosition);
        instantiate(manaBarFill, transform.localPosition);
        instantiate(manaIcon, transform.localPosition);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if (SceneManager.currentScene.getCamera() == null) return;

        float paddingX = 32f;
        float paddingY = 32f;

        manaBarFill.transform.localPosition = new Vector2(
            transform.localPosition.x + paddingX + manaIcon.getWidth() + 16f,
            SceneManager.currentScene.getCamera().getViewportHeight() - manaBarFill.getSize().y - paddingY
        );
        manaBarBg.transform.localPosition = manaBarFill.transform.localPosition;
        currentManaText.transform.localPosition = new Vector2(
            transform.localPosition.x + paddingX + 5f + manaIcon.getWidth() + 16f,
            SceneManager.currentScene.getCamera().getViewportHeight() - currentManaText.getHeight() / 2f - paddingY + 8f
        );
        manaIcon.transform.localPosition = new Vector2(
            transform.localPosition.x + paddingX,
            SceneManager.currentScene.getCamera().getViewportHeight() - manaIcon.getHeight() / 2f - paddingY - 4 * Engine.gameScale
        );

        float progress = Player.getInstance().getCurrentMana() / Player.MAX_MANA;
        manaBarFill.setSize(new Vector2(width * progress, manaBarFill.getSize().y));

        currentManaText.setContent(Math.round(Player.getInstance().getCurrentMana()) + "s");
    }
}
