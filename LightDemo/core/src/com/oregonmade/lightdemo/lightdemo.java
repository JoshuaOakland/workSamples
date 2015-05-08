package com.oregonmade.lightdemo;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Input.Peripheral;
import box2dLight.*;
import com.badlogic.gdx.math.MathUtils;


public class lightdemo extends ApplicationAdapter {
	OrthographicCamera camera;
	boolean shadowValue;
	boolean available;
	float width, height, horizontalGravity,verticalGravity;
	int direction;
	float r,g,b,f,u,x,y;
	float r2,g2,b2;
	int orientation;
	FPSLogger logger;

	World world;
	Box2DDebugRenderer renderer;

	Body circleBody;
	Body BodyAngry;
	Body groundBody,leftWall,rightWall,ceiling;
	SpriteBatch batch;
	Texture img;
	
	RayHandler handler;
	@Override
	public void create () 
	{
		available = Gdx.input.isPeripheralAvailable(Peripheral.Accelerometer);
		shadowValue = MathUtils.randomBoolean();
		width = Gdx.graphics.getWidth() / 5;
		height = Gdx.graphics.getHeight() / 5;

		camera = new OrthographicCamera(width, height);
		camera.position.set(width * 0.5f, height*0.5f, 0);
		camera.update();
		horizontalGravity = MathUtils.random(-0.5f,0.5f);
		verticalGravity = MathUtils.random(0.0f,-20f);
		world = new World(new Vector2(horizontalGravity,verticalGravity), false);

		renderer = new Box2DDebugRenderer();

		logger = new FPSLogger();
		BodyDef circleDef = new BodyDef();
		circleDef.type = BodyType.DynamicBody;
		circleDef.position.set(width/2f, height/2f);

		circleBody = world.createBody(circleDef);

		CircleShape circleShape = new CircleShape();
		circleShape.setRadius(3f);
		
		FixtureDef circleFixture = new FixtureDef();
		circleFixture.shape = circleShape;
		circleFixture.density = 0.4f;
		circleFixture.friction= 0.3f;
		circleFixture.restitution = 1f;

		circleBody.createFixture(circleFixture);
/*
		makeBall(BodyAngry,1f,2f);
		makeBall(BodyAngry,1f,5f);
		makeBall(BodyAngry,1f,3f);
		makeBall(BodyAngry,1f,4f);
		makeBall(BodyAngry,2f,1f);
		makeBall(BodyAngry,2f,2f);
		makeBall(BodyAngry,2f,3f);
		makeBall(BodyAngry,2f,4f);
		makeBall(BodyAngry,2f,5f);
		makeBall(BodyAngry,3f,4f);
		makeBall(BodyAngry,1f,3f);
		makeBall(BodyAngry,2f,3f);
		makeBall(BodyAngry,1f,1f);
		makeBall(BodyAngry,4f,1f);
		makeBall(BodyAngry,4f,2f);
		makeBall(BodyAngry,4f,3f);
		makeBall(BodyAngry,4f,4f);
*/
		BodyDef groundBodyDef = new BodyDef();
		groundBodyDef.position.set(0,3);
//ADDING TO WORLD
		Body groundBody = world.createBody(groundBodyDef);

		PolygonShape groundBox = new PolygonShape();
//GROUND
		groundBox.setAsBox((camera.viewportWidth)*2,3.0f);
		groundBody.createFixture(groundBox,0.0f);
//-------------------------------------------------------------------------creating bounds//
		BodyDef leftWallBodyDef = new BodyDef();
		leftWallBodyDef.position.set(0,(camera.viewportHeight-3));
		Body leftWall = world.createBody(leftWallBodyDef);
		PolygonShape leftWallBox = new PolygonShape();
		leftWallBox.setAsBox((camera.viewportWidth)*2,3.0f);
		leftWall.createFixture(leftWallBox,0.0f);
/////-----------------------//
		BodyDef rightWallBodyDef = new BodyDef();
		rightWallBodyDef.position.set(camera.viewportWidth,0);
		Body rightWall = world.createBody(rightWallBodyDef);
		PolygonShape rightWallBox = new PolygonShape();
		rightWallBox.setAsBox(3.0f,(camera.viewportHeight*2));
		rightWall.createFixture(rightWallBox,0.0f);
//------
		BodyDef ceilingBodyDef = new BodyDef();
		ceilingBodyDef.position.set(0,0);
		Body ceiling = world.createBody(ceilingBodyDef);
		PolygonShape ceilingBox = new PolygonShape();
		ceilingBox.setAsBox(3.0f,(camera.viewportHeight*2));
		ceiling.createFixture(ceilingBox,0.0f);

//SETTING UP LIGHTING
		handler = new RayHandler(world);
		handler.setShadows(shadowValue);
		handler.setCombinedMatrix(camera.combined);


		new PointLight(handler, 1000, Color.CYAN, 400, (width/2) -50 ,(height/2)+15);
		new PointLight(handler, 600, Color.YELLOW,400,(width/2) +50 ,(height/2)-25);
		new PointLight(handler, 400, Color.ORANGE,400,(width/2),(height/2));
		new PointLight(handler, 5000, Color.RED,200,(width/2),(height/2)-100);
		new PointLight(handler, 10, Color.WHITE,400,(width/2) +50 ,(height/2)-25);
		r=colorVal();
		g=colorVal();
		b=colorVal();
		r2=colorVal();
		g2=colorVal();
		b2=colorVal();
		direction = MathUtils.random(-360,360);
		new PointLight(handler, 5000, new Color(r,g,b,1f),400,(width/2) +50 ,(height/2)-25);
		new ConeLight(handler, 2500, new Color(r2,b2,g2,1f),200,(width/2)+50, (height/2)-10, 180, 45);
		new DirectionalLight(handler, 10000, new Color(r,g2,b,.5f),direction);
	}
	public float colorVal()
	{
		return MathUtils.random(0.0f,1.0f);
	}

	public void makeBall(Body circleBody,float widthdiv,float heightdiv)
	{
		BodyDef circleDef = new BodyDef();
		circleDef.type = BodyType.DynamicBody;
		circleDef.position.set(widthdiv, heightdiv);

		circleBody = world.createBody(circleDef);

		CircleShape circleShape = new CircleShape();
		circleShape.setRadius(MathUtils.random(0.5f,10f));
		
		FixtureDef circleFixture = new FixtureDef();
		circleFixture.shape = circleShape;
		circleFixture.density = 0.4f;
		circleFixture.friction= 0.3f;
		circleFixture.restitution = 1f;

		circleBody.createFixture(circleFixture);

	}
	@Override
	public void render () 
	{
/*
double t = 0.0;
double dt = 0.01;

double currentTime = hires_time_in_seconds();
double accumulator = 0.0;

State previous;
State current;

while ( !quit )
{
    double newTime = time();
    double frameTime = newTime - currentTime;
    if ( frameTime > 0.25 )
        frameTime = 0.25;
    currentTime = newTime;

    accumulator += frameTime;

    while ( accumulator >= dt )
    {
        previousState = currentState;
        integrate( currentState, t, dt );
        t += dt;
        accumulator -= dt;
    }

    const double alpha = accumulator / dt;

    State state = currentState * alpha + 
        previousState * ( 1.0 - alpha );

    render( state );
}
*/
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		renderer.render(world, camera.combined);
		handler.updateAndRender();
		world.step(1/60f, 6, 2);
		logger.log();
        if (Gdx.input.isTouched()) 
	{
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
		f =touchPos.x;
		u =touchPos.y;
		x = f;
		y = u;
		makeBall(BodyAngry,x,y);
        }
	if (available)
	{
		orientation = Gdx.input.getRotation();
		// debugging message System.out.println(orientation);
	}
//---------keychecking----------//

	else
	{
	boolean keyUp = Gdx.input.isKeyPressed(Keys.UP);
	boolean keyRight = Gdx.input.isKeyPressed(Keys.RIGHT);
	boolean keyDown = Gdx.input.isKeyPressed(Keys.DOWN);
	boolean keyLeft = Gdx.input.isKeyPressed(Keys.LEFT);
		if (keyUp)
		{
			orientation = 180;
		}
		if (keyRight)
		{
			orientation = 90;
		}
		if (keyDown)
		{
			orientation = 0;
		}
		if (keyLeft)
		{
			orientation = 270;
		}

	}
	if (orientation==0)
	{
		world.setGravity(new Vector2(0.0f,-9.8f));
	}
	if (orientation==90)
	{
		world.setGravity(new Vector2(9.8f,0.0f));
	}
	if (orientation==180)
	{
		world.setGravity(new Vector2(0.0f,9.8f));
	}
	if (orientation==270)
	{
		world.setGravity(new Vector2(-9.8f,0f));
	}
	
	}
	@Override
	public void dispose()
	{
		handler.dispose();
		world.dispose();
	}
}