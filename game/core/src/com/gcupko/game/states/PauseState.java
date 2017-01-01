package com.gcupko.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.gcupko.game.Game;

/**
 * Created by Gligorije on 27.12.2016..
 */
public class PauseState extends State
{
    private Texture background;
    private Texture muteBtn;
    private Texture menuBtn;

    private Vector2 muteBtnPos;
    private Vector2 menuBtnPos;

    protected PauseState(GameStateManager gsm)
    {
        super(gsm);
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();

        hudCam.setToOrtho(false, width, height);

        background = new Texture(Gdx.files.internal("ui/menubackground.jpg"));
        menuBtn = new Texture(Gdx.files.internal("ui/menubtn.png"));
        if (!Game.isMute()) muteBtn = new Texture(Gdx.files.internal("ui/soundbtn.png"));
        else muteBtn = new Texture(Gdx.files.internal("ui/mutebtn.png"));

        muteBtnPos = new Vector2(hudCam.position.x + hudCam.viewportWidth/2 - muteBtn.getWidth() - 10,
                hudCam.position.y + hudCam.viewportHeight/2 - muteBtn.getHeight() - 10);
        menuBtnPos = new Vector2(hudCam.position.x - hudCam.viewportWidth/2 + 10,
                hudCam.position.y + hudCam.viewportHeight/2 - muteBtn.getHeight() - 20);
    }

    @Override
    protected void handleInput()
    {
        if (Gdx.input.justTouched()) {
            if (buttonTouched(muteBtn, muteBtnPos)) {
                if (Game.isMute()) Game.setMute(false);
                else Game.setMute(true);
            }
            else if (buttonTouched(menuBtn, menuBtnPos))
                gsm.pop();
            gsm.pop();
        }
    }

    @Override
    public void update(float deltaTime)
    {
        if (!Game.isMute()) {
            muteBtn = new Texture(Gdx.files.internal("ui/soundbtn.png"));
        }
        else {
            muteBtn = new Texture(Gdx.files.internal("ui/mutebtn.png"));
        }

        handleInput();
        hudCam.update();
    }

    @Override
    public void render(SpriteBatch sb)
    {
        sb.setProjectionMatrix(hudCam.combined);
        sb.begin();
        sb.draw(background, 0, 0, width, height);
        sb.draw(muteBtn, muteBtnPos.x, muteBtnPos.y);
        sb.draw(menuBtn, menuBtnPos.x, menuBtnPos.y);
        sb.end();
    }

    @Override
    public void dispose()
    {
        background.dispose();
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
