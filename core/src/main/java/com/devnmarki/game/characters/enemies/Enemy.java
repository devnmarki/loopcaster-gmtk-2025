package com.devnmarki.game.characters.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.devnmarki.engine.ecs.Entity;
import com.devnmarki.engine.graphics.Sprite;
import com.devnmarki.engine.graphics.SpriteRenderer;
import com.devnmarki.engine.graphics.Spritesheet;
import com.devnmarki.engine.math.Vector2;

public abstract class Enemy extends Entity {

    private final Spritesheet spritesheet;

    private int health = 1;

    public Enemy(TextureRegion spritesheetTexture) {
        this.spritesheet = new Spritesheet(spritesheetTexture, 2, 1, new Vector2(16), false);
    }

    @Override
    public void onAwake() {
        super.onAwake();

        addComponent(new SpriteRenderer().setSprite(spritesheet.getSprite(0)));
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getHealth() {
        return health;
    }

}
