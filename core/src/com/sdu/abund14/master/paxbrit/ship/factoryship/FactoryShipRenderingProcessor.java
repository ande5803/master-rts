package com.sdu.abund14.master.paxbrit.ship.factoryship;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.sdu.abund14.master.paxbrit.GameSettings;
import com.sdu.abund14.master.paxbrit.interfaces.Processor;

import java.util.LinkedList;
import java.util.List;

public class FactoryShipRenderingProcessor implements Processor {

    private static final float RADIUS = 200;
    private static final float SHIP_SPEED = 3;

    private SpriteBatch batch;
    private Sprite p1factory;
    private Sprite p2factory;
    private Sprite p3factory;
    private Sprite p4factory;
    private List<Sprite> sprites;

    private void init() {
        batch = new SpriteBatch();
        sprites = new LinkedList<Sprite>();
        createSprites();
        setStartPositions(GameSettings.numPlayers);
    }

    private void createSprites() {
        p1factory = new Sprite(new Texture(Gdx.files.internal("factoryp1.png")));
        p2factory = new Sprite(new Texture(Gdx.files.internal("factoryp2.png")));
        p1factory.setOrigin(p1factory.getWidth() / 2, p1factory.getHeight() / 2);
        p2factory.setOrigin(p2factory.getWidth() / 2, p2factory.getHeight() / 2);
        sprites.add(p1factory);
        sprites.add(p2factory);
        if (GameSettings.numPlayers > 2) {
            p3factory = new Sprite(new Texture(Gdx.files.internal("factoryp3.png")));
            p3factory.setOrigin(p3factory.getWidth() / 2, p3factory.getHeight() / 2);
            sprites.add(p3factory);
        }
        if (GameSettings.numPlayers > 3) {
            p4factory = new Sprite(new Texture(Gdx.files.internal("factoryp4.png")));
            p4factory.setOrigin(p4factory.getWidth() / 2, p4factory.getHeight() / 2);
            sprites.add(p4factory);
        }
    }

    private void setStartPositions(int numPlayers) {
        p1factory.setOriginBasedPosition(middleWidth() - RADIUS, middleHeight()); //Left
        p1factory.setRotation(270);
        switch (numPlayers) {
            case 2:
                p2factory.setOriginBasedPosition(middleWidth() + RADIUS, middleHeight()); //Right
                p2factory.setRotation(90);
                break;

            case 3:
                //Three ships are placed with 120 degrees between them on the circle
                p2factory.setOriginBasedPosition(
                        (float) (middleWidth() + (Math.cos(Math.toRadians(60)) * RADIUS)),
                        (float) (middleHeight() - (Math.sin(Math.toRadians(60)) * RADIUS))
                );
                p2factory.setRotation(30);
                p3factory.setOriginBasedPosition(
                        (float) (middleWidth() + (Math.cos(Math.toRadians(60)) * RADIUS)),
                        (float) (middleHeight() + (Math.sin(Math.toRadians(60)) * RADIUS))
                );
                p3factory.setRotation(150);
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
                Gdx.app.exit();
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
        float delta = Gdx.graphics.getDeltaTime();
        for (Sprite sprite : sprites) {
            //Add and subtract 90 degrees to point ships toward circle perimeter while rotating
            float newAngle = sprite.getRotation() + (SHIP_SPEED * delta) - 90;
            sprite.setRotation(newAngle + 90);
            sprite.setOriginBasedPosition(
                    (float) (middleWidth() + Math.cos(Math.toRadians(newAngle)) * RADIUS),
                    (float) (middleHeight() + Math.sin(Math.toRadians(newAngle)) * RADIUS)
            );
        }
    }

    private int middleWidth() {
        return Gdx.graphics.getWidth() / 2;
    }

    private int middleHeight() {
        return Gdx.graphics.getHeight() / 2;
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
