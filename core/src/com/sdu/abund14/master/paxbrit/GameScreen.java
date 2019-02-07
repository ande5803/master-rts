package com.sdu.abund14.master.paxbrit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.sdu.abund14.master.paxbrit.interfaces.Processor;
import com.sdu.abund14.master.paxbrit.processor.CombatShipProcessor;
import com.sdu.abund14.master.paxbrit.processor.FactoryShipProcessor;
import com.sdu.abund14.master.paxbrit.ship.Ship;

import java.util.LinkedList;
import java.util.List;

public class GameScreen implements Screen {
    private static final float BG_COLOR_RED = .3f;
    private static final float BG_COLOR_GREEN = .6f;
    private static final float BG_COLOR_BLUE = .7f;

    private List<Processor> processors;
    private GameStage stage;
    private SpriteBatch batch;

    public GameStage getStage() {
        return stage;
    }

    @Override
    public void show() {
        processors = new LinkedList<Processor>();
        stage = new GameStage();
        batch = new SpriteBatch();
        processors.add(new FactoryShipProcessor());
        processors.add(new CombatShipProcessor());
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        stage.act(delta);
        Gdx.gl.glClearColor(BG_COLOR_RED, BG_COLOR_GREEN, BG_COLOR_BLUE, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        for (Processor processor : processors) {
            processor.process(delta);
        }
        stage.draw();
        batch.begin();
        for (Ship ship : PaxBritannicaGame.currentMatch.getAllShips()) {
            ship.draw(batch, 1);
        }
        batch.end();
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
        stage.dispose();
    }
}
