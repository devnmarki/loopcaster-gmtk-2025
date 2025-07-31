package com.devnmarki.engine.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Sprite {

    private TextureRegion texture;
    private boolean flip;

    public Sprite(TextureRegion texture, boolean flip) {
        this.texture = texture;
        this.flip = flip;
    }

    public Sprite(TextureRegion texture) {
        this(texture, false);
    }

    public Sprite(Sprite sprite) {
        this.texture = new TextureRegion(sprite.getTexture());
        this.flip = false;
        this.setFlip(sprite.isFlip());
    }

    public void setFlip(boolean flip) {
        texture.flip(false, false);
        if (this.flip != flip) {
            texture.flip(true, false);
            this.flip = flip;
        }
    }

    public TextureRegion getTexture() {
        return texture;
    }

    public boolean isFlip() {
        return flip;
    }

}
