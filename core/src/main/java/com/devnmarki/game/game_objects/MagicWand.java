package com.devnmarki.game.game_objects;

import com.devnmarki.engine.assets.ResourceManager;
import com.devnmarki.engine.ecs.Entity;
import com.devnmarki.engine.graphics.Sprite;
import com.devnmarki.engine.graphics.SpriteRenderer;

public class MagicWand extends Entity {

    private Sprite sprite;

    @Override
    public void onAwake() {
        super.onAwake();

        sprite = new Sprite(ResourceManager.loadTexture("sprites/game_objects/magic_wand.png"));

        addComponent(new SpriteRenderer().setSprite(sprite));
    }
}
