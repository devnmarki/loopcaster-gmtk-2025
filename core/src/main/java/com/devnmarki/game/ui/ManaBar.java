package com.devnmarki.game.ui;

import com.badlogic.gdx.graphics.Color;
import com.devnmarki.engine.Debug;
import com.devnmarki.engine.math.Vector2;
import com.devnmarki.engine.scene.SceneManager;
import com.devnmarki.engine.ui.Box;
import com.devnmarki.engine.ui.Label;
import com.devnmarki.engine.ui.Widget;
import com.devnmarki.game.Globals;
import com.devnmarki.game.characters.Player;

public class ManaBar extends Widget {

    private Box manaBarFill;
    private Box manaBarBg;
    private Label currentManaText;

    private final float width = 300f;

    @Override
    public void onStart() {
        super.onStart();

        manaBarFill =
            (Box) new Box()
                .setSize(new Vector2(width, 32))
                .setColor(Globals.Colors.PRIMARY)
                .setLayer(200);
        manaBarBg =
            (Box) new Box()
                .setSize(manaBarFill.getSize())
                .setColor(Globals.Colors.SECONDARY)
                .setLayer(100);
        currentManaText =
            (Label) new Label(Globals.FONT)
                .setColor(Globals.Colors.PRIMARY)
                .setFontSize(50)
                .setBorderColor(Globals.Colors.SECONDARY)
                .setBorderWidth(2f)
                .setContent("10s")
                .setLayer(300);

        instantiate(currentManaText, transform.localPosition);
        instantiate(manaBarBg, manaBarFill.transform.localPosition);
        instantiate(manaBarFill, transform.localPosition);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if (SceneManager.currentScene.getCamera() == null) return;

        float barPaddingX = 64f;
        float currentManaTextPaddingX = 43f;
        float paddingY = 32f;

        manaBarFill.transform.localPosition = new Vector2(
            transform.localPosition.x + barPaddingX,
            SceneManager.currentScene.getCamera().getViewportHeight() - manaBarFill.getSize().y - paddingY
        );
        manaBarBg.transform.localPosition = manaBarFill.transform.localPosition;
        currentManaText.transform.localPosition = new Vector2(
            transform.localPosition.x + currentManaTextPaddingX,
            SceneManager.currentScene.getCamera().getViewportHeight() - currentManaText.getHeight() / 2f - paddingY + 8f
        );

        float progress = Player.getInstance().getCurrentMana() / Player.MAX_MANA;
        manaBarFill.setSize(new Vector2(width * progress, manaBarFill.getSize().y));

        currentManaText.setContent(Math.round(Player.getInstance().getCurrentMana()) + "s");
    }
}
