package com.sdu.abund14.master.paxbrit.ship;

import com.sdu.abund14.master.paxbrit.ship.AI.FrigateAI;

public class Frigate extends CombatShip {
    Frigate(int playerNumber, float x, float y) {
        super("frigate", playerNumber, x, y);
        ai = new FrigateAI(this);
        type = ShipType.FRIGATE;
        maxHealth = currentHealth = 2000 * 20;
        movementSpeed = 30;
        setMagazineSize(10);
        fireRate = 5;
        reloadTime = 5;
        shotDamage = 50;
        bulletSpeed = 100;
        bulletTexture = "missile";
        turnSpeed = 40;
    }
}
