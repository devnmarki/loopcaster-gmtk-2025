package com.devnmarki.engine.tilemap;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.devnmarki.engine.Engine;
import com.devnmarki.engine.ecs.components.Component;
import com.devnmarki.engine.scene.SceneManager;

public class TilemapComponent extends Component {

    private String mapPath;

    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;

    public TilemapComponent(String mapPath) {
        this.mapPath = mapPath;
    }

    @Override
    public void awake() {
        super.awake();

        map = new TmxMapLoader().load(mapPath);
    }

    @Override
    public void start() {
        super.start();

        mapRenderer = new OrthogonalTiledMapRenderer(map, Engine.gameScale);
    }

    @Override
    public void update() {
        super.update();

        mapRenderer.setView(SceneManager.currentScene.getCamera().getOrthoCamera());

        mapRenderer.render();
    }

    public TiledMap getTiledMap() {
        return map;
    }

}
