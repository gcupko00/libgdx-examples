package com.gcupko.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class Example extends ApplicationAdapter {
	public static final int WIDTH = 480;
	public static final int HEIGHT = 800;

	private OrthographicCamera cam;
	private SpriteBatch sb;
	private Sprite s;
	private Texture background;
	
	@Override
	public void create () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		sb = new SpriteBatch();
		background = new Texture("background.png");
		Texture t = new Texture("dragon.gif");
		s = new Sprite(t, WIDTH/2 - t.getWidth()/2, HEIGHT/2 - t.getHeight()/2);
		cam = new OrthographicCamera();
		cam.setToOrtho(false, WIDTH, HEIGHT);
	}

	@Override
	public void render () {
		handleInput();
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		cam.update();
		sb.setProjectionMatrix(cam.combined);

		sb.begin();
		sb.draw(background, WIDTH/2 - background.getWidth()/2, HEIGHT/2 - background.getHeight()/2);
		sb.draw(s.getTexture(), s.getPosition().x, s.getPosition().y);
		sb.end();
	}
	
	@Override
	public void dispose () {
		sb.dispose();
	}

	public void handleInput() {
		if (Gdx.input.isKeyPressed(Input.Keys.Y)) {
			cam.zoom += 0.02;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.X)) {
			cam.zoom -= 0.02;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			cam.translate(-3, 0, 0);
			s.update(-3, 0);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			cam.translate(3, 0, 0);
			s.update(3, 0);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			cam.translate(0, -3, 0);
			s.update(0, -3);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			cam.translate(0, 3, 0);
			s.update(0, 3);
		}
	}
}
