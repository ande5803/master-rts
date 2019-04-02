package com.sdu.abund14.master.paxbrit.bullet;

public class BulletPool {

    private final BulletPoolImpl pool;

    /**
     * Wrapper object to restrict access to the actual pool object.
     */
    public BulletPool() {
        pool = new BulletPoolImpl(new BulletFactory());
    }

    public Bullet borrowBullet(int playerNumber, int damage, int speed, float startX, float startY, float angle, String texture) {
        return pool.borrow(playerNumber, damage, speed, startX, startY, angle, texture);
    }

    public void returnBullet(Bullet bullet) {
        pool.returnObject(bullet);
    }
}
