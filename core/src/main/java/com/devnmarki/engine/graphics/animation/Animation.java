package com.devnmarki.engine.graphics.animation;

import com.badlogic.gdx.Gdx;
import com.devnmarki.engine.graphics.Spritesheet;

public class Animation {

    private final Spritesheet spritesheet;
    private final int[] frames;
    private final float frameDuration;
    private final boolean loop;
    private final boolean flip;

    private int currentFrame = 0;
    private float elapsedTime = 0f;

    public Animation(Spritesheet spritesheet, int[] frames, float frameDuration, boolean loop, boolean flip) {
        this.spritesheet = spritesheet;
        this.frames = frames;
        this.frameDuration = frameDuration;
        this.loop = loop;
        this.flip = flip;
    }

    public void update() {
        elapsedTime += Gdx.graphics.getDeltaTime();
        if (elapsedTime >= frameDuration) {
            elapsedTime = 0f;
            currentFrame++;
            if (currentFrame >= frames.length) {
                currentFrame = loop ? 0 : frames.length - 1;
            }
        }
    }

    public void reset() {
        currentFrame = 0;
        elapsedTime = 0f;
    }

    public boolean hasFinished() {
        return !loop & currentFrame == frames.length - 1 & elapsedTime >= frameDuration;
    }

    public int getCurrentFrame() {
        return frames[currentFrame];
    }

    public Spritesheet getSpritesheet() {
        return spritesheet;
    }

    public boolean isFlip() {
        return flip;
    }

}
