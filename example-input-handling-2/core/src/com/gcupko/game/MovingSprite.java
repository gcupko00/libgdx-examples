package com.gcupko.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;

public class MovingSprite
{
    private Texture figure;
    private Vector3 position;

    public MovingSprite(int x, int y) {
        position = new Vector3(x, y, 0);
        figure = new Texture("figure.png");
    }

    public void update(int x, int y) {
        position.add(x, y, 0);
    }

    public boolean isTouched() {
        if (Gdx.input.getX() > position.x
                && Gdx.input.getX() < position.x + figure.getWidth()
                && Gdx.graphics.getHeight() - Gdx.input.getY() > position.y
                && Gdx.graphics.getHeight() - Gdx.input.getY() < position.y + figure.getHeight())
            return true;

        return false;
    }

    public Texture getFigure()
    {
        return figure;
    }

    public Vector3 getPosition()
    {
        return position;
    }
}
