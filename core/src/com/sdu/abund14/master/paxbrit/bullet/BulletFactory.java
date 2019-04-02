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
    String texture;

    @Override
    public Bullet create() throws Exception {
        return new Bullet(playerNumber, damage, speed, startX, startY, angle, texture);
    }

    @Override
    public PooledObject<Bullet> wrap(Bullet obj) {
        return new DefaultPooledObject<Bullet>(obj);
    }


}
