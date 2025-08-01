package com.devnmarki.game.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.devnmarki.engine.Debug;
import com.devnmarki.engine.ecs.Entity;
import com.devnmarki.engine.ecs.components.Component;
import com.devnmarki.game.characters.enemies.*;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnemySpawner extends Component {

    private static final float MIN_WAIT_TIME = 2f;
    private static final float MAX_WAIT_TIME = 3.5f;

    private List<Entity> spawnPoints = new ArrayList<>();
    private final Map<Class<? extends Enemy>, Integer> enemyTypes = new HashMap<>();

    private float waitTimeTimer = 0f;

    @Override
    public void start() {
        super.start();

        spawnPoints = entity.query(EnemySpawnPoint.class);

        enemyTypes.put(WatcherEnemy.class, 35);
        enemyTypes.put(MushroomEnemy.class, 60);
        enemyTypes.put(FrogEnemy.class, 80);

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
        Class<? extends Enemy> selectedEnemy = getRandomEnemyType();

        try {
            Entity enemyInstance = selectedEnemy.getConstructor().newInstance();
            Entity spawnPoint = spawnPoints.get(MathUtils.random(spawnPoints.size() - 1));
            entity.instantiate(enemyInstance, spawnPoint.transform.localPosition);
        } catch (Exception e) {
            throw new RuntimeException("Failed to spawn enemy: " + selectedEnemy.getSimpleName(), e);
        }

        waitTimeTimer = 0f;
    }

    private void spawnEnemy(Entity spawnPoint) {
        Class<? extends Enemy> selectedEnemy = getRandomEnemyType();

        try {
            Entity enemyInstance = selectedEnemy.getConstructor().newInstance();
            entity.instantiate(enemyInstance, spawnPoint.transform.localPosition);
        } catch (Exception e) {
            throw new RuntimeException("Failed to spawn enemy: " + selectedEnemy.getSimpleName(), e);
        }

        waitTimeTimer = 0f;
    }

    private Class<? extends Enemy> getRandomEnemyType() {
        float totalWeight = 0f;

        for (float weight : enemyTypes.values()) {
            totalWeight += weight;
        }

        float random = MathUtils.random(0f, totalWeight);
        float cumulativeWeight = 0f;

        for (Map.Entry<Class<? extends Enemy>, Integer> entry : enemyTypes.entrySet()) {
            cumulativeWeight += entry.getValue();
            if (random <= cumulativeWeight) {
                return entry.getKey();
            }
        }

        return FrogEnemy.class;
    }
}
