package com.gcupko.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Gligorije on 27.12.2016..
 */
public class Flanker
{
    public static final int MOVEMENT = 100;
    private Vector2 position;

    private Rectangle bounds;
    private Texture textureLeft;
    private Texture textureRight;
    private Texture textureStraight;
    private Animation flankerAnimation;

    public Flanker()
    {
        textureLeft = new Texture(Gdx.files.internal("textures/flankerleft.png"));
        textureRight = new Texture(Gdx.files.internal("textures/flankerright.png"));
        textureStraight = new Texture(Gdx.files.internal("animations/flankeranimation.png"));
        straightenUp();
        position = new Vector2(Gdx.graphics.getWidth()/2 - getFlanker().getRegionWidth()/2, 10);
        bounds = new Rectangle(position.x, position.y, textureLeft.getWidth(), textureRight.getHeight());
    }

    public TextureRegion getFlanker()
    {
        return flankerAnimation.getFrame();
    }

    public void leanLeft()
    {
        flankerAnimation = new Animation(textureLeft, 1, 0);
    }

    public void leanRight()
    {
        flankerAnimation = new Animation(textureRight, 1, 0);
    }

    public void straightenUp()
    {
        flankerAnimation = new Animation(textureStraight, 3, 0.1f);
    }

    public Vector2 getPosition()
    {
        return position;
    }

    public void update(float dt)
    {
        flankerAnimation.update(dt);

        position.add(0, MOVEMENT*dt);
        if (position.x <= 0)
            position.x = 0;
        else if (position.x >= Gdx.graphics.getWidth() - flankerAnimation.getFrame().getRegionWidth())
            position.x = Gdx.graphics.getWidth() - flankerAnimation.getFrame().getRegionWidth();

        bounds.setPosition(position.x, position.y);
    }

    public void move(int deltaX)
    {
        position.add(deltaX, 0);
    }

    public void dispose()
    {
        textureLeft.dispose();
        textureRight.dispose();
        textureStraight.dispose();
    }

    public boolean collides(Rectangle otherSpriteBounds)
    {
        if (bounds.overlaps(otherSpriteBounds))
            return true;
        return false;
    }
}
