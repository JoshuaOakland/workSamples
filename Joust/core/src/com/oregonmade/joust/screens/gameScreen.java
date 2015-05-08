package com.oregonmade.joust.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Rectangle;
import com.oregonmade.joust.joust;
import com.oregonmade.joust.menus.mainMenuScreen;
import com.oregonmade.joust.menus.optionsMenuScreen;
import com.oregonmade.joust.creation.definePlayer;
import com.oregonmade.joust.utilities.setUps;
import com.oregonmade.joust.utilities.constants;
import com.oregonmade.joust.screens.testScreen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

//import texture

public class gameScreen implements Screen
{
//audio
	public Music indie;
	public Sound galloping,whinny;
//graphics
	private SpriteBatch batch;
	private Texture horse,horse2,lance1,lance2,player1,player2,background;
	private OrthographicCamera camera;
//logic
	public boolean whinnyHasPlayed,playerHit;
//entity
	public Rectangle horsePlayer;
	public Rectangle horseNotPlayer;
	public Rectangle player, notPlayer;
	public Rectangle playerLance, notPlayerLance;

//weird game constructor thing I don't understand ***MAGIC***
	final joust game;
//box2d shite
	private Body runner,ragRunner;
	private Body ground;
	private World world;
	private Box2DDebugRenderer renderer;
	private testScreen testerFack;
	private int ragX,ragY;

	public gameScreen(final joust constructor)
	{
		boolean isExtAvailable = Gdx.files.isExternalStorageAvailable();
		boolean isLocAvailable = Gdx.files.isLocalStorageAvailable();
		game = constructor;
		logicSetup();
		audioSetup();
		graphicsSetup();
		physicsSetup();
		testerFack = new testScreen(game,world);
		Body testPlayer = testerFack.makePlayer(-(int)constants.playerX,-(int)constants.playerY,.15f);
		testPlayer.applyLinearImpulse(0f,-10000f,0f,0f,true);
		if(isLocAvailable){System.out.println("local available");}
		if(isExtAvailable){System.out.println("external available");}
		

	}
	@Override
	public void render(float delta)
	{
		graphics();
		collision();
		artificialIntelligence();
	}
















//Functions
//Audio

	public void audioSetup()
	{
		indie = Gdx.audio.newMusic(Gdx.files.internal("Indiana.mp3"));
		galloping = Gdx.audio.newSound(Gdx.files.internal("gallop.wav"));
		whinny = Gdx.audio.newSound(Gdx.files.internal("whinny.wav"));
		galloping.loop();
		whinnyHasPlayed = false;
	}
//AI
	public void artificialIntelligence()
	{
		constants.horse2X-=constants.horseSpeed;horseNotPlayer.setX(constants.horse2X);horseNotPlayer.setY(constants.horse2Y);
		constants.notPlayerX-=constants.horseSpeed;notPlayer.setX(constants.notPlayerX);notPlayer.setY(constants.notPlayerY);
constants.notPlayerLanceX-=constants.horseSpeed;notPlayerLance.setX(constants.notPlayerLanceX);notPlayerLance.setY(constants.notPlayerLanceY);
		if (constants.horse2X<0)
		{
			galloping.stop();
		}
		if (constants.horseY > 10)
		{
			if (!indie.isPlaying())
			{
				indie.play();
			}
		}
	}
//Input
	public void controls()
	{
        	if (Gdx.input.isKeyPressed(Keys.ESCAPE))
		{
        		game.setScreen(new optionsMenuScreen(game));
        		dispose();
		}
        	if (Gdx.input.isKeyPressed(Keys.NUMPAD_9))
		{
        		game.setScreen(new testScreen(game));
        		dispose();
		}
		if (Gdx.input.isTouched()) 
		{
			Vector3 movementVector = new Vector3();
			movementVector.set(Gdx.input.getX(),Gdx.input.getY(),0);
			camera.unproject(movementVector);
			constants.horseX = movementVector.x -64 / 2;
			constants.horseY = movementVector.y -64 / 2;
			constants.playerX = (movementVector.x -64 / 2)+32;
			constants.playerY = (movementVector.y -64 / 2)+64;
			constants.playerLanceX = (movementVector.x -64 / 2)+32;
			constants.playerLanceY = (movementVector.y -64 / 2)+42;
		}
		if (Gdx.input.isKeyPressed(Keys.A))
		{
			constants.horseX +=constants.horseSpeed;
			constants.playerX +=constants.horseSpeed;
			constants.playerLanceX +=constants.horseSpeed;
			runner.applyLinearImpulse(10000f,0f,10f,0f,true);
		}
		if (Gdx.input.isKeyPressed(Keys.D))
		{
			constants.horseX-=constants.horseSpeed;
			constants.playerX -=constants.horseSpeed;
			constants.playerLanceX -=constants.horseSpeed;
			runner.applyLinearImpulse(-10000f,0f,10f,0f,true);
		}
		if (Gdx.input.isKeyPressed(Keys.SPACE))
		{
			constants.horseY+=constants.horseSpeed;
			constants.playerY +=constants.horseSpeed;
			constants.playerLanceY +=constants.horseSpeed;
			runner.applyLinearImpulse(0f,10000f,0f,0f,true);
		}
		if (Gdx.input.isKeyPressed(Keys.CONTROL_LEFT))
		{
			constants.horseY-=constants.horseSpeed;
			constants.playerY -=constants.horseSpeed;
			constants.playerLanceY -=constants.horseSpeed;
			runner.applyLinearImpulse(0f,-10000f,0f,0f,true);
		}
	}
//Physics
	public void physicsSetup()
	{
		world = new World(constants.WORLD_GRAVITY, true);
		Body body = world.createBody(definePlayer.definePlayer(0,0));
 		runner = setUps.createRunner(world);
		ground = setUps.createGround(world);
	}
	public void collision()
		{
		world.step(1/60f,2,6);
		ragX=(int)constants.playerX;
		ragY=(int)constants.playerY;
		if(horsePlayer.overlaps(horseNotPlayer))
		{
			if (!whinnyHasPlayed)
			{
				whinnyHasPlayed = true;
				whinny.play();
				indie.stop();
				System.out.println("DEM HORSES IS TUCHINN!!!");
			}
		}

		if(playerLance.overlaps(notPlayer))
		{
			//wreck dat PC!!
			System.out.println("Player hits computer");
			if (playerHit != true)
			{
				Body testPlayer = testerFack.makePlayer(ragX,ragY,.15f);
		testPlayer.applyLinearImpulse(0f,-10000f,0f,0f,true);
			}
			playerHit = true;
			spawnBox(playerHit);
			playerHit = true;
		}
		if(playerLance.overlaps(horseNotPlayer))
		{
			//coming soon
			System.out.println("Player hits computer horse");
			if (playerHit != true)
			{
				Body testPlayer = testerFack.makePlayer(ragX,ragY,.15f);
		testPlayer.applyLinearImpulse(0f,-10000f,0f,0f,true);
			}
			playerHit = true;
			spawnBox(playerHit);
			playerHit = true;
		}
		if(notPlayerLance.overlaps(player))
		{
			//Lol talk about destruction
			System.out.println("Computer hits player!!");
			spawnBox(playerHit);
			if (playerHit != true)
			{
				Body testPlayer = testerFack.makePlayer(ragX,ragY,.15f);
		testPlayer.applyLinearImpulse(0f,-10000f,0f,0f,true);
			}
			playerHit = true;
		}
		if(notPlayerLance.overlaps(horsePlayer))
		{
			//WRECK!
			System.out.println("Computer hits player Horse");
			spawnBox(playerHit);
			if (playerHit != true)
			{
				Body testPlayer = testerFack.makePlayer(ragX,ragY,.15f);
		testPlayer.applyLinearImpulse(0f,-10000f,0f,0f,true);
			}
			playerHit = true;
		}

		}
//game logic
	public void logicSetup()
	{
		//HORSES
		horsePlayer = new Rectangle(constants.horseX,constants.horseY,64f,64f);
		horseNotPlayer = new Rectangle(constants.horse2X,constants.horse2Y,64f,64f);
		//HumanS!
		player = new Rectangle(constants.playerX,constants.playerY,10f,25f);
		notPlayer = new Rectangle(constants.notPlayerX,constants.notPlayerY+64,10f,25f);
		//WEAPONS OF MAYHEM!
		playerLance = new Rectangle(constants.playerLanceX,constants.playerLanceY,32f,8f);
		notPlayerLance = new Rectangle(constants.notPlayerLanceX,constants.notPlayerLanceY,32f,8f);
	}
	public void boundsCheck()
	{
		if (constants.horseY <0)
		{
			constants.horseY=0;
		}
		if (constants.horseY >= 480-64)
		{
			constants.horseY = (480-64);
		}
	}
//Visuals
	public void graphicsSetup()
	{
		batch = new SpriteBatch();
		background = new Texture(Gdx.files.internal("background.png"));
		horse2 = new Texture(Gdx.files.internal("horse2.png"));
		horse = new Texture(Gdx.files.internal("horse.png"));
		lance1 = new Texture(Gdx.files.internal("playerLance.png"));
		lance2 = new Texture(Gdx.files.internal("notPlayerLance.png"));
		player1 = new Texture(Gdx.files.internal("player.png"));
		player2 = new Texture(Gdx.files.internal("notPlayer.png"));
		renderer = new Box2DDebugRenderer();
		camera = new OrthographicCamera(800,480);
		camera.setToOrtho(false);
	}
	public void graphics()
	{
		Gdx.gl.glClearColor(1f,1f,1f,1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();
		game.batch.draw(background,constants.backgroundX,constants.backgroundY);
		game.batch.draw(horse, constants.horseX,constants.horseY);
		game.batch.draw(player1,constants.playerX-12,constants.playerY-38);
		game.batch.draw(lance1, constants.playerLanceX,constants.playerLanceY-20);
		game.batch.draw(lance2, constants.notPlayerLanceX,constants.notPlayerLanceY-20);
		game.batch.draw(player2,constants.notPlayerX+65,constants.notPlayerY-38);
		game.batch.draw(horse2, constants.horse2X,constants.horse2Y);
		game.batch.end();
		controls();
		horsePlayer.setX(constants.horseX);
		horsePlayer.setY(constants.horseY);
		player.setX(constants.playerX);
		player.setY(constants.playerY);
		playerLance.setX(constants.playerLanceX);
		playerLance.setY(constants.playerLanceY);
		renderer.render(world, camera.combined);
	}
public void spawnBox(boolean playerHit)
{
			if (playerHit!=true)
			{
		 		ragRunner = setUps.createRunner(world,constants.playerX,constants.playerY,10f,25f);
				ragRunner.applyLinearImpulse(-100000f,0f,0f,0f,true);
			}
	playerHit = true;
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
		indie.stop();
		indie.dispose();
		indie.pause();
		galloping.dispose();
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
