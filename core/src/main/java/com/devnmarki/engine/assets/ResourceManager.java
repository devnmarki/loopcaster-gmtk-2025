package com.devnmarki.engine.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;
import java.util.Map;

public class ResourceManager {

    public static TextureRegion loadTexture(String texturePath) {
        return new TextureRegion(new Texture(texturePath));
    }

    public static Sound loadSound(String soundPath) {
        return Gdx.audio.newSound(Gdx.files.internal(soundPath));
    }

}
