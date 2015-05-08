//dunno what to do here yet
package com.oregonmade.practicegame.box2d;

import com.oregonmade.practicegame.enums.UserDataType;

public class GroundUserData extends UserData
{
	public GroundUserData(float width, float height)
	{
		super(width, height);
		userDataType = UserDataType.GROUND;
	}
}
