package com.sdu.abund14.master.paxbrit.processor;

import com.badlogic.gdx.Gdx;
import com.sdu.abund14.master.paxbrit.GameSettings;
import com.sdu.abund14.master.paxbrit.NautilusGame;
import com.sdu.abund14.master.paxbrit.interfaces.Processor;
import com.sdu.abund14.master.paxbrit.ship.FactoryShip;


public class FactoryShipProcessor implements Processor {

    private static final float RADIUS = 200;
    private static final float SHIP_SPEED = 3;
    private static final int LEFT = 0;
    private static final int UP = 90;
    private static final int RIGHT = 180;
    private static final int DOWN = 270;

    private FactoryShip p1factory;
    private FactoryShip p2factory;
    private FactoryShip p3factory;
    private FactoryShip p4factory;

    public FactoryShipProcessor() {
        init();
    }

    private void init() {
        createSprites();
        setStartPositions(GameSettings.numPlayers);
    }

    private void createSprites() {
        p1factory = new FactoryShip("factoryp1", false);
        p2factory = new FactoryShip("factoryp2", false);
        if (GameSettings.numPlayers > 2) {
            p3factory = new FactoryShip("factoryp3", false);
        }
        if (GameSettings.numPlayers > 3) {
            p4factory = new FactoryShip("factoryp4", false);
        }
    }

    private void setStartPositions(int numPlayers) {
        p1factory.setOriginBasedPosition(middleWidth() - RADIUS, middleHeight()); //Left
        p1factory.setRotation(DOWN);
        switch (numPlayers) {
            case 2:
                p2factory.setOriginBasedPosition(middleWidth() + RADIUS, middleHeight()); //Right
                p2factory.setRotation(UP);
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
                p2factory.setRotation(RIGHT);
                p3factory.setOriginBasedPosition(middleWidth() + RADIUS, middleHeight()); //Right
                p3factory.setRotation(UP);
                p4factory.setOriginBasedPosition(middleWidth(), middleHeight() - RADIUS); //Bottom
                p4factory.setRotation(LEFT);
                break;

            default:
                System.out.println("Illegal player count: " + GameSettings.numPlayers);
                Gdx.app.exit();
                break;
        }
    }

    @Override
    public void process(float delta) {
        for (FactoryShip ship : NautilusGame.currentMatch.getFactories()) {
            if (ship.isDead()) {
                NautilusGame.currentMatch.getFactories().remove(ship);
                continue;
            }

            //Add and subtract 90 degrees to point ships toward circle perimeter while rotating
            float newAngle = ship.getRotation() + (SHIP_SPEED * delta) - 90;
            ship.setRotation(newAngle + 90);
            //Move in a counter-clockwise circular path
            ship.setOriginBasedPosition(
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
}
