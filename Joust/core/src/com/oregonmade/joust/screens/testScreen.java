//Goal * I want to instantiate a composite body made from box2d primitive rectangles to simulate
// a ragdoll.

//box2d rectangles will compose the feet, shins, theighs, butt, torso, chest, shoulders, biceps, forearms, hands, neck, and head.
// that totals to twelve rectangles. to be instantiated upon impact.

//PACKAGE
package com.oregonmade.joust.screens;
//libGDX imports
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
//local imports
import com.oregonmade.joust.joust;
import com.oregonmade.joust.utilities.setUps;
import com.oregonmade.joust.utilities.constants;

public class testScreen implements Screen
{
final joust game;
private OrthographicCamera camera;
private World world;
public Body ground, runner;
private Body playerHead, playerChest,playerRightUpperArm,playerLeftUpperArm,playerRightLowerArm,playerLeftLowerArm,playerRightUpperLeg,playerLeftUpperLeg,playerRightLowerLeg,playerLeftLowerLeg;
private Box2DDebugRenderer renderer;
//instance variables

	public testScreen(joust constructor)
	{
		game = constructor;
		world = setUps.setUpWorld(world);
		ground = setUps.createGround(world);
		makePlayer(0,0,2.0f);
		makePlayer(-100,-30,.1f);		



		camera = setUps.setUpCamera(camera);
		camera.setToOrtho(false);
		renderer = new Box2DDebugRenderer();
		//weird error ground.position.set(0,0);
		//setup world
	}
	public testScreen(joust constructor, World worldConstructor)
	{
		game = constructor;
		world = worldConstructor;
		
		//bunch of shit to access methods without making it static.
	}
	public void render(float delta)
	{
		Gdx.gl.glClearColor(0.25f,0.15f,.5f,1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		renderer.render(world,camera.combined);
		world.step(1/60f,2,6);
		setUpControls();
	}

//CONTROLS
	public void setUpControls()
	{

		if (Gdx.input.isKeyPressed(Keys.A))
		{
			playerChest.applyLinearImpulse(10000f,0f,10f,0f,true);
		}
		if (Gdx.input.isKeyPressed(Keys.D))
		{
			playerChest.applyLinearImpulse(-10000f,0f,10f,0f,true);
		}
		if (Gdx.input.isKeyPressed(Keys.SPACE))
		{
			playerChest.applyLinearImpulse(0f,10000f,0f,0f,true);
		}
		if (Gdx.input.isKeyPressed(Keys.CONTROL_LEFT))
		{
			playerChest.applyLinearImpulse(0f,-10000f,0f,0f,true);
		}
		if (Gdx.input.isKeyPressed(Keys.ALT_LEFT))
		{
        		game.setScreen(new testScreen(game));
        		dispose();
		}
		if (Gdx.input.isKeyPressed(Keys.ALT_RIGHT))
		{
        		game.setScreen(new gameScreen(game));
        		dispose();
		}
	}
//CREATION
	public Body makePlayer(int x, int y,float scale)
	{
		playerHead = setUps.createRunner(world,constants.HEAD_X+x,constants.HEAD_Y+y+5,20f*scale,20f*scale);
		playerChest = setUps.createRunner(world,constants.CHEST_X+x,constants.CHEST_Y+y,40f*scale,20f*scale);
		playerRightUpperArm = setUps.createRunner(world,constants.RIGHT_UPPERARM_X+x,constants.RIGHT_UPPERARM_Y+y,10f*scale,20f*scale);
		playerLeftUpperArm = setUps.createRunner(world,constants.LEFT_UPPERARM_X+x,constants.LEFT_UPPERARM_Y+y,10f*scale,20f*scale);
		playerRightLowerArm = setUps.createRunner(world,constants.RIGHT_LOWERARM_X+x,constants.RIGHT_LOWERARM_Y+y,10f*scale,15f*scale);
		playerLeftLowerArm = setUps.createRunner(world,constants.LEFT_LOWERARM_X+x,constants.LEFT_LOWERARM_Y+y,10f*scale,15f*scale);
		playerRightUpperLeg = setUps.createRunner(world,constants.RIGHT_UPPERLEG_X+x,constants.RIGHT_UPPERLEG_Y+y,15f*scale,20f*scale);
		playerLeftUpperLeg = setUps.createRunner(world,constants.LEFT_UPPERLEG_X+x,constants.LEFT_UPPERLEG_Y+y,15f*scale,20f*scale);
		playerRightLowerLeg = setUps.createRunner(world,constants.RIGHT_LOWERLEG_X+x,constants.RIGHT_LOWERLEG_Y+y,10f*scale,20f*scale);
		playerLeftLowerLeg = setUps.createRunner(world,constants.LEFT_LOWERLEG_X+x,constants.LEFT_LOWERLEG_Y+y,10f*scale,20f*scale);
		DistanceJointDef neck = new DistanceJointDef();
		neck = setUps.makeJoint(world, neck, playerHead, playerChest,20*scale,0*scale,-20*scale,0*scale,25*scale);

		DistanceJointDef rightShoulder = new DistanceJointDef();
		rightShoulder = setUps.makeJoint(world, rightShoulder, playerRightUpperArm, playerChest,25*scale,0*scale,10*scale,-8*scale,20*scale);


		DistanceJointDef leftShoulder = new DistanceJointDef();
		leftShoulder = setUps.makeJoint(world, leftShoulder, playerLeftUpperArm, playerChest,25*scale,0*scale,10*scale,8*scale,20*scale);

		DistanceJointDef rightElbow = new DistanceJointDef();
		rightElbow = setUps.makeJoint(world, rightElbow, playerRightUpperArm, playerRightLowerArm,45*scale,0*scale,0*scale,0*scale,0*scale);


		DistanceJointDef leftElbow = new DistanceJointDef();
		leftElbow = setUps.makeJoint(world, leftElbow, playerLeftUpperArm, playerLeftLowerArm,45*scale,0*scale,0*scale,0*scale,0*scale);

		DistanceJointDef rightHip = new DistanceJointDef();
		rightHip = setUps.makeJoint(world, rightHip, playerRightUpperLeg, playerChest,45*scale,0*scale,10*scale,-10*scale,-20*scale);

		DistanceJointDef leftHip = new DistanceJointDef();
		rightHip = setUps.makeJoint(world, leftHip, playerLeftUpperLeg, playerChest,45*scale,0*scale,10*scale,10*scale,-20*scale);

		DistanceJointDef rightKnee = new DistanceJointDef();
		rightKnee = setUps.makeJoint(world, rightKnee, playerRightUpperLeg, playerRightLowerLeg,20*scale,0*scale,-10*scale,0*scale,10*scale);
		DistanceJointDef leftKnee = new DistanceJointDef();
		leftKnee = setUps.makeJoint(world, leftKnee, playerLeftUpperLeg, playerLeftLowerLeg,20*scale,0*scale,-10*scale,0*scale,10*scale);
		return (playerChest);

	}

//OVERRIDDEN METHODS
	public void dispose()
	{
	}
	public void hide()
	{
	}
	public void resume()
	{
	}
	public void pause()
	{
	}
	public void show()
	{
	}
	public void resize(int x,int y)
	{
	}
//END OF OVERRIDDEN METHODS



}




//render


