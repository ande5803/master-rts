package com.sdu.abund14.master.paxbrit.ship;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.sdu.abund14.master.paxbrit.GameEntity;
import com.sdu.abund14.master.paxbrit.Grid;
import com.sdu.abund14.master.paxbrit.NautilusGame;
import com.sdu.abund14.master.paxbrit.graphics.TextureRegionProvider;

public class Ship extends GameEntity {

    ShipType type;
    int maxHealth;
    int currentHealth;
    private boolean alive = true;

    Ship(int playerNumber, String textureName, Grid grid, float x, float y) {
        super(playerNumber, TextureRegionProvider.getInstance().get(textureName), grid, x, y);
        NautilusGame.currentMatch.addShip(this);
    }

    public Vector2 getPosition() {
        return new Vector2(getX() + getWidth() / 2, getY() + getHeight() / 2);
    }

    public void takeDamage(int amount) {
        currentHealth -= amount;
        if (currentHealth <= 0) {
            alive = false;
            destroy();
        }
    }

    public boolean isOffScreen() {
        return getX() < 0
                || getX() > Gdx.graphics.getWidth()
                || getY() < 0
                || getY() > Gdx.graphics.getHeight();
    }
}
