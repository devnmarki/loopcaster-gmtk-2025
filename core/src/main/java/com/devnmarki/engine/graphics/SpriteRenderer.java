package com.devnmarki.engine.graphics;

import com.devnmarki.engine.Engine;
import com.devnmarki.engine.ecs.components.Component;
import com.devnmarki.engine.ecs.components.Transform;

public class SpriteRenderer extends Component {

    private Sprite sprite;

    @Override
    public void update() {
        super.update();

        if (entity == null || sprite == null) return;

        float scale = Engine.gameScale;
        Transform transform = entity.getTransform();

        Engine.SPRITE_BATCH.draw(
                sprite.getTexture(),

                transform.worldPosition.x,
                transform.worldPosition.y,

                0,
                0,

                sprite.getTexture().getRegionWidth(),
                sprite.getTexture().getRegionHeight(),

                transform.worldScale.x == 1f ? scale : scale + transform.worldScale.x,
                transform.worldScale.y == 1f ? scale : scale + transform.worldScale.y,

                transform.worldRotation
        );
    }

    public SpriteRenderer setSprite(Sprite sprite) {
        this.sprite = sprite;

        return this;
    }

    public Sprite getSprite() {
        return sprite;
    }

}
