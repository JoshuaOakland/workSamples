package com.oregonmade.practicegame.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.oregonmade.practicegame.box2d.RunnerUserData;
import com.oregonmade.practicegame.utils.Constants;


public class Runner extends GameActor
{
	private boolean jumping;
	private boolean dodging;
	private boolean hit;
	private Animation runningAnimation;
	private TextureRegion jumpingTexture;
	private TextureRegion dodgingTexture;
	private TextureRegion hitTexture;
	private float stateTime;

	public Runner(Body body)
	{
		super(body);
		TextureAtlas textureAtlas = new TextureAtlas(Constants.CHARACTERS_ATLAS_PATH);
		TextureRegion[] runningFrames = new TextureRegion[Constants.RUNNER_RUNNING_REGION_NAMES.length];
		for (int i=0; i< Constants.RUNNER_RUNNING_REGION_NAMES.length; ++i)
		{
			String path = Constants.RUNNER_RUNNING_REGION_NAMES[i];
			runningFrames[i] = textureAtlas.findRegion(path);
		}
		runningAnimation = new Animation(0.1f, runningFrames);
		stateTime = 0f;
		jumpingTexture = textureAtlas.findRegion(Constants.RUNNER_JUMPING_REGION_NAME);
		dodgingTexture = textureAtlas.findRegion(Constants.RUNNER_DODGING_REGION_NAME);
		hitTexture = textureAtlas.findRegion(Constants.RUNNER_HIT_REGION_NAME);
	}

	@Override
	public void draw(Batch batch, float parentAlpha)
	{
		super.draw(batch, parentAlpha);

		if (dodging)
		{
			batch.draw(dodgingTexture, screenRectangle.x, screenRectangle.y + screenRectangle.height / 4, screenRectangle.width, screenRectangle.height * 3 / 4);
		}
		else if (hit)
		{
			batch.draw(hitTexture, screenRectangle.x, screenRectangle.y, screenRectangle.width * 0.5f, screenRectangle.height * 0.5f, screenRectangle.width, screenRectangle.height, 1f, 1f,(float) Math.toDegrees(body.getAngle()));
		}
		else if (jumping)
		{
			batch.draw(jumpingTexture, screenRectangle.x, screenRectangle.y, screenRectangle.width, screenRectangle.height);
		}
		else
		{
			//we are running
			stateTime += Gdx.graphics.getDeltaTime();
			batch.draw(runningAnimation.getKeyFrame(stateTime, true), screenRectangle.x, screenRectangle.y, screenRectangle.getWidth(), screenRectangle.getHeight());
		}
	}

	@Override
	public RunnerUserData getUserData()
	{
		return(RunnerUserData) userData;
	}

	public void jump()
	{
		if (!(jumping || dodging || hit))
		{
			body.applyLinearImpulse(getUserData().getJumpingLinearImpulse(), body.getWorldCenter(), true);
			jumping = true;
		}
	}

	public void landed()
	{
		jumping = false;
	}

	public void dodge()
	{
		if (!(jumping || hit))
		{
		body.setTransform(getUserData().getDodgePosition(), getUserData().getDodgeAngle());
		dodging = true;
		}
	}

	public void stopDodge()
	{
		if (!hit)
		{
		dodging = false;
		body.setTransform(getUserData().getRunningPosition(), 0f);
		}
	}

	public boolean isDodging()
	{
		return dodging;
	}

	public void hit()
	{
		if (!hit)
		{
			body.applyAngularImpulse(getUserData().getHitAngularImpulse(),true);
			hit = true;
		}
	}
	public boolean isHit()
	{
		return hit;
	}
}
