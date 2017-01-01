package com.gcupko.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.gcupko.game.Game;
import com.gcupko.game.sprites.Animation;
import com.gcupko.game.sprites.Flanker;
import com.gcupko.game.sprites.Fritz;
import com.gcupko.game.sprites.Rocket;

import java.util.Random;

/**
 * Created by Gligorije on 27.12.2016..
 */
public class PlayState extends State
{
    private Music music;
    private Sound flankerExplosion;
    private Texture background;
    private Texture pauseButton;
    private Texture fritzRocket;
    private Texture flankerRocket;
    private Texture explosion;
    private Vector2 pauseBtnPos;
    private Flanker flanker;
    private Array<Fritz> fritzs;
    private Array<Rocket> fritzRockets;
    private Array<Rocket> flankerRockets;
    private Animation explosionAnimation;
    private int score;
    private int shootCond = 0;
    private int difLimit = 100;
    private Random rand;
    private boolean clearToShoot;
    private Timer timer;

    public PlayState(GameStateManager gsm)
    {
        super(gsm);
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        camera.setToOrtho(false, width, height);
        flankerExplosion = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.mp3"));
        music = Gdx.audio.newMusic(Gdx.files.internal("music/i_fought_the_law.mp3"));
        music.setLooping(true);
        music.setVolume(0.1f);
        if (!Game.isMute()) music.play();

        background = new Texture("textures/clouds.png");
        fritzRocket = new Texture("animations/fritzrocket.png");
        flankerRocket = new Texture("animations/flankerrocket.png");
        explosion = new Texture("animations/explosion.png");
        pauseButton = new Texture("ui/pausebtn.png");
        pauseBtnPos = new Vector2(camera.position.x + (camera.viewportWidth / 2) - pauseButton.getWidth() - 10,
                camera.position.y + (camera.viewportHeight / 2) - pauseButton.getHeight() - 10);
        flanker = new Flanker();
        fritzs = new Array<Fritz>();
        fritzRockets = new Array<Rocket>();
        flankerRockets = new Array<Rocket>();

        fritzs.add(new Fritz((int)camera.position.y + (int)camera.viewportHeight));
        fritzs.add(new Fritz((int)camera.position.y + (int)camera.viewportHeight));

//        Gdx.input.setInputProcessor(new InputHandler());
        rand = new Random();
        clearToShoot = true;
        timer = new Timer();
        this.score = 0;
    }

    @Override
    protected void handleInput()
    {
        float accX = Gdx.input.getAccelerometerX();

        if (Gdx.input.justTouched()) {
            if (Gdx.input.getX() > pauseBtnPos.x
                    && height - Gdx.input.getY() > (pauseBtnPos.y - camera.position.y + camera.viewportHeight/2)) {
                gsm.push(new PauseState(gsm));
            }
            else {
                shoot();
            }
        }
        else if (accX > 1 || Gdx.input.isKeyPressed(Input.Keys.LEFT))
            moveFlankerLeft();
        else if(accX < - 1 || Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            moveFlankerRight();
        else
            flanker.straightenUp();
    }

    @Override
    public void update(float deltaTime)
    {
        handleInput();
        flanker.update(deltaTime);
        if (explosionAnimation != null)
            explosionAnimation.update(deltaTime);

        for (Fritz fritz : fritzs) {
            fritz.update(deltaTime);
            checkCollision(fritz.getBounds());
            scoreAndRepositionFritz(fritz);
            decideShootFritzRocket(fritz);
        }

        for (Rocket rocket : fritzRockets) {
            rocket.update(-deltaTime);
            checkCollision(rocket.getBounds());
            scoreRocket(rocket);
        }

        for (Rocket rocket : flankerRockets) {
            rocket.update(deltaTime);
            scoreHit(rocket);
        }

        pauseBtnPos.x =  camera.position.x + (camera.viewportWidth / 2) - pauseButton.getWidth() - 10;
        pauseBtnPos.y = camera.position.y + (camera.viewportHeight / 2) - pauseButton.getHeight() - 10;

        if(Game.isMute()) music.setVolume(0);
        else music.setVolume(0.1f);

        camera.position.y = flanker.getPosition().y + height/2 - 10;
        camera.update();
    }

    @Override
    public void render(SpriteBatch sb)
    {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        sb.draw(background, 0, camera.position.y - (camera.viewportHeight / 2), width, height);
        if (explosionAnimation == null
                || explosionAnimation != null && explosionAnimation.getFrameStage() < 0.75)
            sb.draw(flanker.getFlanker(), flanker.getPosition().x, flanker.getPosition().y);
        if (explosionAnimation != null)
            sb.draw(explosionAnimation.getFrame(),
                    flanker.getPosition().x + flanker.getFlanker().getRegionWidth()/2
                            - explosionAnimation.getFrame().getRegionWidth()/2,
                    flanker.getPosition().y);
        for (Rocket rocket : fritzRockets) {
            sb.draw(rocket.getRocket(), rocket.getPosition().x, rocket.getPosition().y);
        }
        for (Rocket rocket : flankerRockets) {
            sb.draw(rocket.getRocket(), rocket.getPosition().x, rocket.getPosition().y);
        }
        for (Fritz fritz : fritzs) {
            sb.draw(fritz.getFritz(), fritz.getPosition().x, fritz.getPosition().y);
            if (fritz.isDestroyed())
                sb.draw(fritz.getExplosionFrame(), fritz.getPosition().x, fritz.getPosition().y);
        }
        sb.draw(pauseButton, pauseBtnPos.x, pauseBtnPos.y);
        sb.end();
    }

    @Override
    public void dispose()
    {
        music.dispose();
        flankerExplosion.dispose();
        flanker.dispose();
        fritzRocket.dispose();
        flankerRocket.dispose();
        explosion.dispose();
        background.dispose();
        for (int i = 0; i < fritzs.size; i++) {
            Fritz fritz = fritzs.get(i);
            fritz.dispose();
        }
    }

    private void moveFlankerLeft()
    {
        flanker.leanLeft();
        flanker.move(-6);
    }

    private void moveFlankerRight()
    {
        flanker.leanRight();
        flanker.move(6);
    }

    private void checkCollision(Rectangle sprite)
    {
        if (flanker.collides(sprite) && explosionAnimation == null) {
            explodeFlanker();
            timer.scheduleTask(new Timer.Task() {
                                   @Override
                                   public void run() {
                                       gsm.set(new EndState(gsm, score));
                                   }
                               }, 0.3f
            );
            music.stop();
            if (!Game.isMute()) flankerExplosion.play();
        }
    }

    private void explodeFlanker() {
        explosionAnimation = new Animation(explosion, 11, 0.2f);
    }

    private void scoreAndRepositionFritz(Fritz fritz)
    {
        if (fritz.getPosition().y < camera.position.y - camera.viewportHeight/2 - fritz.getFritz().getHeight()) {
            score += 5;
            intensify();
            fritzs.removeValue(fritz, true);
            fritz.dispose();
            fritzs.add(new Fritz((int)camera.position.y + (int)camera.viewportHeight));
        }
    }

    private void decideShootFritzRocket(Fritz fritz)
    {
        if (rand.nextInt(1000) == shootCond || rand.nextInt(1000) == ++shootCond) {
            Rocket newRocket = new Rocket(fritzRocket,
                    fritz.getPosition().x + fritz.getFritz().getWidth()/2 - fritzRocket.getWidth()/2,
                    fritz.getPosition().y);
            fritzRockets.add(newRocket);
        }
        shootCond = rand.nextInt(1000);
    }

    private void scoreRocket(Rocket rocket)
    {
        if (rocket.getPosition().y < camera.position.y - camera.viewportHeight/2 - rocket.getRocket().getRegionHeight()) {
            fritzRockets.removeValue(rocket, true);
            score += 10;
            intensify();
        }
    }

    private void intensify()
    {
        if(score > difLimit) {
            fritzs.add(new Fritz((int) camera.position.y + (int) camera.viewportHeight));
            difLimit *= 4;
        }
    }

    private void shoot() {
        if (clearToShoot) {
            Rocket newRocket = new Rocket(flankerRocket,
                    flanker.getPosition().x + flanker.getFlanker().getRegionWidth() / 2 - flankerRocket.getWidth() / 2,
                    flanker.getPosition().y + flanker.getFlanker().getRegionHeight() - flankerRocket.getHeight());
            flankerRockets.add(newRocket);
            clearToShoot = false;
            timer.scheduleTask(new Timer.Task() {
                                   @Override
                                   public void run() {
                                       clearToShoot = true;
                                   }
                               }, 0.2f
            );
        }
    }

    private void scoreHit(final Rocket rocket)
    {
        for (final Fritz fritz : fritzs) {
            if (fritz.collides(rocket.getBounds()) && !fritz.isDestroyed()) {
                score += 15;
                fritz.setDestroyed(true);
                timer.scheduleTask(new Timer.Task() {
                                       @Override
                                       public void run() {
                                           fritzs.removeValue(fritz, true);
                                           fritz.dispose();
                                           fritzs.add(new Fritz((int)camera.position.y + (int)camera.viewportHeight));
                                       }
                                   }, 0.35f
                );
                flankerRockets.removeValue(rocket, true);
            }
        }
    }

//    private class InputHandler extends InputAdapter
//    {
//        @Override
//        public boolean touchDown(int screenX, int screenY, int pointer, int button)
//        {
//            handleFlanker(screenX);
//            return true;
//        }
//
//        @Override
//        public boolean touchDragged(int screenX, int screenY, int pointer)
//        {
//            handleFlanker(screenX);
//            return true;
//        }
//
//        @Override
//        public boolean touchUp(int screenX, int screenY, int pointer, int button)
//        {
//            flanker.straightenUp();
//            return true;
//        }
//
//        private void handleFlanker(int screenX)
//        {
//            if (screenX < flanker.getPosition().x)
//                flanker.leanLeft();
//            else if(Gdx.input.getX() > flanker.getPosition().x + flanker.getFlanker().getRegionWidth())
//                flanker.leanRight();
//            else
//                flanker.straightenUp();
//        }
//    }
}
