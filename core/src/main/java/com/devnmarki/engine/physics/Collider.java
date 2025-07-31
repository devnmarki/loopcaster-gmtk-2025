package com.devnmarki.engine.physics;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.devnmarki.engine.ecs.components.Component;
import com.devnmarki.engine.scene.SceneManager;

public abstract class Collider extends Component {
    public Body body;

    protected BodyDef.BodyType type = BodyDef.BodyType.StaticBody;

    protected Fixture fixture;

    @Override
    public void start() {
        super.start();
    }

    public abstract void createBody();
    public abstract FixtureDef getFixtureDef();

    public void destroyBody() {
        if (body != null) {
            SceneManager.currentScene.getPhysicsWorld().destroyBody(body);
        }
    }

    public Collider setType(BodyDef.BodyType type) {
        this.type = type;

        return this;
    }

    public Body getBody() {
        return body;
    }

    public Fixture getFixture() {
        return fixture;
    }
}
