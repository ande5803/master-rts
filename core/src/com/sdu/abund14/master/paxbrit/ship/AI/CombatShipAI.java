package com.sdu.abund14.master.paxbrit.ship.AI;

import com.badlogic.gdx.math.Vector2;
import com.sdu.abund14.master.paxbrit.ship.CombatShip;
import com.sdu.abund14.master.paxbrit.ship.Ship;
import com.sdu.abund14.master.paxbrit.util.MathUtil;

import java.awt.geom.Point2D;

public abstract class CombatShipAI {

    CombatShip ship;
    Ship target;

    CombatShipAI(CombatShip ship) {
        this.ship = ship;
    }

    public abstract void update(float delta);

    void engage(Ship target, float range, float delta) {
        //Point towards target and calculate distance
        ship.setRotation((float) MathUtil.angleBetweenPoints(ship.getX(), ship.getY(), target.getX(), target.getY())); //TODO: Rotation speed
        double distance = Point2D.distance(ship.getX(), ship.getY(), target.getX(), target.getY());

        //Determine whether to move towards or away from target
        Vector2 unitVector = null;
        float rangeDeviation = 30;
        if (distance > range + rangeDeviation) {
            unitVector = MathUtil.unitVector(ship.getRotation());
        } else if (distance < range - rangeDeviation) {
            unitVector = MathUtil.unitVector((ship.getRotation() + 180) % 360);
        } else {
            if (ship.isReadyToShoot()) ship.shoot();
        }

        //Move
        if (unitVector != null) {
            ship.setPosition(
                    ship.getX() + unitVector.x * ship.movementSpeed * delta,
                    ship.getY() + unitVector.y * ship.movementSpeed * delta
            );
        }
    }
}
