package com.devnmarki.engine.ecs.components;

import com.badlogic.gdx.math.Matrix3;
import com.devnmarki.engine.math.Vector2;

import java.util.ArrayList;
import java.util.List;

public class Transform extends Component {

    public Vector2 localPosition = Vector2.zero();
    public float localRotation = 0f;
    public Vector2 localScale = Vector2.one();

    private Transform parent = null;
    private final List<Transform> children = new ArrayList<>();

    public Vector2 worldPosition = Vector2.zero();
    public float worldRotation = 0f;
    public Vector2 worldScale = Vector2.one();

    private final Matrix3 localMatrix = new Matrix3();
    private final Matrix3 worldMatrix = new Matrix3();

    public Transform() {

    }

    public void setParent(Transform newParent) {
        if (this.parent != null) {
            this.parent.children.remove(this);
        }

        this.parent = newParent;

        if (newParent != null) {
            newParent.children.add(this);
        }
    }

    @Override
    public void update() {
        super.update();

        localMatrix.idt()
                .translate(localPosition.x, localPosition.y)
                .rotate(localRotation)
                .scale(localScale.x, localScale.y);

        if (parent != null) {
            worldMatrix.set(parent.worldMatrix).mul(localMatrix);

            worldPosition.set(new Vector2(worldMatrix.getValues()[Matrix3.M02], worldMatrix.getValues()[Matrix3.M12]));
            worldRotation = parent.worldRotation + localRotation;
            worldScale.set(new Vector2(parent.worldScale.x * localScale.x, parent.worldScale.y * localScale.y));
        } else {
            worldMatrix.set(localMatrix);
            worldPosition.set(localPosition);
            worldRotation = localRotation;
            worldScale.set(localScale);
        }
    }
}