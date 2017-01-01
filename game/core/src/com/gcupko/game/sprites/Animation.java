package com.gcupko.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Gligorije on 27.12.2016..
 */
public class Animation
{
    private Array<TextureRegion> frames;
    private float maxFrameTime;
    private float currentFrameTime;
    private int frameCount;
    private int frame;

    public Animation(Texture texture, int frameCount, float cycleTime)
    {
        frames = new Array<TextureRegion>();

        TextureRegion region = new TextureRegion(texture);
        int frameWidth = region.getRegionWidth() / frameCount;
        for (int i = 0; i < frameCount; i++) {
            frames.add(new TextureRegion(region, i * frameWidth, 0, frameWidth, region.getRegionHeight()));
        }

        this.frameCount = frameCount;
        maxFrameTime = cycleTime / frameCount;

        frame = 0;
    }

    public void update(float dt)
    {
        currentFrameTime += dt;

        if (currentFrameTime > maxFrameTime) {
            frame++;
            currentFrameTime = 0;
        }

        if (frame >= frameCount) {
            frame = 0;
        }
    }

    public void revert()
    {
        TextureRegion tmp;
        for (int i = 0; i < frameCount/2; i++) {
            tmp = frames.get(frameCount - i - 1);
            frames.set(frameCount - i - 1, frames.get(i));
            frames.set(i, tmp);
        }
    }

    public TextureRegion getFrame()
    {
        return frames.get(frame);
    }

    public float getFrameStage() { return (float)frame/frameCount; }

    public boolean isOnLastFrame()
    {
        if (frame >= frameCount - 1) {
            frame = 0;
            return true;
        }
        return false;
    }
}
