package com.sdu.abund14.master.paxbrit.ship;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.sdu.abund14.master.paxbrit.graphics.SpriteBuilder;

public class Ship extends Sprite {

    public Ship(String textureName ) {
        super(SpriteBuilder.get(textureName));
        setOrigin(getWidth() / 2, getHeight() / 2);
    }
}
