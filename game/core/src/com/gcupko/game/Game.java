package com.gcupko.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gcupko.game.states.GameStateManager;
import com.gcupko.game.states.MenuState;
import com.gcupko.game.states.PlayState;

public class Game extends ApplicationAdapter {
	public static final int WIDTH = 460;
	public static final int HEIGHT = 800;

	private GameStateManager gsm;
	SpriteBatch sb;
	
	@Override
	public void create () {
		sb = new SpriteBatch();
		gsm = new GameStateManager();
		gsm.push(new MenuState(gsm));
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(sb);
	}
	
	@Override
	public void dispose () {
		sb.dispose();
	}
}
