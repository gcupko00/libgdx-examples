package com.gcupko.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Example extends ApplicationAdapter {
	SpriteBatch sb;
	Texture img;
	Animation animation;

	@Override
	public void create () {
		sb = new SpriteBatch();
		img = new Texture("figanimation.png");
		animation = new Animation(img, 2, 0.1f);
	}

	@Override
	public void render () {
		int width = Gdx.graphics.getWidth();
		int height = Gdx.graphics.getHeight();

		Gdx.gl.glClearColor(0.7f, 0.9f, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		animation.update(Gdx.graphics.getDeltaTime());

		sb.begin();
		sb.draw(animation.getFrame(), width/2 - animation.getFrame().getRegionWidth()/2,
				height/2 - animation.getFrame().getRegionHeight()/2);
		sb.end();
	}

	@Override
	public void dispose () {
		sb.dispose();
		img.dispose();
	}
}
