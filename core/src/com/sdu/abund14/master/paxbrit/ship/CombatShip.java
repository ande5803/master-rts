package com.sdu.abund14.master.paxbrit.ship;

import com.sdu.abund14.master.paxbrit.ship.AI.CombatShipAI;

public class CombatShip extends Ship {

    public CombatShipAI ai;
    public float movementSpeed = 0;
    public int magazineSize = 0;
    public float fireRate = 0;
    public float reloadTime = 0;

    CombatShip(String type, int playerNumber, float x, float y) {
        super(playerNumber,type + "p" + playerNumber);
        setPosition(x, y);
    }
}
