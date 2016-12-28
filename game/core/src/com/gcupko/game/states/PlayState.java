package com.gcupko.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.gcupko.game.sprites.Flanker;
import com.gcupko.game.sprites.Fritz;
import com.gcupko.game.sprites.Rocket;

import java.util.Random;

/**
 * Created by Gligorije on 27.12.2016..
 */
public class PlayState extends State
{
    private Texture background;
    private Texture pauseButton;
    private Texture fritzRocket;
    private Vector2 pauseBtnPos;
    private Flanker flanker;
    private Array<Fritz> fritzs;
    private Array<Rocket> fritzRockets;
    private Array<Rocket> flankerRockets;
    private int score;
    private Random rand;

    public PlayState(GameStateManager gsm)
    {
        super(gsm);
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        camera.setToOrtho(false, width, height);

        background = new Texture("clouds.png");
        fritzRocket = new Texture("fritzrocket.png");
        pauseButton = new Texture("pausebtn.png");
        pauseBtnPos = new Vector2(camera.position.x + (camera.viewportWidth / 2) - pauseButton.getWidth() - 10,
                camera.position.y + (camera.viewportHeight / 2) - pauseButton.getHeight() - 10);
        flanker = new Flanker();
        fritzs = new Array<Fritz>();
        fritzRockets = new Array<Rocket>();
        flankerRockets = new Array<Rocket>();

        fritzs.add(new Fritz((int)camera.position.y + (int)camera.viewportHeight));
        fritzs.add(new Fritz((int)camera.position.y + (int)camera.viewportHeight));

        Gdx.input.setInputProcessor(new InputHandler());
        rand = new Random();
        this.score = 0;
    }

    @Override
    protected void handleInput()
    {
        if (Gdx.input.justTouched()) {
            if (Gdx.input.getX() > pauseBtnPos.x
                    && height - Gdx.input.getY() > (pauseBtnPos.y - camera.position.y + camera.viewportHeight/2)) {
                gsm.push(new PauseState(gsm));
            }
        }
        else if (Gdx.input.isTouched()) {
            if (Gdx.input.getX() < flanker.getPosition().x)
                flanker.move(-6);
            else if(Gdx.input.getX() > flanker.getPosition().x + flanker.getFlanker().getRegionWidth())
                flanker.move(6);
        }
    }

    @Override
    public void update(float deltaTime)
    {
        handleInput();
        flanker.update(deltaTime);

        for (Fritz fritz : fritzs) {
            fritz.update(deltaTime);

            if (flanker.collides(fritz.getBounds())) {
                gsm.set(new EndState(gsm, score));
            }

            if (fritz.getPosition().y < camera.position.y - camera.viewportHeight/2 - fritz.getFritz().getHeight()) {
                fritzs.removeValue(fritz, true);
                fritz.dispose();
                fritzs.add(new Fritz((int)camera.position.y + (int)camera.viewportHeight));
                score += 5;
            }

            if (rand.nextInt(1000) <= 2) {
                Rocket newRocket = new Rocket(fritzRocket,
                        fritz.getPosition().x + fritz.getFritz().getWidth()/2 - fritzRocket.getWidth()/2,
                        fritz.getPosition().y);

                fritzRockets.add(newRocket);
            }
        }

        for (Rocket rocket : fritzRockets) {
            rocket.update(-deltaTime);

            if (flanker.collides(rocket.getBounds())) {
                gsm.set(new EndState(gsm, score));
            }

            if (rocket.getPosition().y < camera.position.y - camera.viewportHeight/2 - rocket.getRocket().getRegionHeight()) {
                fritzRockets.removeValue(rocket, true);
                score += 10;
            }
        }

        pauseBtnPos.x =  camera.position.x + (camera.viewportWidth / 2) - pauseButton.getWidth() - 10;
        pauseBtnPos.y = camera.position.y + (camera.viewportHeight / 2) - pauseButton.getHeight() - 10;

        camera.position.y = flanker.getPosition().y + height/2 - 10;
        camera.update();
    }

    @Override
    public void render(SpriteBatch sb)
    {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        sb.draw(background, 0, camera.position.y - (camera.viewportHeight / 2));
        sb.draw(background, 0, camera.position.y - (camera.viewportHeight / 2) + background.getHeight());
        sb.draw(flanker.getFlanker(), flanker.getPosition().x, flanker.getPosition().y);
        for (Rocket rocket : fritzRockets) {
            sb.draw(rocket.getRocket(), rocket.getPosition().x, rocket.getPosition().y);
        }
        for (Fritz fritz : fritzs) {
            sb.draw(fritz.getFritz(), fritz.getPosition().x, fritz.getPosition().y);
        }
        sb.draw(pauseButton, pauseBtnPos.x, pauseBtnPos.y);
        sb.end();
    }

    @Override
    public void dispose()
    {
        flanker.dispose();
        fritzRocket.dispose();
        background.dispose();
        for (int i = 0; i < fritzs.size; i++) {
            Fritz fritz = fritzs.get(i);
            fritz.dispose();
        }
    }

    private class InputHandler extends InputAdapter
    {
        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button)
        {
            handleFlanker(screenX);
            return true;
        }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer)
        {
            handleFlanker(screenX);
            return true;
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button)
        {
            flanker.straightenUp();
            return true;
        }

        private void handleFlanker(int screenX)
        {
            if (screenX < flanker.getPosition().x)
                flanker.leanLeft();
            else if(Gdx.input.getX() > flanker.getPosition().x + flanker.getFlanker().getRegionWidth())
                flanker.leanRight();
            else
                flanker.straightenUp();
        }
    }
}
