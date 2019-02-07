package com.sdu.abund14.master.paxbrit.ship;

import com.sdu.abund14.master.paxbrit.ship.AI.FighterAI;

public class Fighter extends CombatShip {

    Fighter(int playerNumber, float x, float y) {
        super("fighter", playerNumber, x, y);
        ai = new FighterAI(this);
        type = ShipType.FIGHTER;
        movementSpeed = 80;
    }
}
