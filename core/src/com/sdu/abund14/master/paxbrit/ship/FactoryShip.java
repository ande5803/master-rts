package com.sdu.abund14.master.paxbrit.ship;

import com.badlogic.gdx.math.Circle;

public class FactoryShip extends Ship {

    private Circle productionCircle;

    public FactoryShip(String textureName) {
        super(textureName);
        productionCircle = new Circle(getOriginX(), getOriginY(), getWidth() / 2);
    }

    public Circle getProductionCircle() {
        return productionCircle;
    }
}
