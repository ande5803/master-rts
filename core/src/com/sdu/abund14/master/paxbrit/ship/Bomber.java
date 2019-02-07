package com.sdu.abund14.master.paxbrit.ship;

import com.sdu.abund14.master.paxbrit.ship.AI.BomberAI;

public class Bomber extends CombatShip {
    Bomber(int playerNumber, float x, float y) {
        super("bomber", playerNumber, x, y);
        ai = new BomberAI(this);
        type = ShipType.BOMBER;
        movementSpeed = 45;
    }
}
