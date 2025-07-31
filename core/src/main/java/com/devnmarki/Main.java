package com.devnmarki;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.devnmarki.engine.Engine;

import static com.badlogic.gdx.Input.*;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {

    @Override
    public void create() {
        super.create();

        Engine.gameScale = 4f;
    }

    @Override
    public void render() {
        super.render();

        Engine.getInstance().clear(255, 255, 255);
        Engine.getInstance().update();

        if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
            Gdx.app.exit();
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
