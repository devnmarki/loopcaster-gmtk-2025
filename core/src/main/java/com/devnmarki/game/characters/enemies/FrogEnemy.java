package com.devnmarki.game.characters.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Contact;
import com.devnmarki.engine.Debug;
import com.devnmarki.engine.assets.ResourceManager;
import com.devnmarki.engine.ecs.Entity;
import com.devnmarki.engine.ecs.EntityDestroyer;
import com.devnmarki.engine.math.Vector2;
import com.devnmarki.engine.physics.BoxCollider;
import com.devnmarki.game.Globals;
import com.devnmarki.game.characters.Player;
import com.devnmarki.game.game_objects.Bullet;

public class FrogEnemy extends Enemy {

    private static final float WAIT_TIME = 0.1f;

    private float moveDirection;
    private float waitTimer;
    private boolean onGround = false;
    private boolean flip = false;

    public FrogEnemy() {
        super(ResourceManager.loadTexture("sprites/characters/frog.png"));
    }

    @Override
    public void onAwake() {
        super.onAwake();

        addComponent(new BoxCollider().setSize(new Vector2(16f)));
    }

    @Override
    public void onStart() {
        super.onStart();

        setHealth(4);
        setKnockback(2f);
        rigidbody.setGravityScale(Globals.GRAVITY_SCALE);

        moveDirection = MathUtils.randomBoolean() ? 1f : -1f;
        waitTimer = WAIT_TIME;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        move();
        updateFacingDirection();
    }

    private void move() {
        if (!onGround) return;

        waitTimer += Gdx.graphics.getDeltaTime();
        if (waitTimer >= WAIT_TIME) {
            rigidbody.setVelocity(new Vector2(moveDirection * 1.5f, 5f));

            waitTimer = 0f;
            onGround = false;
        }
    }

    private void updateFacingDirection() {
        flip = moveDirection < 0f;
        spriteRenderer.getSprite().setFlip(flip);
    }

    @Override
    public void onCollisionEnter(Entity actor, Vector2 normal, Contact contact) {
        super.onCollisionEnter(actor, normal, contact);

        if (actor instanceof Bullet || actor instanceof Player || actor instanceof Enemy) return;

        Vector2 collisionNormal = new Vector2(contact.getWorldManifold().getNormal().x, contact.getWorldManifold().getNormal().y);

        if (collisionNormal.y > 0f) {
            onGround = true;
        }

        if (collisionNormal.x != 0f) {
            moveDirection *= -1f;
        }
    }
}
