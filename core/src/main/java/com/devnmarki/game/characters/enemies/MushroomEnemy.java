package com.devnmarki.game.characters.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.devnmarki.engine.Engine;
import com.devnmarki.engine.assets.ResourceManager;
import com.devnmarki.engine.ecs.EntityDestroyer;
import com.devnmarki.engine.math.Vector2;
import com.devnmarki.engine.physics.BoxCollider;
import com.devnmarki.game.Direction;
import com.devnmarki.game.Globals;
import com.devnmarki.game.characters.Player;
import com.devnmarki.game.game_objects.EnemyBullet;

public class MushroomEnemy extends Enemy {

    private static final float FIRE_RATE = 0.75f;

    private boolean flip = false;
    private Direction facingDirection = Direction.Right;
    private Vector2 shootPoint;
    private float fireRateTimer = 0f;

    public MushroomEnemy() {
        super(ResourceManager.loadTexture("sprites/characters/mushroom.png"));
    }

    @Override
    public void onAwake() {
        super.onAwake();

        addComponent(new BoxCollider().setSize(new Vector2(9f, 15f)).setOffset(new Vector2(4.5f, 0f)));
    }

    @Override
    public void onStart() {
        super.onStart();

        setHealth(3);
        setKnockback(0f);
        rigidbody.setGravityScale(Globals.GRAVITY_SCALE);

        shootPoint = transform.localPosition;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        updateFacingDirection();
        updateShootPoint();
        shoot();
    }

    private void updateFacingDirection() {
        flip = Player.getInstance().transform.localPosition.x <= transform.localPosition.x;
        spriteRenderer.getSprite().setFlip(flip);

        facingDirection = flip ? Direction.Left : Direction.Right;
    }

    private void updateShootPoint() {
        if (facingDirection == Direction.Left) {
            shootPoint = new Vector2(transform.localPosition.x - 6f, transform.localPosition.y + 4f * Engine.gameScale);
        } else {
            shootPoint = new Vector2(transform.localPosition.x + 20f * Engine.gameScale, transform.localPosition.y + 4f * Engine.gameScale);
        }
    }

    private void shoot() {
        fireRateTimer += Gdx.graphics.getDeltaTime();
        if (fireRateTimer >= FIRE_RATE) {
            instantiate(new EnemyBullet(facingDirection), shootPoint);
            fireRateTimer = 0f;
        }
    }

    @Override
    public void onDebug() {
        super.onDebug();

        Engine.SHAPE_RENDERER.begin(ShapeRenderer.ShapeType.Filled);
        Engine.SHAPE_RENDERER.setColor(Color.RED);
        Engine.SHAPE_RENDERER.rect(shootPoint.x, shootPoint.y, 4f, 4f);
        Engine.SHAPE_RENDERER.end();
    }

    @Override
    protected void die() {
        super.die();

        EntityDestroyer.queue(this);
    }
}
