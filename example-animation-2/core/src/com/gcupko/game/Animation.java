package com.gcupko.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Gligorije on 25.12.2016..
 */
public class Animation
{
    private Array<TextureRegion> frames;
    private float maxFrameTime;
    private float currentFrameTime;
    private int frameCount;
    private int frame;

    public Animation(Texture texture, int frameCount, float cycleTime) {
        this.frames = new Array<TextureRegion>();

        TextureRegion region = new TextureRegion(texture);
        int frameWidth = region.getRegionWidth() / frameCount;
        for (int i = 0; i < frameCount; i++) {
            frames.add(new TextureRegion(region, i * frameWidth, 0, frameWidth, region.getRegionHeight()));
        }

        this.frameCount = frameCount;
        this.maxFrameTime = cycleTime / frameCount;

        this.frame = 0;
    }

    public void update(float dt) {
        currentFrameTime += dt;

        if (currentFrameTime > maxFrameTime) {
            frame++;
            currentFrameTime = 0;
        }

        if (frame >= frameCount) {
            frame = 0;
        }
    }

    public TextureRegion getFrame()
    {
        return frames.get(frame);
    }
}
