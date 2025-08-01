package com.devnmarki;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.devnmarki.engine.Engine;
import com.devnmarki.engine.physics.LayerCollision;
import com.devnmarki.engine.scene.SceneManager;
import com.devnmarki.engine.tilemap.TilemapEntityLoader;
import com.devnmarki.game.Globals;
import com.devnmarki.game.characters.Player;
import com.devnmarki.game.characters.enemies.EnemySpawnPoint;
import com.devnmarki.game.characters.enemies.FrogEnemy;
import com.devnmarki.game.characters.enemies.MushroomEnemy;
import com.devnmarki.game.characters.enemies.WatcherEnemy;
import com.devnmarki.game.scenes.DeathScreenScene;
import com.devnmarki.game.scenes.MainMenuScene;
import com.devnmarki.game.scenes.SampleScene;

import static com.badlogic.gdx.Input.*;

public class Main extends ApplicationAdapter {

    @Override
    public void create() {
        super.create();

        Engine.gameScale = 4f;

        LayerCollision.setCollision(Globals.EntityLayers.PLAYER, Globals.EntityLayers.ENEMY, false);
        LayerCollision.setCollision(Globals.EntityLayers.ENEMY, Globals.EntityLayers.ENEMY, false);

        TilemapEntityLoader.register("Player", Player.class);
        TilemapEntityLoader.register("Watcher Enemy", WatcherEnemy.class);
        TilemapEntityLoader.register("Mushroom Enemy", MushroomEnemy.class);
        TilemapEntityLoader.register("Frog Enemy", FrogEnemy.class);
        TilemapEntityLoader.register("Enemy Spawn Point", EnemySpawnPoint.class);

        SceneManager.addScene("sample", new SampleScene());
        SceneManager.addScene("main_menu", new MainMenuScene());
        SceneManager.addScene("death_screen", new DeathScreenScene());
        SceneManager.loadScene("main_menu");
    }

    @Override
    public void render() {
        super.render();

        Engine.getInstance().clear(0, 9, 36);
        Engine.getInstance().update();

        if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
            Gdx.app.exit();
        }

        if (Gdx.input.isKeyJustPressed(Keys.TAB)) {
            Engine.debugMode = !Engine.debugMode;
        }

        if (Gdx.input.isKeyJustPressed(Keys.Q)) {
            SceneManager.loadScene("main_menu");
        } else if (Gdx.input.isKeyJustPressed(Keys.E)) {
            SceneManager.loadScene("sample");
        }
    }

    @Override
    public void dispose() {
        super.dispose();

        Engine.getInstance().dispose();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);

        Engine.getInstance().resize(width, height);
    }
}
