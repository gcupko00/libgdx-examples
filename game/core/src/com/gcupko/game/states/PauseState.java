package com.gcupko.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Gligorije on 27.12.2016..
 */
public class PauseState extends State
{
    private Texture pauseBackground;

    protected PauseState(GameStateManager gsm)
    {
        super(gsm);
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();

        pauseBackground = new Texture(Gdx.files.internal("menubackground.jpg"));

        hudCam.setToOrtho(false, width, height);
    }

    @Override
    protected void handleInput()
    {
        if (Gdx.input.justTouched()) {
            gsm.pop();
        }
    }

    @Override
    public void update(float deltaTime)
    {
        handleInput();
        hudCam.update();
    }

    @Override
    public void render(SpriteBatch sb)
    {
        sb.setProjectionMatrix(hudCam.combined);
        sb.begin();
        sb.draw(pauseBackground, 0, 0);
        sb.end();
    }

    @Override
    public void dispose()
    {
        pauseBackground.dispose();
    }
}
