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

    void moveWithinRangeOfTarget(Ship target, float range, float delta) {
        ship.setRotation((float) MathUtil.angleBetweenPoints(ship.getX(), ship.getY(), target.getX(), target.getY())); //TODO: Rotation speed
        double distance = Point2D.distance(ship.getX(), ship.getY(), target.getX(), target.getY());
        Vector2 unitVector = null;
        if (distance > range) {
            unitVector = MathUtil.unitVector(ship.getRotation());
        } else if (distance <= range) {
            unitVector = MathUtil.unitVector((ship.getRotation() + 180) % 360);
        }
        ship.setPosition(
                ship.getX() + unitVector.x * ship.movementSpeed * delta,
                ship.getY() + unitVector.y * ship.movementSpeed * delta
        );
    }
}
