package com.gcupko.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class MovingSprite
{
    private Texture figure;
    private Vector3 position;
    private Rectangle bounds;
    private boolean dragging;

    public MovingSprite(int x, int y) {
        position = new Vector3(x, y, 0);
        figure = new Texture("figure.png");
        bounds = new Rectangle(x, y, figure.getWidth(), figure.getHeight());
    }

    public void update(int x, int y) {
        position.add(x, y, 0);
        bounds.setPosition(position.x, position.y);
    }

    public boolean isTouched() {
        if (Gdx.input.getX() > position.x
                && Gdx.input.getX() < position.x + figure.getWidth()
                && Gdx.graphics.getHeight() - Gdx.input.getY() > position.y
                && Gdx.graphics.getHeight() - Gdx.input.getY() < position.y + figure.getHeight())
            return true;

        return false;
    }

    public boolean collides(Rectangle otherSprite) {
        System.out.println(bounds + " : " + otherSprite);
        return otherSprite.overlaps(this.bounds);
    }

    public Rectangle getBounds() { return bounds; }

    public Texture getFigure()
    {
        return figure;
    }

    public Vector3 getPosition()
    {
        return position;
    }

    public void setDragging(boolean dragging) { this.dragging = dragging; }

    public boolean isDragging() { return dragging; }

    public void dispose() { figure.dispose(); }
}
