package com.sdu.abund14.master.paxbrit.ship;

import com.sdu.abund14.master.paxbrit.ship.AI.BomberAI;

public class Bomber extends CombatShip {
    Bomber(int playerNumber, float x, float y) {
        super("bomber", playerNumber, x, y);
        ai = new BomberAI(this);
        type = ShipType.BOMBER;
        maxHealth = currentHealth = 440;
        movementSpeed = 60;
        turnSpeed = 75;
        setMagazineSize(4);
        fireRate = 1f;
        reloadTime = 5f;
        shotDamage = 300;
        bulletSpeed = 150;
        bulletTexture = "bomb";
    }
}
