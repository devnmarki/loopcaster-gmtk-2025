package com.devnmarki.game;

import com.devnmarki.engine.ecs.Entity;
import com.devnmarki.game.components.EnemySpawner;

public class GameManager extends Entity {

    private static GameManager INSTANCE;

    @Override
    public void onAwake() {
        super.onAwake();

        INSTANCE = this;

        addComponent(new EnemySpawner());
    }

    public GameManager getInstance() {
        return INSTANCE;
    }
}
