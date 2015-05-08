package com.oregonmade.joust.menus;

import com.oregonmade.joust.joust;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.oregonmade.joust.screens.gameScreen;

public class mainMenuScreen implements Screen 
{

	final joust game;
	OrthographicCamera camera;

	public mainMenuScreen(final joust constructor) 
	{
	        game = constructor;
        	camera = new OrthographicCamera(800,480);
        	camera.setToOrtho(false);
	}

	@Override
	public void render(float delta) 
	{
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        	camera.update();
	        game.batch.setProjectionMatrix(camera.combined);

        	game.batch.begin();
	        game.font.draw(game.batch, "Welcome to Joust!!! ", 100, 150);
	        game.font.draw(game.batch, "Tap anywhere to begin!", 100, 100);
	        game.batch.end();

        	if (Gdx.input.isTouched()) 
		{
        		game.setScreen(new gameScreen(game));
        		dispose();
		}
	}

	@Override 
	public void show()
	{
		//play music?
	}

	@Override
	public void hide()
	{
	}

	@Override
	public void pause()
	{
	}

	@Override
	public void dispose()
	{
		//music dispose
		//images dispose
		
	}
	@Override
	public void resume()
	{
	}
	@Override
	public void resize(int x, int y)
	{
	}
}
