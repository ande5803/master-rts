package com.sdu.abund14.master.paxbrit.bullet;


import org.apache.commons.pool2.impl.GenericObjectPool;

class BulletPoolImpl extends GenericObjectPool<Bullet> {

    private BulletFactory factory;

    BulletPoolImpl(BulletFactory factory, int size) {
        super(factory);
        this.factory = factory;
        setMaxTotal(size);
        setMaxIdle(size);
        setBlockWhenExhausted(false);
        setTestOnBorrow(true);
        for (int i = 0; i < size; i++) {
            try {
                addObject();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    Bullet borrow(int playerNumber, int damage, int speed, float startX, float startY, float angle, String texture) {
//        System.out.println(getCreatedCount());
//        System.out.println("B: " + getBorrowedCount());
//        System.out.println("R: " + getReturnedCount());
//        System.out.println("A: " + getNumActive());
//        System.out.println("I: " + getNumIdle());

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
