package com.sdu.abund14.master.paxbrit.bullet;

public class BulletPool {

    private final BulletPoolImpl pool;

    /**
     * Wrapper class to restrict access to the bullet pool implementation.
     */
    public BulletPool(int size) {
        pool = new BulletPoolImpl(new BulletFactory(), size);
    }

    public Bullet borrowBullet(int playerNumber, int damage, int speed, float startX, float startY, float angle, String texture) {
        return pool.borrow(playerNumber, damage, speed, startX, startY, angle, texture);
    }

    public void returnBullet(Bullet bullet) {
        pool.returnObject(bullet);
    }
}
