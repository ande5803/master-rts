package com.sdu.abund14.master.paxbrit;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.sdu.abund14.master.paxbrit.interfaces.Processor;
import com.sdu.abund14.master.paxbrit.processor.FactoryShipRenderingProcessor;

import java.util.LinkedList;
import java.util.List;

public class PaxBritannicaCaseStudy extends ApplicationAdapter {
	private static final float BG_COLOR_RED = .3f;
	private static final float BG_COLOR_GREEN = .6f;
	private static final float BG_COLOR_BLUE = .7f;

	private List<Processor> processors;
	
	@Override
	public void create () {
		processors = new LinkedList<Processor>();
		processors.add(new FactoryShipRenderingProcessor());
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(BG_COLOR_RED, BG_COLOR_GREEN, BG_COLOR_BLUE, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		for (Processor processor : processors) {
			processor.process();
		}
	}
	
	@Override
	public void dispose () {
		for (Processor processor : processors) {
			processor.dispose();
		}
	}
}
