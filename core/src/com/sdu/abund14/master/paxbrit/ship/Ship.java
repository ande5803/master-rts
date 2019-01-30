package com.sdu.abund14.master.paxbrit.ship;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.sdu.abund14.master.paxbrit.graphics.TextureRegionProvider;

public class Ship extends Sprite {

    public Ship(String textureName ) {
        super(TextureRegionProvider.get(textureName));
        setOrigin(getWidth() / 2, getHeight() / 2);
    }

    @Override
    public void draw(Batch batch, float alpha) {
        super.draw(batch, alpha);
    }
}
