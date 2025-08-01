package com.devnmarki.engine.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.devnmarki.engine.ecs.Actor;
import com.devnmarki.engine.ecs.Entity;

public class CollisionContactListener implements ContactListener {

    private final ObjectMap<Entity, Array<Entity>> collisions = new ObjectMap<>();

    @Override
    public void beginContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        Entity entityA = (Entity) fa.getBody().getUserData();
        Entity entityB = (Entity) fb.getBody().getUserData();

        Vector2 posA = fa.getBody().getPosition();
        Vector2 posB = fb.getBody().getPosition();

        Vector2 normal = posB.cpy().sub(posA).nor();

        addCollision(entityA, entityB);
        addCollision(entityB, entityA);

        entityA.onCollisionEnter((Entity) fb.getUserData(), new com.devnmarki.engine.math.Vector2(normal.x, normal.y), contact);

        Vector2 flipped = normal.cpy().scl(-1);
        entityB.onCollisionEnter((Entity) fa.getUserData(), new com.devnmarki.engine.math.Vector2(flipped.x, flipped.y), contact);
    }

    @Override
    public void endContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        Entity entityA = (Entity) fa.getBody().getUserData();
        Entity entityB = (Entity) fb.getBody().getUserData();

        removeCollision(entityA, entityB);
        removeCollision(entityB, entityA);

        if (entityA != null) {
            entityA.onCollisionExit((Entity) fb.getUserData());
        }

        if (entityB != null) {
            entityB.onCollisionExit((Entity) fa.getUserData());
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        Entity entityA = (Entity) fa.getBody().getUserData();
        Entity entityB = (Entity) fb.getBody().getUserData();

        addCollision(entityA, entityB);
        addCollision(entityB, entityA);

        int layerA = entityA.getLayer();
        int layerB = entityB.getLayer();

        Vector2 posA = fa.getBody().getPosition();
        Vector2 posB = fb.getBody().getPosition();

        Vector2 normal = posB.cpy().sub(posA).nor();

        if (!LayerCollision.canCollide(layerA, layerB)){
            entityA.onPreCollision((Entity) fb.getUserData(), new com.devnmarki.engine.math.Vector2(normal.x, normal.y), contact);

            Vector2 flipped = normal.cpy().scl(-1);
            entityB.onPreCollision((Entity) fa.getUserData(), new com.devnmarki.engine.math.Vector2(flipped.x, flipped.y), contact);

            contact.setEnabled(false);
        }
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        Entity entityA = (Entity) fa.getBody().getUserData();
        Entity entityB = (Entity) fb.getBody().getUserData();

        addCollision(entityA, entityB);
        addCollision(entityB, entityA);

        Vector2 posA = fa.getBody().getPosition();
        Vector2 posB = fb.getBody().getPosition();

        Vector2 normal = posB.cpy().sub(posA).nor();

        entityA.onPostCollision((Entity) fb.getUserData(), new com.devnmarki.engine.math.Vector2(normal.x, normal.y), contact);

        Vector2 flipped = normal.cpy().scl(-1);
        entityB.onPostCollision((Entity) fa.getUserData(), new com.devnmarki.engine.math.Vector2(flipped.x, flipped.y), contact);
    }

    public void dispatchCollisionStayEvents() {
        for (ObjectMap.Entry<Entity, Array<Entity>> entry : collisions.entries()) {
            Entity a = entry.key;
            Array<Entity> collidedEntities = entry.value;

            for (Entity b : collidedEntities) {
                addCollision(a, b);
                addCollision(b, a);

                com.devnmarki.engine.math.Vector2 normal = b.getTransform().localPosition.cpy().sub(a.getTransform().localPosition).nor();
                a.onCollisionStay(b, normal);
            }
        }
    }

    private void addCollision(Entity a, Entity b) {
        collisions.put(a, new Array<Entity>());
        if (!collisions.get(a).contains(b, true)) {
            collisions.get(a).add(b);
        }
    }

    private void removeCollision(Entity a, Entity b) {
        Array<Entity> list = collisions.get(a);
        if (list != null)
            list.removeValue(b, true);
    }

    public Array<Entity> getCollidingEntities(Entity entity) {
        return collisions.get(entity, new Array<Entity>());
    }

}
