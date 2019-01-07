package com.sdu.abund14.master.paxbrit;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.sdu.abund14.master.paxbrit.ship.factoryship.FactoryShipRenderingProcessor;

public class PaxBritannicaCaseStudy extends ApplicationAdapter {
	private static final float BG_COLOR_RED = .3f;
	private static final float BG_COLOR_GREEN = .6f;
	private static final float BG_COLOR_BLUE = .7f;

	private FactoryShipRenderingProcessor factoryShipMovementProcessor;

	
	@Override
	public void create () {
		factoryShipMovementProcessor = new FactoryShipRenderingProcessor();

	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(BG_COLOR_RED, BG_COLOR_GREEN, BG_COLOR_BLUE, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		factoryShipMovementProcessor.process();
	}
	
	@Override
	public void dispose () {

	}
}
