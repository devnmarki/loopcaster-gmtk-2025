package com.devnmarki.game.characters.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Contact;
import com.devnmarki.engine.ecs.Entity;
import com.devnmarki.engine.graphics.Sprite;
import com.devnmarki.engine.graphics.SpriteRenderer;
import com.devnmarki.engine.graphics.Spritesheet;
import com.devnmarki.engine.math.Vector2;
import com.devnmarki.engine.physics.Rigidbody;
import com.devnmarki.game.Assets;
import com.devnmarki.game.Globals;
import com.devnmarki.game.IDamageable;
import com.devnmarki.game.characters.Player;
import com.devnmarki.game.game_objects.Bullet;
import com.devnmarki.game.game_objects.PlayerBullet;

public abstract class Enemy extends Entity implements IDamageable {

    private final Spritesheet spritesheet;

    protected SpriteRenderer spriteRenderer;
    protected Rigidbody rigidbody;

    private int health = 1;
    private float knockback = 2f;
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

        setLayer(Globals.EntityLayers.ENEMY);

        spriteRenderer = getComponent(SpriteRenderer.class);
        rigidbody = getComponent(Rigidbody.class);
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

        Assets.Sounds.HURT.play();

        if (health <= 0)
            die();
    }

    protected void die() {
        Player.getInstance().increaseMana(2f);
    }

    @Override
    public void onCollisionEnter(Entity actor, Vector2 normal, Contact contact) {
        super.onCollisionEnter(actor, normal, contact);

        if (actor instanceof PlayerBullet bullet) {
            rigidbody.setVelocity(new Vector2(knockback * bullet.getMoveDirection(), rigidbody.getVelocity().y));
        }

        if (actor instanceof Player player) {
            player.damage(1f);
        }
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setKnockback(float knockback) {
        this.knockback = knockback;
    }

}
