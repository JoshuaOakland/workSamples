package com.oregonmade.practicegame;

import com.badlogic.gdx.Game;
import com.oregonmade.practicegame.screens.GameScreen;

public class practicegame extends Game
{

	@Override
	public void create ()
	{
		setScreen(new GameScreen());
	}
}
