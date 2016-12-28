package com.gcupko.game.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Gligorije on 27.12.2016..
 */
public abstract class State
{
    protected OrthographicCamera camera;
    protected OrthographicCamera hudCam;
    protected GameStateManager gsm;
    protected int width;
    protected int height;

    protected State(GameStateManager gsm)
    {
        this.gsm = gsm;
        camera = new OrthographicCamera();
        hudCam = new OrthographicCamera();
    }

    protected abstract void handleInput();
    public abstract void update(float deltaTime);
    public abstract void render(SpriteBatch sb);
    public abstract void dispose();
}
