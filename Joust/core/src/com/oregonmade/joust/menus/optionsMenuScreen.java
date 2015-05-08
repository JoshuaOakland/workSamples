package com.oregonmade.joust.menus;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.Gdx;
import com.oregonmade.joust.utilities.setUps;
import com.oregonmade.joust.screens.gameScreen;
import com.oregonmade.joust.joust;

public class optionsMenuScreen implements Screen
{
	final joust game;
	OrthographicCamera camera;

	public optionsMenuScreen(final joust constructor)
	{
		game = constructor;
		/*camera = setUps.setUpCamera(camera);
		camera.setToOrtho(false);*/
	}
	@Override
	public void render(float delta)
	{
		Gdx.gl.glClearColor(0.5f,0.3f,0.7f,1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.batch.begin();
		game.font.draw(game.batch, "Options Menu!", 400,200);
		game.font.draw(game.batch, "New Game",400,220);
		game.font.draw(game.batch, "Load Game",400,240);
		game.font.draw(game.batch, "Exit",400,260);
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
