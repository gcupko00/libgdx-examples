package com.gcupko.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Gligorije on 27.12.2016..
 */
public class MenuState extends State
{
    private Texture background;
    private Texture play;

    public MenuState(GameStateManager gsm)
    {
        super(gsm);
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();

        background = new Texture(Gdx.files.internal("menubackground.jpg"));
        play = new Texture(Gdx.files.internal("playbtn.png"));

        hudCam.setToOrtho(false, width, height);
    }

    @Override
    protected void handleInput()
    {
        if (Gdx.input.justTouched()) {
            gsm.push(new PlayState(gsm));
        }
    }

    @Override
    public void update(float deltaTime)
    {
        hudCam.update();
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb)
    {
        sb.setProjectionMatrix(hudCam.combined);
        sb.begin();
        sb.draw(background, 0, 0);
        sb.draw(play, hudCam.position.x - play.getWidth()/2, hudCam.position.y);
        sb.end();
    }

    @Override
    public void dispose()
    {
        background.dispose();
        play.dispose();
    }
}
