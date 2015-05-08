package com.oregonmade.joust.creation;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class spawnPlayer
{
	private BodyDef player;
	public spawnPlayer(int xposition, int yposition)
	{
		player = new BodyDef();
		player.type = BodyType.DynamicBody;
		player.position.set(xposition,yposition);
	}
}
