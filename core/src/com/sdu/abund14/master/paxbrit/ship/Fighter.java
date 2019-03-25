package com.sdu.abund14.master.paxbrit.ship;

import com.sdu.abund14.master.paxbrit.ship.AI.FighterAI;

public class Fighter extends CombatShip {

    Fighter(int playerNumber, float x, float y) {
        super("fighter", playerNumber, x, y);
        ai = new FighterAI(this);
        type = ShipType.FIGHTER;
        maxHealth = currentHealth = 40;
        movementSpeed = 80;
        setMagazineSize(5);
        fireRate = 2;
        reloadTime = 3f;
        shotDamage = 10;
        bulletSpeed = 1000;
        bulletTexture = "laser";
        turnSpeed = 120;
    }
}
