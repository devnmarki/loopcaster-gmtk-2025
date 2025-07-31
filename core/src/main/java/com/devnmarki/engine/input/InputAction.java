package com.devnmarki.engine.input;

import com.badlogic.gdx.Input;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class InputAction {

    private final String name;
    private final List<Integer> keys = new ArrayList<>();
    private final List<Integer> mouseButtons = new ArrayList<>();

    public InputAction(String name, Collection<Integer> keys, Collection<Integer> mouseButtons) {
        this.name = name;
        if (keys != null) this.keys.addAll(keys);
        if (mouseButtons != null) this.mouseButtons.addAll(mouseButtons);
    }

    public String getName() {
        return name;
    }

    public List<Integer> getKeys() {
        return keys;
    }

    public List<Integer> getMouseButtons() {
        return mouseButtons;
    }

}
