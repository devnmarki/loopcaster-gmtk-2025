package com.devnmarki.engine.ui;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.devnmarki.engine.Engine;

public class Image extends Widget {

    private TextureRegion texture;

    @Override
    public void onRender() {
        super.onRender();

        if (texture == null) return;

        Engine.SPRITE_BATCH.begin();
        Engine.SPRITE_BATCH.draw(
            texture,

            // Position
            transform.localPosition.x,
            transform.localPosition.y,

            // Origin (0,0)
            0f,
            0f,

            // Width & Height scaled
            texture.getRegionWidth(),
            texture.getRegionHeight(),

            // Scale
            Engine.gameScale,
            Engine.gameScale,

            // Rotation
            0f
        );
        Engine.SPRITE_BATCH.end();
    }

    public Image setTexture(TextureRegion texture) {
        this.texture = texture;

        return this;
    }

    public TextureRegion getTexture() {
        return texture;
    }

    public float getWidth() {
        return texture.getRegionWidth() * Engine.gameScale;
    }

    public float getHeight() {
        return texture.getRegionHeight() * Engine.gameScale;
    }

}
