package com.sdu.abund14.master.paxbrit.ship;

import com.badlogic.gdx.math.Vector2;
import com.sdu.abund14.master.paxbrit.bullet.Bullet;
import com.sdu.abund14.master.paxbrit.ship.AI.CombatShipAI;
import com.sdu.abund14.master.paxbrit.util.MathUtil;

public class CombatShip extends Ship {

    private static final float BULLET_OFFSET_DISTANCE = 30;

    public CombatShipAI ai;
    public float movementSpeed = 0;
    public float shotCooldownTimeLeft = 0;
    public float reloadTimeLeft = 0;
    public int ammoLeft = 0;

    float fireRate = 0; //Shots per second
    int shotDamage = 0;
    float reloadTime = 0;
    int bulletSpeed = 0;
    String bulletTexture;

    private int magazineSize = 0;

    CombatShip(String type, int playerNumber, float x, float y) {
        super(playerNumber,type + "p" + playerNumber);
        setPosition(x, y);
    }

    void setMagazineSize(int size) {
        magazineSize = size;
        ammoLeft = size;
    }

    private boolean reloading() {
        return reloadTimeLeft > 0;
    }

    private boolean coolingDown() {
        return shotCooldownTimeLeft > 0;
    }

    public boolean isReadyToShoot() {
        return !reloading() && !coolingDown() && ammoLeft > 0;
    }

    public void shoot() {
        Vector2 bulletPosition = MathUtil.offsetVector(getX(), getY(), getRotation(), BULLET_OFFSET_DISTANCE);
        new Bullet(getPlayerNumber(), shotDamage, bulletSpeed, bulletPosition.x, bulletPosition.y, getRotation(), bulletTexture);
        shotCooldownTimeLeft = 1 / fireRate;
        ammoLeft -= 1;
        if (ammoLeft <= 0) reloadTimeLeft = reloadTime;
    }

    public void reload() {
        ammoLeft = magazineSize;
        reloadTimeLeft = 0;
    }
}
