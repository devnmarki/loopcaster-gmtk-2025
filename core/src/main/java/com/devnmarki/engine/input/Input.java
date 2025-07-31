package com.devnmarki.engine.input;

import com.badlogic.gdx.Gdx;
import com.devnmarki.engine.math.Vector2;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Input {
    private static final Set<Integer> EMPTY_KEYS = Set.of();
    private static final Set<Integer> EMPTY_MOUSE = Set.of();

    private static Map<String, InputAction> actions = new HashMap<>();

    public static void addAction(String name, Collection<Integer> keys, Collection<Integer> mouseButtons) {
        actions.put(name, new InputAction(name, keys != null ? keys : EMPTY_KEYS, mouseButtons != null ? mouseButtons : EMPTY_MOUSE));
    }

    public static boolean isPressed(String action) {
        InputAction a = actions.get(action);
        if (a == null)
            return false;

        for (int key : a.getKeys()) {
            if (Gdx.input.isKeyPressed(key))
                return true;
        }

        for (int btn : a.getMouseButtons()) {
            if (Gdx.input.isButtonPressed(btn))
                return true;
        }

        return false;
    }

    public static boolean isJustPressed(String action) {
        InputAction a = actions.get(action);
        if (a == null)
            return false;

        for (int key : a.getKeys()) {
            if (Gdx.input.isKeyJustPressed(key))
                return true;
        }

        for (int btn : a.getMouseButtons()) {
            if (Gdx.input.isButtonJustPressed(btn))
                return true;
        }

        return false;
    }

    public static float getAxis(String negative, String positive) {
        float axis = 0f;
        if (isPressed(negative)) axis = -1f;
        if (isPressed(positive)) axis = 1f;
        return axis;
    }

    public static Vector2 getVector(String negativeX, String positiveX, String negativeY, String positiveY) {
        Vector2 axis = Vector2.zero();
        if (isPressed(negativeX)) axis.x = -1f;
        if (isPressed(positiveX)) axis.x = 1f;
        if (isPressed(negativeY)) axis.y = -1f;
        if (isPressed(positiveY)) axis.y = 1f;
        return axis;
    }

    public static Vector2 getVectorNormalized(String negativeX, String positiveX, String negativeY, String positiveY) {
        return getVector(negativeX, positiveX, negativeY, positiveY).normalized();
    }

}
