package com.sdu.abund14.master.paxbrit;

import com.badlogic.gdx.Game;

import java.util.concurrent.ForkJoinPool;

public class PaxBritannicaGame extends Game {

	public static Match currentMatch;
	public static ForkJoinPool forkJoinPool = new ForkJoinPool(16);

	@Override
	public void create () {
		GameScreen currentScreen = new GameScreen();
		currentMatch = new Match(currentScreen);
		setScreen(currentScreen);
	}
}
