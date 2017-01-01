package com.gcupko.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.gcupko.game.Game;

/**
 * Created by Gligorije on 27.12.2016..
 */
public class MenuState extends State
{
    private Sound introEffect;

    private Texture background;
    private Texture playBtn;
    private Texture scoresBtn;
    private Texture exitBtn;
    private Texture muteBtn;

    private Vector2 playBtnPos;
    private Vector2 scoresBtnPos;
    private Vector2 exitBtnPos;
    private Vector2 muteBtnPos;

    public MenuState(GameStateManager gsm)
    {
        super(gsm);
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();

        hudCam.setToOrtho(false, width, height);
        introEffect = Gdx.audio.newSound(Gdx.files.internal("sounds/intro.mp3"));
        if (!Game.isMute()) introEffect.play();

        background = new Texture(Gdx.files.internal("ui/menubackground.jpg"));
        playBtn = new Texture(Gdx.files.internal("ui/playbtn.png"));
        scoresBtn = new Texture(Gdx.files.internal("ui/scoresbtn.png"));
        exitBtn = new Texture(Gdx.files.internal("ui/exitbtn.png"));
        if (!Game.isMute()) muteBtn = new Texture(Gdx.files.internal("ui/soundbtn.png"));
        else muteBtn = new Texture(Gdx.files.internal("ui/mutebtn.png"));

        playBtnPos = new Vector2(hudCam.position.x - playBtn.getWidth()/2,  hudCam.position.y + 140);
        scoresBtnPos = new Vector2(hudCam.position.x - playBtn.getWidth()/2, hudCam.position.y + 70);
        exitBtnPos = new Vector2(hudCam.position.x - playBtn.getWidth()/2, hudCam.position.y);
        muteBtnPos = new Vector2(hudCam.position.x + hudCam.viewportWidth/2 - muteBtn.getWidth() - 10,
                hudCam.position.y + hudCam.viewportHeight/2 - muteBtn.getHeight() - 10);
    }

    @Override
    protected void handleInput()
    {
        if (Gdx.input.justTouched()) {
            if (buttonTouched(playBtn, playBtnPos)) {
                introEffect.stop();
                gsm.push(new PlayState(gsm));
            }
            else if (buttonTouched(scoresBtn, scoresBtnPos))
                gsm.push(new ScoresState(gsm));
            else if (buttonTouched(exitBtn, exitBtnPos))
                Gdx.app.exit();
            else if (buttonTouched(muteBtn, muteBtnPos)) {
                if (!Game.isMute()) Game.setMute(true);
                else Game.setMute(false);
            }
        }
    }

    @Override
    public void update(float deltaTime)
    {
        if (!Game.isMute()) {
            muteBtn = new Texture(Gdx.files.internal("ui/soundbtn.png"));
            introEffect.setVolume(0, 1);
        }
        else {
            muteBtn = new Texture(Gdx.files.internal("ui/mutebtn.png"));
            introEffect.setVolume(0, 0);
        }
        hudCam.update();
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb)
    {
        sb.setProjectionMatrix(hudCam.combined);
        sb.begin();
        sb.draw(background, 0, 0, width, height);
        sb.draw(playBtn, playBtnPos.x, playBtnPos.y);
        sb.draw(scoresBtn, scoresBtnPos.x, scoresBtnPos.y);
        sb.draw(exitBtn, exitBtnPos.x, exitBtnPos.y);
        sb.draw(muteBtn, muteBtnPos.x, muteBtnPos.y);
        sb.end();
    }

    @Override
    public void dispose()
    {
        introEffect.dispose();
        background.dispose();
        playBtn.dispose();
        scoresBtn.dispose();
        exitBtn.dispose();
    }

    public boolean buttonTouched(Texture button, Vector2 buttonPos)
    {
        if (Gdx.input.getX() > buttonPos.x
                && Gdx.input.getX() < buttonPos.x + button.getWidth()
                && height - Gdx.input.getY() > buttonPos.y
                && height -  Gdx.input.getY() < buttonPos.y + button.getHeight())
            return true;
        return false;
    }
}
