package com.sdu.abund14.master.paxbrit.ship;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.sdu.abund14.master.paxbrit.PaxBritannicaGame;
import com.sdu.abund14.master.paxbrit.graphics.TextureRegionProvider;

public class Ship extends Sprite {

    private int playerNumber;
    ShipType type;

    Ship(int playerNumber, String textureName ) {
        super(TextureRegionProvider.getInstance().get(textureName));
        this.playerNumber = playerNumber;
        PaxBritannicaGame.currentMatch.addShip(this);
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public void destroy() {
        PaxBritannicaGame.currentMatch.removeShip(this);
    }
}
