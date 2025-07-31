package com.devnmarki.game.characters;

import com.badlogic.gdx.physics.box2d.Contact;
import com.devnmarki.engine.Debug;
import com.devnmarki.engine.assets.ResourceManager;
import com.devnmarki.engine.ecs.Actor;
import com.devnmarki.engine.ecs.Entity;
import com.devnmarki.engine.graphics.SpriteRenderer;
import com.devnmarki.engine.graphics.Spritesheet;
import com.devnmarki.engine.input.Input;
import com.devnmarki.engine.math.Vector2;
import com.devnmarki.engine.physics.BoxCollider;
import com.devnmarki.engine.physics.Rigidbody;
import com.devnmarki.game.Direction;
import com.devnmarki.game.Globals;

import static com.badlogic.gdx.Input.*;

import java.util.List;

public class Player extends Actor {

    private static final float MOVE_SPEED = 3f;
    private static final float JUMP_FORCE = 11f;

    private Spritesheet spritesheet;

    private Rigidbody rigidbody;
    private float input;
    private Direction facingDirection = Direction.Right;
    private boolean onGround = false;

    @Override
    public void onAwake() {
        super.onAwake();

        spritesheet = new Spritesheet(ResourceManager.loadTexture("sprites/characters/player_sheet.png"), 3, 2, new Vector2(16), false);

        addComponent(new SpriteRenderer().setSprite(spritesheet.getSprite(0)));
        addComponent(new BoxCollider().setSize(new Vector2(7, 14)).setOffset(new Vector2(4, 0)));
        addComponent(new Rigidbody().setGravityScale(Globals.GRAVITY_SCALE));
    }

    @Override
    public void onStart() {
        super.onStart();

        rigidbody = getComponent(Rigidbody.class);

        createInputActions();
    }

    private void createInputActions() {
        Input.addAction("walk_left", List.of(Keys.A), null);
        Input.addAction("walk_right", List.of(Keys.D), null);
        Input.addAction("jump", List.of(Keys.SPACE), null);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        getInput();
        move();
        updateFacingDirection();
    }

    private void getInput() {
        input = Input.getAxis("walk_left", "walk_right");

        if (Input.isJustPressed("jump") && onGround) {
            jump();
        }
    }

    private void move() {
        rigidbody.setVelocity(new Vector2(input * MOVE_SPEED, rigidbody.getVelocity().y));
    }

    private void jump() {
        rigidbody.setVelocity(new Vector2(rigidbody.getVelocity().x, JUMP_FORCE));
        onGround = false;
    }

    private void updateFacingDirection() {
        if (input < 0f) {
            facingDirection = Direction.Left;
        } else {
            facingDirection = Direction.Right;
        }
    }

    @Override
    public void onCollisionEnter(Entity actor, Vector2 normal, Contact contact) {
        super.onCollisionEnter(actor, normal, contact);

        if (normal.y < 0) {
            onGround = true;
        }
    }
}
