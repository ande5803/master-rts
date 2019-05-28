package com.sdu.abund14.master.paxbrit.ship;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.sdu.abund14.master.paxbrit.NautilusGame;
import com.sdu.abund14.master.paxbrit.graphics.TextureRegionProvider;

public class Ship extends Sprite {

    private int playerNumber;
    ShipType type;
    int maxHealth;
    int currentHealth;
    private boolean alive = true;

    Ship(int playerNumber, String textureName ) {
        super(TextureRegionProvider.getInstance().get(textureName));
        this.playerNumber = playerNumber;
        NautilusGame.currentMatch.addShip(this);
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public boolean isDead() {
        return !alive;
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

    //Override to do extra things on destruction
    public void destroy() {}
}
