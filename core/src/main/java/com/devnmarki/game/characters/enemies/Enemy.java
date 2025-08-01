package com.devnmarki.game.characters.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.devnmarki.engine.ecs.Entity;
import com.devnmarki.engine.graphics.Sprite;
import com.devnmarki.engine.graphics.SpriteRenderer;
import com.devnmarki.engine.graphics.Spritesheet;
import com.devnmarki.engine.math.Vector2;
import com.devnmarki.engine.physics.Rigidbody;
import com.devnmarki.game.IDamageable;

public abstract class Enemy extends Entity implements IDamageable {

    private final Spritesheet spritesheet;
    protected SpriteRenderer spriteRenderer;

    private int health = 1;

    private float hitEffectTimer = 0f;

    public Enemy(TextureRegion spritesheetTexture) {
        this.spritesheet = new Spritesheet(spritesheetTexture, 2, 1, new Vector2(16), false);
    }

    @Override
    public void onAwake() {
        super.onAwake();

        addComponent(new SpriteRenderer().setSprite(spritesheet.getSprite(0)));
        addComponent(new Rigidbody());
    }

    @Override
    public void onStart() {
        super.onStart();

        spriteRenderer = getComponent(SpriteRenderer.class);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if (hitEffectTimer > 0f) {
            hitEffectTimer -= Gdx.graphics.getDeltaTime();
            spriteRenderer.setSprite(spritesheet.getSprite(1)); // hit sprite
        } else {
            spriteRenderer.setSprite(spritesheet.getSprite(0)); // normal sprite
        }
    }

    @Override
    public void onDamage(int damage) {
        health -= damage;

        hitEffectTimer = 0.15f;

        if (health <= 0)
            die();
    }

    protected void die() {

    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getHealth() {
        return health;
    }

}
