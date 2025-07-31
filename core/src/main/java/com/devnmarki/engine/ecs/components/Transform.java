package com.devnmarki.engine.ecs.components;

import com.badlogic.gdx.math.Matrix3;
import com.devnmarki.engine.math.Vector2;

import java.util.ArrayList;
import java.util.List;

public class Transform extends Component {

    public Vector2 localPosition = new Vector2(0, 0);
    public float localRotation = 0f;
    public Vector2 localScale = new Vector2(1, 1);

}
