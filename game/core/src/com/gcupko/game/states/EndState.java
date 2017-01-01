package com.gcupko.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

/**
 * Created by Gligorije on 27.12.2016..
 */
public class EndState extends State
{
    private Texture background;
    private BitmapFont scoreFont;
    private BitmapFont infoFont;
    private int score;

    protected EndState(GameStateManager gsm, int score)
    {
        super(gsm);
        this.score = score;
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();

        background = new Texture("ui/menubackground.jpg");
        infoFont = new BitmapFont(Gdx.files.internal("fonts/asimov.fnt"));
        scoreFont = new BitmapFont(Gdx.files.internal("fonts/asimov.fnt"));

        hudCam.setToOrtho(false, width, height);
        saveScore();
    }

    @Override
    protected void handleInput()
    {
        if (Gdx.input.isTouched(0) && Gdx.input.isTouched(1)
                || Gdx.input.isKeyPressed(Input.Keys.ENTER))
            gsm.pop();
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
                hudCam.position.x - hudCam.viewportWidth/2 + 30,
                hudCam.position.y + hudCam.viewportHeight/2 - 70);
        infoFont.draw(sb, "Two finger touch to go back",
                hudCam.position.x - hudCam.viewportWidth/2 + 30,
                hudCam.position.y + hudCam.viewportHeight/2 - 120);
        sb.end();
    }

    @Override
    public void dispose()
    {
        scoreFont.dispose();
        infoFont.dispose();
    }

    private void saveScore() {
        ArrayList<String> scores = new ArrayList<String>();
        Preferences prefs = Gdx.app.getPreferences("scores");

        for (int i = 1; i <= 5; i++) {
            if (prefs.getString("hs" + Integer.toString(i)) == "")
                scores.add("0");
            else
                scores.add(prefs.getString("hs" + Integer.toString(i)));
        }

        for (String score : scores) {
            if (this.score > Integer.parseInt(score)) {
                scores.add(scores.indexOf(score), Integer.toString(this.score));
                break;
            }
        }

        scores = new ArrayList<String>(scores.subList(0, 5));

        for (int i = 1; i <= 5; i++) {
            prefs.putString("hs" + Integer.toString(i), scores.get(i-1));
        }

        prefs.flush();
    }
}
