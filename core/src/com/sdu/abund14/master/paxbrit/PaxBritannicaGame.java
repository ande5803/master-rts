package com.sdu.abund14.master.paxbrit;

import com.badlogic.gdx.Game;

public class PaxBritannicaGame extends Game {

	public static Match currentMatch;

	@Override
	public void create () {
		GameScreen currentScreen = new GameScreen();
		currentMatch = new Match(currentScreen);
		setScreen(currentScreen);
	}
}
