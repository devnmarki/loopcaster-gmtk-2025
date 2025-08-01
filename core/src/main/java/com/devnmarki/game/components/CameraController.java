package com.devnmarki.game.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapProperties;
import com.devnmarki.engine.Engine;
import com.devnmarki.engine.ecs.components.Component;
import com.devnmarki.engine.math.MathUtils;
import com.devnmarki.engine.scene.Camera;
import com.devnmarki.engine.tilemap.Tilemap;
import com.devnmarki.game.characters.Player;

public class CameraController extends Component {

    private static final float DAMPING = 10f;

    private Tilemap tilemap;
    private Camera camera;

    private int mapWidth;
    private int mapHeight;

    @Override
    public void start() {
        super.start();

        tilemap = entity.queryFirst(Tilemap.class);
        camera = (Camera) entity;

        MapProperties prop = tilemap.getTiledMap().getProperties();
        mapWidth = prop.get("width", Integer.class) * (int) Engine.gameScale * 16;
        mapHeight = prop.get("height", Integer.class) * (int) Engine.gameScale * 16;
    }

    @Override
    public void update() {
        super.update();

        followPlayer();
        snapCamera();
    }

    private void followPlayer() {
        float lerpSpeed = Gdx.graphics.getDeltaTime() * DAMPING;

        entity.transform.localPosition.x = MathUtils.lerp(entity.transform.localPosition.x, Player.getInstance().transform.localPosition.x + 8f, lerpSpeed);
        entity.transform.localPosition.y = MathUtils.lerp(entity.transform.localPosition.y, Player.getInstance().transform.localPosition.y + 8f, lerpSpeed);
    }

    private void snapCamera() {
        if (entity.transform.localPosition.x <= camera.getViewportWidth() / 2f) {
            entity.transform.localPosition.x = camera.getViewportWidth() / 2f;
        } else if (entity.transform.localPosition.x >= mapWidth - camera.getViewportWidth() / 2f) {
            entity.transform.localPosition.x = mapWidth - camera.getViewportWidth() / 2f;
        }

        if (entity.transform.localPosition.y <= camera.getViewportHeight() / 2f) {
            entity.transform.localPosition.y = camera.getViewportHeight() / 2f;
        } else if (entity.transform.localPosition.y >= mapHeight - camera.getViewportHeight() / 2f) {
            entity.transform.localPosition.y = mapHeight - camera.getViewportHeight() / 2f;
        }
    }

}
