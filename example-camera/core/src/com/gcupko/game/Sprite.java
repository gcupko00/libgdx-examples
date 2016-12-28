package com.gcupko.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;

public class Sprite
{
    private Texture texture;
    private Vector3 position;

    public Sprite(Texture texture, int x, int y) {
        position = new Vector3(x, y, 0);
        this.texture = texture;
    }

    public void update(int x, int y) {
        position.add(x, y, 0);
    }

    public boolean isTouched() {
        if (Gdx.input.getX() > position.x
                && Gdx.input.getX() < position.x + texture.getWidth()
                && Gdx.graphics.getHeight() - Gdx.input.getY() > position.y
                && Gdx.graphics.getHeight() - Gdx.input.getY() < position.y + texture.getHeight())
            return true;

        return false;
    }

    public Texture getTexture()
    {
        return texture;
    }

    public Vector3 getPosition()
    {
        return position;
    }
}
