package com.devnmarki.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.devnmarki.engine.assets.ResourceManager;
import com.devnmarki.engine.tilemap.Tilemap;

public class Assets {

    public static class Tilemaps {
        public static final Tilemap TEST_LEVEL = new Tilemap("levels/test_level.tmx");
        public static final Tilemap MAIN_MENU_BG = new Tilemap("levels/main_menu_bg.tmx");
        public static final Tilemap WORLD = new Tilemap("levels/world.tmx");
    }

    public static class Sounds {
        public static final Sound DEATH = ResourceManager.loadSound("sfx/death.wav");
        public static final Sound JUMP = ResourceManager.loadSound("sfx/jump.wav");
        public static final Sound SHOOT = ResourceManager.loadSound("sfx/shoot.wav");
        public static final Sound HURT = ResourceManager.loadSound("sfx/hurt.wav");
        public static final Sound PLAYER_HURT = ResourceManager.loadSound("sfx/player_hurt.wav");
    }

}
