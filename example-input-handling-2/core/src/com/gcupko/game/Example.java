package com.gcupko.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Example extends InputAdapter implements ApplicationListener
{
	SpriteBatch sb;
	MovingSprite ms;
	private boolean dragging;

	@Override
	public void create () {
		sb = new SpriteBatch();
		ms = new MovingSprite(0, 0);
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void resize(int width, int height) {
		sb = new SpriteBatch();
		ms = new MovingSprite(0, 0);
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		sb.begin();
		sb.draw(ms.getFigure(), ms.getPosition().x, ms.getPosition().y);
		sb.end();
	}

	@Override
	public void pause() { }

	@Override
	public void resume() { }

	@Override
	public void dispose () {
		sb.dispose();
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button)	{
		if (ms.isTouched())
			dragging = true;
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		if (dragging)
			ms.update(Gdx.input.getDeltaX(), -Gdx.input.getDeltaY());
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		dragging = false;
		System.out.println(dragging);
		return true;
	}
}
