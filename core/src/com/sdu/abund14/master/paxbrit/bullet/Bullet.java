package com.sdu.abund14.master.paxbrit.bullet;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.sdu.abund14.master.paxbrit.PaxBritannicaGame;
import com.sdu.abund14.master.paxbrit.graphics.TextureRegionProvider;

public class Bullet extends Sprite {

    private int owner;
    private int damage;
    private int speed;
    public boolean alive = true;

    public Bullet(int owner, int damage, int speed, float startX, float startY, float angle, String texture) {
        super(TextureRegionProvider.getInstance().get(texture));
        this.owner = owner;
        this.damage = damage;
        this.speed = speed;
        setPosition(startX, startY);
        setRotation(angle);
        PaxBritannicaGame.currentMatch.addBullet(this);
    }

    public int getOwner() {
        return owner;
    }

    public int getDamage() {
        return damage;
    }

    public int getSpeed() {
        return speed;
    }


}
