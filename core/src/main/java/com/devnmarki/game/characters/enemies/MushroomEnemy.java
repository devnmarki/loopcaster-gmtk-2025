package com.devnmarki.game.characters.enemies;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.devnmarki.engine.assets.ResourceManager;
import com.devnmarki.engine.ecs.EntityDestroyer;
import com.devnmarki.engine.math.Vector2;
import com.devnmarki.engine.physics.BoxCollider;
import com.devnmarki.game.Globals;
import com.devnmarki.game.characters.Player;

public class MushroomEnemy extends Enemy {

    private boolean flip = false;

    public MushroomEnemy() {
        super(ResourceManager.loadTexture("sprites/characters/mushroom.png"));
    }

    @Override
    public void onAwake() {
        super.onAwake();

        addComponent(new BoxCollider().setSize(new Vector2(9f, 15f)));
    }

    @Override
    public void onStart() {
        super.onStart();

        setHealth(3);
        setKnockback(0f);
        rigidbody.setGravityScale(Globals.GRAVITY_SCALE);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        flip = Player.getInstance().transform.localPosition.x <= transform.localPosition.x;
        spriteRenderer.getSprite().setFlip(flip);
    }

    @Override
    protected void die() {
        super.die();

        EntityDestroyer.queue(this);
    }
}
