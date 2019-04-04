package com.sdu.abund14.master.paxbrit.bullet;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

public class BulletFactory extends BasePooledObjectFactory<Bullet> {

    int playerNumber;
    int damage;
    int speed;
    float startX;
    float startY;
    float angle;
    String texture = "laser"; //To prevent NullPointerException when creating idle objects

    @Override
    public Bullet create() {
        return new Bullet();
    }

    @Override
    public PooledObject<Bullet> wrap(Bullet obj) {
        return new DefaultPooledObject<Bullet>(obj);
    }

    @Override
    public void activateObject(PooledObject<Bullet> bullet) {
        bullet.getObject().activate(playerNumber, damage, speed, startX, startY, angle, texture);
    }

    @Override
    public boolean validateObject(PooledObject<Bullet> bullet) {
        return bullet.getObject().getPlayerNumber() != 0;
    }

    @Override
    public void passivateObject(PooledObject<Bullet> bullet) {
        bullet.getObject().reset();
    }
}
