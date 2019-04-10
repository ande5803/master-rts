package com.sdu.abund14.master.paxbrit.bullet;

import com.sdu.abund14.master.paxbrit.GameEntity;
import com.sdu.abund14.master.paxbrit.Grid;
import com.sdu.abund14.master.paxbrit.PaxBritannicaGame;
import com.sdu.abund14.master.paxbrit.graphics.TextureRegionProvider;

public class Bullet extends GameEntity {

    private int playerNumber;
    private int damage;
    private int speed;
    private boolean alive = true;

    public Bullet(int playerNumber, int damage, int speed, Grid grid, float startX, float startY, float angle, String texture) {
        super(TextureRegionProvider.getInstance().get(texture), grid, startX, startY);
        this.playerNumber = playerNumber;
        this.damage = damage;
        this.speed = speed;
        setPosition(startX, startY);
        setRotation(angle);
        PaxBritannicaGame.currentMatch.addBullet(this);
    }

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

    public void destroy() { alive = false; }
}
