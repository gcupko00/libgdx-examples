package com.gcupko.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

/**
 * Created by Gligorije on 27.12.2016..
 */
public class Fritz
{
    public static final int MOVEMENT = 100;

    private Texture fritz;
    private Vector2 position;
    private Rectangle bounds;

    private Random rand;
    private int speedUp;

    private boolean destroyed;
    private Animation explosionAnimation;

    public Fritz(int y)
    {
        fritz = new Texture(Gdx.files.internal("textures/fritz.png"));
        rand = new Random();
        position = new Vector2(rand.nextInt(Gdx.graphics.getWidth() - fritz.getWidth()), y + rand.nextInt(500));
        speedUp = rand.nextInt(4);
        bounds = new Rectangle(position.x, position.y, fritz.getWidth(), fritz.getHeight());
        destroyed = false;
    }

    public void update(float dt)
    {
        if (isDestroyed()) {
            explosionAnimation.update(dt);
            if (explosionAnimation.isOnLastFrame())
                explosionAnimation.revert();
        }
        else {
            position.add(0, -MOVEMENT*dt - speedUp);

            bounds.setPosition(position.x, position.y);
        }
    }

    public void dispose()
    {
        fritz.dispose();
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
        explosionAnimation = new Animation(new Texture("animations/explosion.png"), 11, 0.1f);
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public Texture getFritz()
    {
        return fritz;
    }

    public Vector2 getPosition()
    {
        return position;
    }

    public Rectangle getBounds()
    {
        return bounds;
    }

    public TextureRegion getExplosionFrame()
    {
        return explosionAnimation.getFrame();
    }

    public boolean collides(Rectangle otherSpriteBounds)
    {
        if (bounds.overlaps(otherSpriteBounds))
            return true;
        return false;
    }
}
