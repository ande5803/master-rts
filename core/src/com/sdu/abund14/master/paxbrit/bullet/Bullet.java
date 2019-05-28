package com.sdu.abund14.master.paxbrit.bullet;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.sdu.abund14.master.paxbrit.NautilusGame;
import com.sdu.abund14.master.paxbrit.graphics.TextureRegionProvider;

public class Bullet extends Sprite {

    private int playerNumber;
    private int damage;
    private int speed;
    private boolean alive = true;

    Bullet(){}

    public int getPlayerNumber() {
        return playerNumber;
    }

    public int getDamage() {
        return damage;
    }

    public int getSpeed() {
        return speed;
    }

    public boolean isAlive() { return alive; }

    public void destroy() {
        alive = false;
    }

    //For passivating objects when returning to pool
    void reset() {
        destroy();
        playerNumber = 0;
        damage = 0;
        speed = 0;
        setPosition(0,0);
        setRotation(0);
        setRegion(TextureRegionProvider.getInstance().get("laser"));
    }

    void activate(int playerNumber, int damage, int speed, float startX, float startY, float angle, String texture) {
        alive = true;
        this.playerNumber = playerNumber;
        this.damage = damage;
        this.speed = speed;
        setPosition(startX, startY);
        setRotation(angle);
        setRegion(TextureRegionProvider.getInstance().get(texture));
        setSize(getRegionWidth(), getRegionHeight());
        NautilusGame.currentMatch.addBullet(this);
    }
}
