package com.devnmarki.engine.physics;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.devnmarki.engine.Debug;
import com.devnmarki.engine.Engine;
import com.devnmarki.engine.ecs.components.Component;
import com.devnmarki.engine.math.Vector2;

public class Rigidbody extends Component {

    private Vector2 velocity = new Vector2();
    private boolean useGravity = true;
    private boolean isKinematic = false;
    private float gravityScale = 1f;
    private float mass = 1f;

    private BoxCollider collider;

    @Override
    public void start() {
        super.start();

        collider = entity.getComponent(BoxCollider.class);
        if (collider == null) {
            throw new RuntimeException("Rigidbody requires a Collider.");
        }

        if (collider.body == null) {
            collider.createBody();
        }

        if (isKinematic) {
            collider.body.setType(BodyDef.BodyType.KinematicBody);
        } else {
            collider.body.setType(useGravity ? BodyDef.BodyType.DynamicBody : BodyDef.BodyType.StaticBody);
        }

        collider.body.setGravityScale(useGravity ? gravityScale : 0f);
        collider.getFixtureDef().density = mass;
    }

    @Override
    public void update() {
        super.update();

        if (collider.body == null) return;

        if (isKinematic) {
            collider.body.setLinearVelocity(velocity.x / Engine.PPM, velocity.y / Engine.PPM);
        } else {
            velocity.x = collider.body.getLinearVelocity().x * Engine.PPM;
            velocity.y = collider.body.getLinearVelocity().y * Engine.PPM;
        }
    }

    public void applyForce(Vector2 force) {
        if (collider.body != null) {
            collider.body.applyForceToCenter(force.x, force.y, true);
        }
    }

    public Rigidbody setVelocity(Vector2 vel) {
        this.velocity = vel;
        if (collider.getBody() != null) {
            collider.getBody().setLinearVelocity(vel.x, vel.y);
        }

        return this;
    }

    public Rigidbody setUseGravity(boolean useGravity) {
        this.useGravity = useGravity;

        return this;
    }

    public Rigidbody setKinematic(boolean kinematic) {
        isKinematic = kinematic;

        return this;
    }

    public Rigidbody setGravityScale(float gravityScale) {
        this.gravityScale = gravityScale;

        return this;
    }

    public Rigidbody setMass(float mass) {
        this.mass = mass;

        return this;
    }

    public Vector2 getVelocity() {
        return new Vector2(collider.getBody().getLinearVelocity().x, collider.getBody().getLinearVelocity().y);
    }

    public boolean useGravity() {
        return useGravity;
    }

    public boolean isKinematic() {
        return isKinematic;
    }

    public float getGravityScale() {
        return gravityScale;
    }
}
