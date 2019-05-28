package com.sdu.abund14.master.paxbrit.bullet;

import com.sdu.abund14.master.paxbrit.GameEntity;
import com.sdu.abund14.master.paxbrit.Grid;
import com.sdu.abund14.master.paxbrit.NautilusGame;
import com.sdu.abund14.master.paxbrit.graphics.TextureRegionProvider;

public class Bullet extends GameEntity {

    private int damage;
    private int speed;

    public Bullet(int playerNumber, int damage, int speed, Grid grid, float startX, float startY, float angle, String texture) {
        super(playerNumber, TextureRegionProvider.getInstance().get(texture), grid, startX, startY);
        this.damage = damage;
        this.speed = speed;
        setPosition(startX, startY);
        setRotation(angle);
        NautilusGame.currentMatch.addBullet(this);
    }

    public int getDamage() {
        return damage;
    }

    public int getSpeed() {
        return speed;
    }

}
