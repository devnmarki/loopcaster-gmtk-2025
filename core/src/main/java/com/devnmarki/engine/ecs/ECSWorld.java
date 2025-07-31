package com.devnmarki.engine.ecs;

import com.devnmarki.engine.Debug;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ECSWorld {

    private List<Entity> entities = new ArrayList<>();
    private Map<Class<? extends Entity>, List<Entity>> entityTypeMap = new HashMap<>();

    public void addEntity(Entity entity) {
        entities.add(entity);

        Class<? extends Entity> type = entity.getClass();
        if (!entityTypeMap.containsKey(type)) {
            entityTypeMap.put(type, new ArrayList<>());
        }

        entityTypeMap.get(type).add(entity);

        entity.onAwake();
        entity.onStart();
    }

    public void removeEntity(Entity entity) {
        entity.onDestroy();
        int index = entities.indexOf(entity);
        if (index != -1) {
            int lastIndex = entities.size() - 1;
            Entity lasEntity = entities.get(lastIndex);
            entities.set(index, lasEntity);
            entities.remove(lastIndex);
        }
        entityTypeMap.get(entity.getClass()).remove(entity);
    }

    public void update() {
        List<Entity> entitiesCopy = new ArrayList<>(entities);
        for (Entity e : entitiesCopy) {
            e.onUpdate();
        }
    }

    public void debug() {
        List<Entity> entitiesCopy = new ArrayList<>(entities);
        for (Entity e : entitiesCopy) {
            e.onDebug();
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends Entity> List<T> query(Class<T> type) {
        List<Entity> entities = entityTypeMap.get(type);

        if (entities == null)
            return new ArrayList<>();

        return (List<T>) entities;
    }

    @SuppressWarnings("unchecked")
    public <T extends Entity> T queryFirst(Class<T> type) {
        Entity target = entityTypeMap.get(type).get(0);

        if (target == null) {
            Debug.error("There is not entity of type " + type.getName() + "!");
            return null;
        }

        return (T) target;
    }

    public List<Entity> getEntities() {
        return entities;
    }
}
