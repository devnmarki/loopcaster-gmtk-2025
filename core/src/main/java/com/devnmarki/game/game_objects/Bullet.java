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

public class Bullet extends Entity {

    private static final float LIFETIME = 0.5f;
    private static final float SPEED = 10f;

    private final Sprite sprite;
    private final Direction facingDirection;

    private Rigidbody rigidbody;
    private float moveDirection = 0f;

    private float currentLifetime = 0f;
    private boolean shouldDestroy = false;

    public Bullet(Direction facingDirection) {
        this.sprite = new Sprite(ResourceManager.loadTexture("sprites/game_objects/bullet.png"));
        this.facingDirection = facingDirection;
    }

    @Override
    public void onAwake() {
        super.onAwake();

        addComponent(new SpriteRenderer().setSprite(sprite));
        addComponent(new BoxCollider().setSize(new Vector2(4f)).setOffset(new Vector2(2f)).setSolid(false));
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
        if (currentLifetime >= LIFETIME) {
            EntityDestroyer.queue(this);
        }
    }

    private void move() {
        moveDirection = facingDirection == Direction.Right ? 1f : -1f;

        rigidbody.setVelocity(new Vector2(moveDirection * SPEED, rigidbody.getVelocity().y));
    }

    @Override
    public void onCollisionEnter(Entity actor, Vector2 normal, Contact contact) {
        super.onCollisionEnter(actor, normal, contact);

        shouldDestroy = true;
    }
}
