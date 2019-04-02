package com.sdu.abund14.master.paxbrit.bullet;


import org.apache.commons.pool2.impl.AbandonedConfig;
import org.apache.commons.pool2.impl.GenericObjectPool;

class BulletPoolImpl extends GenericObjectPool<Bullet> {

    private static final int MAX_BULLETS = 150;
    private BulletFactory factory;

    BulletPoolImpl(BulletFactory factory) {
        super(factory);
        this.factory = factory;
        setMaxTotal(MAX_BULLETS);
        AbandonedConfig abandonedConfig = new AbandonedConfig();
        abandonedConfig.setRemoveAbandonedOnBorrow(true);
        abandonedConfig.setRemoveAbandonedTimeout(10000);
        try {
            for (int i = 0; i < MAX_BULLETS; i++) {
                addObject();
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    Bullet borrow(int playerNumber, int damage, int speed, float startX, float startY, float angle, String texture) {
        factory.playerNumber = playerNumber;
        factory.damage = damage;
        factory.speed = speed;
        factory.startX = startX;
        factory.startY = startY;
        factory.angle = angle;
        factory.texture = texture;
        try {
            return borrowObject();
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }
}
