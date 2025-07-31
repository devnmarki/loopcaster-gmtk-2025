package com.devnmarki.game.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.devnmarki.engine.Debug;
import com.devnmarki.engine.Engine;
import com.devnmarki.engine.assets.ResourceManager;
import com.devnmarki.engine.ecs.Entity;
import com.devnmarki.engine.graphics.SpriteRenderer;
import com.devnmarki.engine.graphics.Spritesheet;
import com.devnmarki.engine.graphics.animation.Animation;
import com.devnmarki.engine.graphics.animation.Animator;
import com.devnmarki.engine.input.Input;
import com.devnmarki.engine.math.Vector2;
import com.devnmarki.engine.physics.BoxCollider;
import com.devnmarki.engine.physics.Rigidbody;
import com.devnmarki.engine.ui.Label;
import com.devnmarki.game.Direction;
import com.devnmarki.game.Globals;
import com.devnmarki.game.game_objects.Bullet;
import com.devnmarki.game.game_objects.MagicWand;

import static com.badlogic.gdx.Input.*;

import java.util.List;

public class Player extends Entity {

    private static Player INSTANCE;

    private static final float MOVE_SPEED = 3f;
    private static final float JUMP_FORCE = 11f;
    private static final float FIRE_RATE = 0.15f;
    public static final float MAX_MANA = 10f;

    private Spritesheet spritesheet;
    private Animator animator;

    private Rigidbody rigidbody;
    private float input;
    private Direction facingDirection = Direction.Right;
    private boolean onGround = false;
    private int jumps = 2;

    private MagicWand magicWand;
    private Vector2 shootPoint;
    private float fireRateTimer = 0f;

    private float currentMana;

    @Override
    public void onAwake() {
        super.onAwake();

        INSTANCE = this;

        spritesheet = new Spritesheet(ResourceManager.loadTexture("sprites/characters/player_sheet.png"), 3, 2, new Vector2(16), false);

        addComponent(new SpriteRenderer().setSprite(spritesheet.getSprite(0)));
        addComponent(new BoxCollider().setSize(new Vector2(7, 14)).setOffset(new Vector2(6, 0)));
        addComponent(new Rigidbody().setGravityScale(Globals.GRAVITY_SCALE));
        addComponent(new Animator());
    }

    @Override
    public void onStart() {
        super.onStart();

        rigidbody = getComponent(Rigidbody.class);
        animator = getComponent(Animator.class);

        createInputActions();
        loadAnimations();

        jumps = 2;

        magicWand = new MagicWand();
        instantiate(magicWand, transform.localPosition);

        currentMana = MAX_MANA;
    }

    private void createInputActions() {
        Input.addAction("walk_left", List.of(Keys.LEFT), null);
        Input.addAction("walk_right", List.of(Keys.RIGHT), null);
        Input.addAction("jump", List.of(Keys.Z), null);
        Input.addAction("shoot", List.of(Keys.X), null);
    }

    private void loadAnimations() {
        animator.addAnimation("idle_right", new Animation(spritesheet, new int[] { 0 }, 0.1f, true, false));
        animator.addAnimation("idle_left", new Animation(spritesheet, new int[] { 0 }, 0.1f, true, true));
        animator.addAnimation("walk_right", new Animation(spritesheet, new int[] { 3, 4, 5 }, 0.15f, true, false));
        animator.addAnimation("walk_left", new Animation(spritesheet, new int[] { 3, 4, 5 }, 0.15f, true, true));
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        getInput();
        move();
        updateFacingDirection();
        updateCurrentAnimation();
        updateMagicWand();
        updateMana();

        if (onGround) {
            jumps = 2;
        }
    }

    private void getInput() {
        input = Input.getAxis("walk_left", "walk_right");

        if (Input.isJustPressed("jump") && jumps > 0) {
            jump();
        }

        fireRateTimer += Gdx.graphics.getDeltaTime();
        if (Input.isPressed("shoot") && fireRateTimer >= FIRE_RATE) {
            shoot();
        }
    }

    private void move() {
        rigidbody.setVelocity(new Vector2(input * MOVE_SPEED, rigidbody.getVelocity().y));
    }

    private void jump() {
        rigidbody.setVelocity(new Vector2(rigidbody.getVelocity().x, JUMP_FORCE));

        jumps--;
        if (jumps == 1)
            onGround = false;
    }

    private void shoot() {
        instantiate(new Bullet(facingDirection), shootPoint);
        fireRateTimer = 0f;
    }

    private void updateFacingDirection() {
        if (input > 0f) {
            facingDirection = Direction.Right;
        } else if (input < 0f) {
            facingDirection = Direction.Left;
        }
    }

    private void updateCurrentAnimation() {
        String animName = facingDirection.toString().toLowerCase();

        if (input == 0f)
            animator.play("idle_" + animName);
        else
            animator.play("walk_" + animName);
    }

    private void updateMagicWand() {
        if (facingDirection == Direction.Right) {
            magicWand.transform.localPosition.x = transform.localPosition.x - 4f * Engine.gameScale;
            magicWand.transform.localPosition.y = transform.localPosition.y - 2f * Engine.gameScale;
            shootPoint = new Vector2(magicWand.transform.localPosition.x + 23f * Engine.gameScale, magicWand.transform.localPosition.y + 8f * Engine.gameScale);
        }
        else {
            magicWand.transform.localPosition.x = transform.localPosition.x + 5f * Engine.gameScale;
            magicWand.transform.localPosition.y = transform.localPosition.y - 2f * Engine.gameScale;
            shootPoint = new Vector2(magicWand.transform.localPosition.x - 11f * Engine.gameScale, magicWand.transform.localPosition.y + 8f * Engine.gameScale);
        }
    }

    private void updateMana() {
        currentMana -= Gdx.graphics.getDeltaTime();
        if (currentMana <= 0f) {
            die();
            currentMana = 0f;
        }
        Debug.log(currentMana);
    }

    @Override
    public void onCollisionEnter(Entity actor, Vector2 normal, Contact contact) {
        super.onCollisionEnter(actor, normal, contact);

        if (normal.y < 0) {
            onGround = true;
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

    private void die() {
        Debug.log("u ded loser");
    }

    public static Player getInstance() {
        return INSTANCE;
    }

    public float getCurrentMana() {
        return currentMana;
    }

}
