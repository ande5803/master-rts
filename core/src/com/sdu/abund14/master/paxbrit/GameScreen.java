package com.sdu.abund14.master.paxbrit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.sdu.abund14.master.paxbrit.interfaces.Processor;
import com.sdu.abund14.master.paxbrit.processor.FactoryShipRenderingProcessor;

import java.util.LinkedList;
import java.util.List;

public class GameScreen implements Screen {
    private static final float BG_COLOR_RED = .3f;
    private static final float BG_COLOR_GREEN = .6f;
    private static final float BG_COLOR_BLUE = .7f;

    private List<Processor> processors;

    @Override
    public void show() {
        processors = new LinkedList<Processor>();
        processors.add(new FactoryShipRenderingProcessor());
        Gdx.input.setInputProcessor(GameStage.getInstance());
    }

    @Override
    public void render(float delta) {
        GameStage.getInstance().act(delta);
        Gdx.gl.glClearColor(BG_COLOR_RED, BG_COLOR_GREEN, BG_COLOR_BLUE, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        for (Processor processor : processors) {
            processor.process();
        }
        GameStage.getInstance().draw();
    }

    @Override
    public void resize(int width, int height) { }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void hide() { }

    @Override
    public void dispose() {
        for (Processor processor : processors) {
            processor.dispose();
        }
        GameStage.getInstance().dispose();
    }
}
