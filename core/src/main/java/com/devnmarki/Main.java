package com.devnmarki;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.devnmarki.engine.Engine;
import com.devnmarki.engine.scene.SceneManager;
import com.devnmarki.engine.tilemap.TilemapEntityLoader;
import com.devnmarki.game.characters.Player;
import com.devnmarki.game.scenes.SampleScene;

import static com.badlogic.gdx.Input.*;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {

    @Override
    public void create() {
        super.create();

        Engine.gameScale = 4f;

        TilemapEntityLoader.register("Player", Player.class);

        SceneManager.addScene("sample", new SampleScene());
        SceneManager.loadScene("sample");
    }

    @Override
    public void render() {
        super.render();

        Engine.getInstance().clear(0, 9, 36);
        Engine.getInstance().update();

        if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
            Gdx.app.exit();
        }

        if (Gdx.input.isKeyJustPressed(Keys.TAB)) {
            Engine.debugMode = !Engine.debugMode;
        }
    }

    @Override
    public void dispose() {
        super.dispose();

        Engine.getInstance().dispose();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);

        Engine.getInstance().resize(width, height);
    }
}
