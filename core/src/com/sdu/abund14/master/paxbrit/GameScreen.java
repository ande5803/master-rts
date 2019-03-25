package com.sdu.abund14.master.paxbrit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.sdu.abund14.master.paxbrit.bullet.Bullet;
import com.sdu.abund14.master.paxbrit.interfaces.Processor;
import com.sdu.abund14.master.paxbrit.processor.*;
import com.sdu.abund14.master.paxbrit.ship.Ship;

import java.util.LinkedList;
import java.util.List;

public class GameScreen implements Screen {
    private static final float BG_COLOR_RED = .3f;
    private static final float BG_COLOR_GREEN = .6f;
    private static final float BG_COLOR_BLUE = .7f;

    private BitmapFont font = new BitmapFont();
    private List<Processor> processors;
    private Stage stage = new Stage();
    private SpriteBatch batch;
    private boolean gameOver = false;
    private boolean victory;
    private float totalFPS = 0;
    private int totalFrames = 0;
    private float averageFPS;
    private float totalDiff = 0;
    private float avgDiff;

    public Stage getStage() {
        return stage;
    }

    @Override
    public void show() {
        processors = new LinkedList<Processor>();
        batch = new SpriteBatch();
        processors.add(new FactoryShipProcessor());
        processors.add(new CombatShipProcessor());
        processors.add(new BulletProcessor());
        processors.add(new CollisionProcessor());
        processors.add(new OpponentProcessor());
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        totalFPS += 1 / delta;
        totalFrames++;
        averageFPS = totalFPS / totalFrames;

        long startTime = System.nanoTime();

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
        for (Bullet bullet : PaxBritannicaGame.currentMatch.getBullets()) {
            bullet.draw(batch);
        }
        if (gameOver) {
            String endGameDisplayText = victory ? "Victory!" : "Defeat!";
            font.draw(batch, endGameDisplayText, getStage().getWidth() / 2, getStage().getHeight() / 2);
        }
        batch.end();

        long nanos = System.nanoTime() - startTime;
        long deltaNanos = (long) (delta * 1000000000);
        totalDiff += deltaNanos - nanos;
        System.out.println(totalDiff / totalFrames);
    }

    void endGame(boolean victory) {
        gameOver = true;
        this.victory = victory;
        System.out.println(averageFPS + " FPS");
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
