package com.gcupko.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Gligorije on 27.12.2016..
 */
public class EndState extends State
{
    private Texture background;
    private BitmapFont scoreFont;
    private int score;

    protected EndState(GameStateManager gsm, int score)
    {
        super(gsm);
        this.score = score;
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();

        background = new Texture("menubackground.jpg");
        scoreFont = new BitmapFont();
        scoreFont.setColor(Color.BLACK);

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
        hudCam.update();
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb)
    {
        sb.setProjectionMatrix(hudCam.combined);
        sb.begin();
        sb.draw(background,
                hudCam.position.x - hudCam.viewportWidth/2,
                hudCam.position.y - hudCam.viewportHeight/2);
        scoreFont.draw(sb, "YOUR SCORE: " + score,
                hudCam.position.x - hudCam.viewportWidth/2 + 20,
                hudCam.position.y + hudCam.viewportHeight/2 - 50);
        sb.end();
    }

    @Override
    public void dispose()
    {
        scoreFont.dispose();
    }
}
