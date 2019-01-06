package com.sdu.abund14.master.paxbrit;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PaxBritannicaCaseStudy extends ApplicationAdapter {
	private SpriteBatch batch;
	private Texture p1factory;
	private Texture p2factory;
	private Texture p3factory;
	private Texture p4factory;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		p1factory = new Texture(Gdx.files.internal("factoryp1.png"));
		p2factory = new Texture(Gdx.files.internal("factoryp2.png"));
		p3factory = new Texture(Gdx.files.internal("factoryp3.png"));
		p4factory = new Texture(Gdx.files.internal("factoryp4.png"));
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(.3f, .6f, .7f, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(p1factory, 50, 50);
		batch.draw(p2factory, 50, 300);
		batch.draw(p3factory, 300, 300);
		batch.draw(p4factory, 300, 50);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		p1factory.dispose();
		p2factory.dispose();
		p3factory.dispose();
		p4factory.dispose();
	}
}
