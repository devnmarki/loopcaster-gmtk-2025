package com.devnmarki.engine.graphics.animation;

import com.devnmarki.engine.ecs.components.Component;
import com.devnmarki.engine.graphics.SpriteRenderer;

import java.util.HashMap;
import java.util.Map;

public class Animator extends Component {

    private Map<String, Animation> animations = new HashMap<String, Animation>();
    private Animation currentAnimation;

    @Override
    public void update() {
        super.update();

        if (currentAnimation != null) {
            currentAnimation.update();

            int frameIndex = currentAnimation.getCurrentFrame();
            currentAnimation.getSpritesheet().getSprite(frameIndex).setFlip(currentAnimation.isFlip());

            entity.getComponent(SpriteRenderer.class).setSprite(currentAnimation.getSpritesheet().getSprite(frameIndex));
        }
    }

    public Animator addAnimation(String animationName, Animation animation) {
        animations.put(animationName, animation);

        return this;
    }

    public Animator play(String animationName) {
        Animation newAnimation = animations.get(animationName);
        if (newAnimation != null && currentAnimation != newAnimation) {
            currentAnimation = newAnimation;
            currentAnimation.reset();
        }

        return this;
    }

    public boolean isCurrentAnimationFinished() {
        return currentAnimation != null && currentAnimation.hasFinished();
    }

    public Animation getCurrentAnimation() {
        return currentAnimation;
    }

}
