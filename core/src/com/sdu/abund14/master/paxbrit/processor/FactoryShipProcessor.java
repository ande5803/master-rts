package com.sdu.abund14.master.paxbrit.processor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.sdu.abund14.master.paxbrit.GameSettings;
import com.sdu.abund14.master.paxbrit.Grid;
import com.sdu.abund14.master.paxbrit.PaxBritannicaGame;
import com.sdu.abund14.master.paxbrit.interfaces.Processor;
import com.sdu.abund14.master.paxbrit.ship.FactoryShip;

import java.util.ListIterator;

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
    private Grid grid;

    public FactoryShipProcessor() {
        init();
    }

    private void init() {
        grid = PaxBritannicaGame.currentMatch.getGrid();
        createShips(GameSettings.numPlayers);
    }

    private void createShips(int numPlayers) {
        Vector2 p1; //Positions
        Vector2 p2 = null;
        Vector2 p3 = null;
        Vector2 p4 = null;
        float r1; //Rotations
        float r2 = 0;
        float r3 = 0;
        float r4 = 0;

        p1 = new Vector2(middleWidth() - RADIUS, middleHeight()); //Left
        r1 = DOWN;

        //Place ships opposite each other dependent on player count
        switch (numPlayers) {
            case 2:
                p2 = new Vector2(middleWidth() + RADIUS, middleHeight()); //Right
                r2 = UP;
                break;

            case 3:
                //Three ships are placed with 120 degrees between them on the circle
                p2 = new Vector2(
                        (float) (middleWidth() + (Math.cos(Math.toRadians(60)) * RADIUS)),
                        (float) (middleHeight() - (Math.sin(Math.toRadians(60)) * RADIUS))
                );
                r2 = 30;
                p3 = new Vector2(
                        (float) (middleWidth() + (Math.cos(Math.toRadians(60)) * RADIUS)),
                        (float) (middleHeight() + (Math.sin(Math.toRadians(60)) * RADIUS))
                );
                r3 = 150;
                break;

            case 4:
                p2 = new Vector2(middleWidth(), middleHeight() + RADIUS); //Top
                r2 = RIGHT;
                p3 = new Vector2(middleWidth() + RADIUS, middleHeight()); //Right
                r3 = UP;
                p4 = new Vector2(middleWidth(), middleHeight() - RADIUS); //Bottom
                r4 = LEFT;
                break;

            default:
                System.out.println("Illegal player count: " + GameSettings.numPlayers);
                Gdx.app.exit();
                break;
        }

        p1factory = new FactoryShip("factoryp1", false, grid, p1.x, p1.y);
        p2factory = new FactoryShip("factoryp2", false, grid, p2.x, p2.y);
        p1factory.setRotation(r1);
        p2factory.setRotation(r2);
        if (GameSettings.numPlayers > 2) {
            p3factory = new FactoryShip("factoryp3", false, grid, p3.x, p3.y);
            p3factory.setRotation(r3);
        }
        if (GameSettings.numPlayers > 3) {
            p4factory = new FactoryShip("factoryp4", false, grid, p4.x, p4.y);
            p4factory.setRotation(r4);
        }
    }

    @Override
    public void process(float delta) {
        ListIterator<FactoryShip> iterator = PaxBritannicaGame.currentMatch.getFactories().listIterator();
        while (iterator.hasNext()) {
            FactoryShip ship = iterator.next();
            if (ship.isDead()) {
                iterator.remove();
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
