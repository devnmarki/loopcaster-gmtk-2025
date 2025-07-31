package com.devnmarki.engine.tilemap;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.devnmarki.engine.Debug;
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

        for (TiledMapTileSet tileset : map.getTileSets()) {
            for (TiledMapTile tile : tileset) {
                if (tile instanceof StaticTiledMapTile) {
                    TextureRegion region = tile.getTextureRegion();
                    Texture texture = region.getTexture();
                    texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
                }
            }
        }

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
