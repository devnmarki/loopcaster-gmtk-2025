package com.devnmarki.game.ui;

import com.badlogic.gdx.graphics.Color;
import com.devnmarki.engine.Debug;
import com.devnmarki.engine.math.Vector2;
import com.devnmarki.engine.scene.SceneManager;
import com.devnmarki.engine.ui.Box;
import com.devnmarki.engine.ui.Widget;
import com.devnmarki.game.Globals;
import com.devnmarki.game.characters.Player;

public class ManaBar extends Widget {

    private Box manaBarFill;
    private Box manaBarBg;

    private float width = 300f;

    @Override
    public void onStart() {
        super.onStart();

        manaBarFill = new Box().setSize(new Vector2(width, 32)).setColor(Globals.Colors.PRIMARY);
        manaBarBg = new Box().setSize(manaBarFill.getSize()).setColor(Globals.Colors.SECONDARY);

        instantiate(manaBarBg, manaBarFill.transform.localPosition);
        instantiate(manaBarFill, transform.localPosition);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if (SceneManager.currentScene.getCamera() == null) return;

        float padding = 32f;

        manaBarFill.transform.localPosition = new Vector2(
            transform.localPosition.x + padding,
            SceneManager.currentScene.getCamera().getViewportHeight() - manaBarFill.getSize().y - padding
        );

        float progress = Player.getInstance().getCurrentMana() / Player.MAX_MANA;
        manaBarFill.setSize(new Vector2(width * progress, manaBarFill.getSize().y));

        manaBarBg.transform.localPosition = manaBarFill.transform.localPosition;
    }
}
