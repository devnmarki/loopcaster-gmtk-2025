package com.devnmarki.engine.assets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;
import java.util.Map;

public class ResourceManager {

    private static Map<String, TextureRegion> textures = new HashMap<String, TextureRegion>();

    public static TextureRegion loadTexture(String texturePath) {
        TextureRegion tex = new TextureRegion(new Texture(texturePath));
        textures.put(texturePath, tex);

        return tex;
    }

}
