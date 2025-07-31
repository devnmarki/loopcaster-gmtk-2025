package com.devnmarki.engine.graphics;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.devnmarki.engine.math.Vector2;

import java.util.ArrayList;
import java.util.List;

public class Spritesheet {

    private TextureRegion texture;
    private int cols;
    private int rows;
    private Vector2 spriteSize;
    private boolean flip;

    private List<Sprite> sprites = new ArrayList<>();

    public Spritesheet(TextureRegion texture, int cols, int rows, Vector2 spriteSize, boolean flip) {
        this.texture = texture;
        this.cols = cols;
        this.rows = rows;
        this.spriteSize = spriteSize;
        this.flip = flip;

        this.generateSprites();
    }

    private void generateSprites() {
        TextureRegion[][] textures = texture.split((int) spriteSize.x, (int) spriteSize.y);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Sprite sprite = new Sprite(textures[i][j]);
                sprite.setFlip(flip);
                sprites.add(sprite);
            }
        }
    }

    public Sprite getSprite(int index) {
        return sprites.get(index);
    }

    public List<Sprite> getSprites() {
        return sprites;
    }

    public TextureRegion getTexture() {
        return texture;
    }

}