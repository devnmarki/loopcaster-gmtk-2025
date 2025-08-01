package com.devnmarki.game.characters.enemies;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Contact;
import com.devnmarki.engine.Debug;
import com.devnmarki.engine.assets.ResourceManager;
import com.devnmarki.engine.ecs.Entity;
import com.devnmarki.engine.ecs.EntityDestroyer;
import com.devnmarki.engine.graphics.Sprite;
import com.devnmarki.engine.graphics.Spritesheet;
import com.devnmarki.engine.math.Vector2;
import com.devnmarki.engine.physics.BoxCollider;
import com.devnmarki.engine.physics.Rigidbody;
import com.devnmarki.game.game_objects.Bullet;

public class WatcherEnemy extends Enemy {

    private static final float MOVE_SPEED = 3f;

    private final Vector2 moveDirection = new Vector2(0f, 0f);

    public WatcherEnemy() {
        super(ResourceManager.loadTexture("sprites/characters/watcher.png"));
    }

    @Override
    public void onAwake() {
        super.onAwake();

        setHealth(3);

        addComponent(new BoxCollider().setSize(new Vector2(13f, 9f)).setOffset(new Vector2(3.5f, 7f)));
    }

    @Override
    public void onStart() {
        super.onStart();

        this.rigidbody.setGravityScale(0f);
        this.setKnockback(15f);

        generateInitialMoveDirection();
    }

    private void generateInitialMoveDirection() {
        moveDirection.x = MathUtils.randomBoolean() ? -1f : 1f;
        moveDirection.y = MathUtils.randomBoolean() ? -0.5f : 0.5f;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        move();
    }

    private void move() {
        rigidbody.setVelocity(new Vector2(moveDirection.x *  MOVE_SPEED, moveDirection.y * MOVE_SPEED));
    }

    @Override
    public void onCollisionEnter(Entity actor, Vector2 normal, Contact contact) {
        super.onCollisionEnter(actor, normal, contact);

        Vector2 collisionNormal = new Vector2(contact.getWorldManifold().getNormal().x, contact.getWorldManifold().getNormal().y);

        if (actor instanceof Bullet) return;

        if (collisionNormal.x != 0f) {
            moveDirection.x *= -1f;
        }
        else if (collisionNormal.y != 0f) {
            moveDirection.y *= -1f;
        }
    }

    @Override
    public void onDamage(int damage) {
        super.onDamage(damage);
    }

    @Override
    protected void die() {
        super.die();

        EntityDestroyer.queue(this);
    }
}
