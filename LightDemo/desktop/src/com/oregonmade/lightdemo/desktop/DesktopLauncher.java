package com.oregonmade.lightdemo.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.oregonmade.lightdemo.lightdemo;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title="Light Demo";
		config.height=1280;
		config.width=720;
		new LwjglApplication(new lightdemo(), config);
	}
}
