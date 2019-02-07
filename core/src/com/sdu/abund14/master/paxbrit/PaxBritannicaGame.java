package com.sdu.abund14.master.paxbrit;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

public class PaxBritannicaGame extends Game {

	private Screen currentScreen;
	public static Match currentMatch;

	@Override
	public void create () {
		currentScreen = new GameScreen();
		currentMatch = new Match((GameScreen) currentScreen);
		setScreen(currentScreen);
	}
}
