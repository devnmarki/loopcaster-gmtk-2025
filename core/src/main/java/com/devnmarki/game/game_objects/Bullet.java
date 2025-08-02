package com.devnmarki.game.game_objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.devnmarki.engine.assets.ResourceManager;
import com.devnmarki.engine.ecs.Entity;
import com.devnmarki.engine.ecs.EntityDestroyer;
import com.devnmarki.engine.graphics.Sprite;
import com.devnmarki.engine.graphics.SpriteRenderer;
import com.devnmarki.engine.math.Vector2;
import com.devnmarki.engine.physics.BoxCollider;
import com.devnmarki.engine.physics.Rigidbody;
import com.devnmarki.game.Direction;
import com.devnmarki.game.IDamageable;
import com.devnmarki.game.characters.enemies.Enemy;

public class Bullet extends Entity {

    private final Sprite sprite;
    private final Direction facingDirection;
    private final float speed;
    private final float lifetime;

    private Rigidbody rigidbody;
    private float moveDirection = 0f;

    private float currentLifetime = 0f;
    private boolean shouldDestroy = false;

    public Bullet(Direction facingDirection, float speed, float lifetime) {
        this.sprite = new Sprite(ResourceManager.loadTexture("sprites/game_objects/bullet.png"));
        this.facingDirection = facingDirection;
        this.speed = speed;
        this.lifetime = lifetime;
    }

    @Override
    public void onAwake() {
        super.onAwake();

        addComponent(new SpriteRenderer().setSprite(sprite));
        addComponent(new BoxCollider().setSize(new Vector2(6f)).setOffset(new Vector2(4f)).setSolid(false));
        addComponent(new Rigidbody().setGravityScale(0f));
    }

    @Override
    public void onStart() {
        super.onStart();

        rigidbody = getComponent(Rigidbody.class);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        updateCurrentLifetime();
        move();

        if (shouldDestroy) {
            EntityDestroyer.queue(this);
        }
    }

    private void updateCurrentLifetime() {
        currentLifetime += Gdx.graphics.getDeltaTime();
        if (currentLifetime >= lifetime) {
            EntityDestroyer.queue(this);
        }
    }

    private void move() {
        moveDirection = facingDirection == Direction.Right ? 1f : -1f;

        rigidbody.setVelocity(new Vector2(moveDirection * speed, rigidbody.getVelocity().y));
    }

    public void setShouldDestroy(boolean shouldDestroy) {
        this.shouldDestroy = shouldDestroy;
    }

    public float getMoveDirection() {
        return moveDirection;
    }

}
