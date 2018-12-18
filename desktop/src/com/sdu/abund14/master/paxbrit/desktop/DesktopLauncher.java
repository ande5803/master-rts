package com.sdu.abund14.master.paxbrit.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.sdu.abund14.master.paxbrit.PaxBritannicaCaseStudy;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new PaxBritannicaCaseStudy(), config);
	}
}
