package com.gcupko.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
// GL20 is a wrapper for OpenGL ES methods
import com.badlogic.gdx.graphics.GL20;
// ShapeType is a class used to draw shapes
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
// ShapeType defines how the shape will be drawn
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class Example extends ApplicationAdapter {
	private ShapeRenderer shapeRenderer;

	@Override
	public void create () {
		// set background color
		Gdx.gl.glClearColor(1, 1, 1, 1);
		// initialize shapeRenderer
		shapeRenderer = new ShapeRenderer();
	}

	@Override
	public void render ()
	{
		// screen/window width
		int width = Gdx.graphics.getWidth();
		// screen/window height
		int height = Gdx.graphics.getHeight();

		// clear background and set to defined color
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// enables shape type change while drawing
		shapeRenderer.setAutoShapeType(true);
		// begin drawing (outline only)
		shapeRenderer.begin(ShapeType.Line);
		// set shape color
		shapeRenderer.setColor(0.8f, 0, 0.9f, 1);
		// draw a circle in the middle of the screen
		// radius is equal to minimum of screen width
		// and screen height reduced by 50 px
		shapeRenderer.circle(width/2,height/2,
				(width < height ? width : height)/2 - 50);
		// change shape type to filled
		shapeRenderer.set(ShapeType.Filled);
		// draw a 200x100 rectangle in the center of the screen
		shapeRenderer.rect(width/2 - 100,height/2 - 50, 200, 100);
		// finished
		shapeRenderer.end();
	}

	@Override
	public void dispose () {
		shapeRenderer.dispose();
	}
}
