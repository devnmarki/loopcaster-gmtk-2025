package com.devnmarki.engine.physics;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.devnmarki.engine.Engine;
import com.devnmarki.engine.ecs.Actor;
import com.devnmarki.engine.math.Vector2;
import com.devnmarki.engine.scene.SceneManager;

public class BoxCollider extends Collider {
    private Vector2 size = new Vector2(1f, 1f);
    private Vector2 offset = new Vector2(0f, 0f);
    private boolean solid = true;

    private FixtureDef fixtureDef;

    @Override
    public void start() {
        super.start();

        createBody();
    }

    @Override
    public void update() {
        super.update();

        if (body == null) return;

        entity.transform.localPosition = new Vector2(
                body.getPosition().x * Engine.PPM - (offset.x * Engine.gameScale),
                body.getPosition().y * Engine.PPM - (offset.y * Engine.gameScale)
        );
    }

    @Override
    public void createBody() {
        if (body != null) return;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = type;
        bodyDef.position.set(
                (entity.transform.localPosition.x + offset.x * Engine.gameScale) / Engine.PPM,
                (entity.transform.localPosition.y + offset.y * Engine.gameScale) / Engine.PPM
        );
        bodyDef.fixedRotation = true;
        bodyDef.bullet = (type == BodyDef.BodyType.DynamicBody);

        body = SceneManager.currentScene.getPhysicsWorld().createBody(bodyDef);

        float halfWidth = size.x / 2f / Engine.PPM;
        float halfHeight = size.y / 2f / Engine.PPM;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(halfWidth * Engine.gameScale, halfHeight * Engine.gameScale, new com.badlogic.gdx.math.Vector2(size.x / 2f * Engine.gameScale / Engine.PPM, size.y / 2f * Engine.gameScale / Engine.PPM), 0f);
        fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 0f;
        fixtureDef.restitution = 0f;

        fixture = body.createFixture(fixtureDef);
        fixture.setSensor(!solid);
        fixture.setUserData(entity);
        body.setUserData(entity);

        shape.dispose();
    }

    public BoxCollider setPosition(Vector2 position) {
        if (body != null) {
            body.setTransform(
                    (position.x + offset.x * Engine.gameScale) / Engine.PPM,
                    (position.y + offset.y * Engine.gameScale) / Engine.PPM,
                    body.getAngle()
            );
        }

        return this;
    }

    public BoxCollider setSize(Vector2 size) {
        this.size = size;

        if (body != null) {
            SceneManager.currentScene.getPhysicsWorld().destroyBody(body);
            body = null;
            createBody();
        }

        return this;
    }

    public BoxCollider setOffset(Vector2 offset) {
        this.offset = offset;

        return this;
    }

    public BoxCollider setSolid(boolean solid) {
        this.solid = solid;

        return this;
    }

    @Override
    public FixtureDef getFixtureDef() {
        return fixtureDef;
    }

    public Vector2 getSize() {
        return size;
    }

    public Vector2 getOffset() {
        return offset;
    }

    public boolean isSolid() {
        return solid;
    }
}
