package com.sdu.abund14.master.paxbrit;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class PaxBritannicaGame extends Game {

	@Override
	public void create () {
		Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND);
		setScreen(new GameScreen());
	}
}
