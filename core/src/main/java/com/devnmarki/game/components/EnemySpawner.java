package com.devnmarki.game.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.devnmarki.engine.Debug;
import com.devnmarki.engine.ecs.Entity;
import com.devnmarki.engine.ecs.components.Component;
import com.devnmarki.game.characters.enemies.*;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class EnemySpawner extends Component {

    private static final float MIN_WAIT_TIME = 2f;
    private static final float MAX_WAIT_TIME = 3.5f;

    private List<Entity> spawnPoints = new ArrayList<>();
    private final List<Class<? extends Enemy>> enemyTypes = new ArrayList<>();

    private float waitTimeTimer = 0f;
    private int enemyIndex;

    @Override
    public void start() {
        super.start();

        spawnPoints = entity.query(EnemySpawnPoint.class);

        enemyTypes.add(WatcherEnemy.class);
        enemyTypes.add(MushroomEnemy.class);
        enemyTypes.add(FrogEnemy.class);

        for (Entity spawnPoint : spawnPoints) {
            spawnEnemy(spawnPoint);
        }
    }

    @Override
    public void update() {
        super.update();

        waitTimeTimer += Gdx.graphics.getDeltaTime();
        if (waitTimeTimer >= MathUtils.random(MIN_WAIT_TIME, MAX_WAIT_TIME)) {
            spawnEnemyRandom();
        }
    }

    private void spawnEnemyRandom() {
        enemyIndex = MathUtils.random(enemyTypes.size() - 1);

        try {
            Entity enemyInstance = enemyTypes.get(enemyIndex).getConstructor().newInstance();
            entity.instantiate(enemyInstance, spawnPoints.get(MathUtils.random(spawnPoints.size() - 1)).transform.localPosition);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        waitTimeTimer = 0f;
    }

    private void spawnEnemy(Entity spawnPoint) {
        enemyIndex = MathUtils.random(enemyTypes.size() - 1);

        try {
            Entity enemyInstance = enemyTypes.get(enemyIndex).getConstructor().newInstance();
            entity.instantiate(enemyInstance, spawnPoint.transform.localPosition);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
