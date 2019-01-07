package com.sdu.abund14.master.paxbrit.ship.factoryship;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.sdu.abund14.master.paxbrit.GameSettings;
import com.sdu.abund14.master.paxbrit.interfaces.Processor;

public class FactoryShipRenderingProcessor implements Processor {

    private static final float RADIUS = 200;

    private SpriteBatch batch;
    private Sprite p1factory;
    private Sprite p2factory;
    private Sprite p3factory;
    private Sprite p4factory;

    private void init() {
        batch = new SpriteBatch();

        p1factory = new Sprite(new Texture(Gdx.files.internal("factoryp1.png")));
        p2factory = new Sprite(new Texture(Gdx.files.internal("factoryp2.png")));
        p1factory.setOrigin(p1factory.getWidth() / 2, p1factory.getHeight() / 2);
        p2factory.setOrigin(p2factory.getWidth() / 2, p2factory.getHeight() / 2);
        if (GameSettings.numPlayers > 2) {
            p3factory = new Sprite(new Texture(Gdx.files.internal("factoryp3.png")));
            p3factory.setOrigin(p3factory.getWidth() / 2, p3factory.getHeight() / 2);
        }
        if (GameSettings.numPlayers > 3) {
            p4factory = new Sprite(new Texture(Gdx.files.internal("factoryp4.png")));
            p4factory.setOrigin(p4factory.getWidth() / 2, p4factory.getHeight() / 2);
        }

        p1factory.setOriginBasedPosition(middleWidth() - RADIUS, middleHeight()); //Left
        p1factory.setRotation(270);
        switch (GameSettings.numPlayers) {
            case 2:
                p2factory.setOriginBasedPosition(middleWidth() + RADIUS, middleHeight()); //Right
                p2factory.setRotation(90);
                break;

            case 3: //FIXME
                //Three ships are placed with 120 degrees between them on the circle
                p2factory.setOriginBasedPosition((float) (middleWidth() + Math.abs(Math.cos(60) * RADIUS)), (float) (middleHeight() - Math.abs(Math.sin(60) * RADIUS)));
                p2factory.setRotation(60);
                p3factory.setOriginBasedPosition((float) (middleWidth() + Math.abs(Math.cos(60) * RADIUS)), (float) (middleHeight() + Math.abs(Math.sin(60) * RADIUS)));
                p3factory.setRotation(120);
                break;

            case 4:
                p2factory.setOriginBasedPosition(middleWidth(), middleHeight() + RADIUS); //Top
                p2factory.setRotation(180);
                p3factory.setOriginBasedPosition(middleWidth() + RADIUS, middleHeight()); //Right
                p3factory.setRotation(90);
                p4factory.setOriginBasedPosition(middleWidth(), middleHeight() - RADIUS); //Bottom
                p4factory.setRotation(0);
                break;

            default:
                System.out.println("Illegal player count: " + GameSettings.numPlayers);
                break;
        }

    }

    @Override
    public void process() {
        if (p1factory != null) {
            tick();
        } else {
            init();
        }
        batch.begin();
        p1factory.draw(batch);
        p2factory.draw(batch);
        if (GameSettings.numPlayers > 2) p3factory.draw(batch);
        if (GameSettings.numPlayers > 3) p4factory.draw(batch);
        batch.end();
    }

    private void tick() {

    }

    private int middleWidth() {
        return Gdx.graphics.getWidth() / 2;
    }

    private int middleHeight() {
        return Gdx.graphics.getHeight() / 2;
    }


}
