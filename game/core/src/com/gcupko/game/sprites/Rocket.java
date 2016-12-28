package com.gcupko.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Gligorije on 28.12.2016..
 */
public class Rocket
{
    public static final int MOVEMENT = 350;
    private Vector2 position;

    private Rectangle bounds;
    private Animation rocketAnimation;

    public Rocket(Texture t, float x, float y)
    {
        position = new Vector2(x, y);
        rocketAnimation = new Animation(t, 3, 0.1f);
        bounds = new Rectangle(position.x, position.y,
                rocketAnimation.getFrame().getRegionWidth(),
                rocketAnimation.getFrame().getRegionHeight());
    }

    public void update(float dt)
    {
        rocketAnimation.update(dt);
        position.add(0, MOVEMENT*dt);
        bounds.setPosition(position.x, position.y);
    }

    public TextureRegion getRocket() { return rocketAnimation.getFrame(); }

    public Vector2 getPosition()
    {
        return position;
    }

    public Rectangle getBounds() { return bounds; }
}
