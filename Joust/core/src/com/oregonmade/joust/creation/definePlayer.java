package com.oregonmade.joust.creation;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class definePlayer
{
	public static BodyDef player;
	public static BodyDef definePlayer(int xposition, int yposition)
	{
		player = new BodyDef();
		player.type = BodyType.DynamicBody;
		player.position.set(xposition,yposition);
		return player;
	}
}