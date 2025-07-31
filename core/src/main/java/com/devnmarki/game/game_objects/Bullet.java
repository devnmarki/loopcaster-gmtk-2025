package com.devnmarki.game.game_objects;

import com.devnmarki.engine.assets.ResourceManager;
import com.devnmarki.engine.ecs.Entity;
import com.devnmarki.engine.graphics.Sprite;
import com.devnmarki.engine.graphics.SpriteRenderer;
import com.devnmarki.engine.math.Vector2;
import com.devnmarki.engine.physics.BoxCollider;

public class Bullet extends Entity {

    private final Sprite sprite;

    public Bullet() {
        this.sprite = new Sprite(ResourceManager.loadTexture("sprites/game_objects/bullet.png"));
    }

    @Override
    public void onAwake() {
        super.onAwake();

        addComponent(new SpriteRenderer().setSprite(sprite));
        addComponent(new BoxCollider().setSize(new Vector2(4f)).setOffset(new Vector2(2f)).setSolid(false));
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
    }
}
