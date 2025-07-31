package com.devnmarki.game.characters;

import com.devnmarki.engine.assets.ResourceManager;
import com.devnmarki.engine.ecs.Actor;
import com.devnmarki.engine.graphics.SpriteRenderer;
import com.devnmarki.engine.graphics.Spritesheet;
import com.devnmarki.engine.math.Vector2;
import com.devnmarki.engine.physics.Rigidbody;

public class Player extends Actor {

    // GFX
    private Spritesheet spritesheet;

    @Override
    public void onAwake() {
        super.onAwake();

        spritesheet = new Spritesheet(ResourceManager.loadTexture("sprites/characters/player_sheet.png"), 3, 2, new Vector2(16), false);

        addComponent(new SpriteRenderer().setSprite(spritesheet.getSprite(0)));
    }
}
