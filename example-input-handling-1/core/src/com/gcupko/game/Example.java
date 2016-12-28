package com.gcupko.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Example extends ApplicationAdapter {
	SpriteBatch sb;
	MovingSprite ms;
	
	@Override
	public void create () {
		sb = new SpriteBatch();
		ms = new MovingSprite(0, 0);
	}

	@Override
	public void render () {
		handleInput();
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		sb.begin();
		sb.draw(ms.getFigure(), ms.getPosition().x, ms.getPosition().y);
		sb.end();
	}

	@Override
	public void dispose () {
		sb.dispose();
	}

	private void handleInput() {
		if (Gdx.input.isTouched() && ms.isTouched()) {
			ms.update(Gdx.input.getDeltaX(), -Gdx.input.getDeltaY());
		}
	}
}
