package com.gcupko.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Example extends ApplicationAdapter {
	private SpriteBatch sb;

	@Override
	public void create () {
		Gdx.gl.glClearColor(0.8f, 0.9f, 1, 1);
		sb = new SpriteBatch();
	}

	@Override
	public void render ()
	{
		int width = Gdx.graphics.getWidth();
		int height = Gdx.graphics.getHeight();
		Texture figure = new Texture("figure.png");

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		sb.begin();
		sb.draw(figure, width/2 - figure.getWidth()/2, height/2 - figure.getHeight()/2);
		sb.end();
	}

	@Override
	public void dispose () {
		sb.dispose();
	}
}
