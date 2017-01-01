package com.gcupko.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

/**
 * Created by Gligorije on 30.12.2016..
 */
public class ScoresState extends State {

    private Texture background;
    private BitmapFont scoreFont;

    protected ScoresState(GameStateManager gsm) {
        super(gsm);
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();

        hudCam.setToOrtho(false, width, height);

        background = new Texture(Gdx.files.internal("ui/menubackground.jpg"));
        scoreFont = new BitmapFont(Gdx.files.internal("fonts/asimov.fnt"));
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched()) {
            gsm.pop();
        }
    }

    @Override
    public void update(float deltaTime) {
        handleInput();
        hudCam.update();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(hudCam.combined);
        sb.begin();
        sb.draw(background, 0, 0, width, height);
        drawScores(sb);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
    }

    private void drawScores(SpriteBatch sb)
    {
        ArrayList<String> scores = new ArrayList<String>();
        Preferences prefs = Gdx.app.getPreferences("scores");

        for (int i = 1; i <= 5; i++) {
            if (prefs.getString("hs" + Integer.toString(i)) == "")
                scores.add("0");
            else
                scores.add(prefs.getString("hs" + Integer.toString(i)));
        }

        int i = 1;
        for (String score : scores) {
            scoreFont.draw(sb, i + ":    " + score,
                    hudCam.position.x - hudCam.viewportWidth/2 + 50,
                    hudCam.position.y + hudCam.viewportHeight/2 - 70*i++);
        }
    }
}
