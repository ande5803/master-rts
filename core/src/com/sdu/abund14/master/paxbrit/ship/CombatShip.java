package com.sdu.abund14.master.paxbrit.ship;

import com.badlogic.gdx.math.Vector2;
import com.sdu.abund14.master.paxbrit.bullet.Bullet;
import com.badlogic.gdx.Gdx;
import com.sdu.abund14.master.paxbrit.ship.AI.CombatShipAI;
import com.sdu.abund14.master.paxbrit.util.MathUtil;

import java.awt.geom.Point2D;

public class CombatShip extends Ship {

    private static final float BULLET_OFFSET_DISTANCE = 30;

    public CombatShipAI ai;
    public float shotCooldownTimeLeft = 0;
    public float reloadTimeLeft = 0;
    public int ammoLeft = 0;

    //Values are injected by subclasses
    float turnSpeed = 0;
    float movementSpeed = 0;
    float fireRate = 0; //Shots per second
    int shotDamage = 0;
    float reloadTime = 0;
    int bulletSpeed = 0;
    String bulletTexture;

    private int magazineSize = 0;
    private double rangeDeviation = 30;
    private Vector2 facing;
    private Vector2 position = new Vector2(0,0);
    private Vector2 velocity = new Vector2(0,0);

    CombatShip(String type, int playerNumber, float x, float y) {
        super(playerNumber,type + "p" + playerNumber);
        position.x = x;
        position.y = y;
        setPosition(position.x, position.y);
        facing = new Vector2(1, 1);
    }

    private void turn(boolean clockwise) {
        float delta = Math.min(0.06f, Gdx.graphics.getDeltaTime());
        int direction = clockwise ? 1 : -1;
        facing.rotate(direction * turnSpeed * delta).nor();
        setRotation(facing.angle());
    }

    private void move(boolean forwards, float delta) {
        int direction = forwards ? 1 : -1;
        velocity.add(facing.x * movementSpeed * direction * delta, facing.y * movementSpeed * direction * delta);
        velocity.scl((float) Math.pow(0.97f, delta * 30f)); //TODO explain better
        position.add(velocity.x * delta, velocity.y * delta);
        setPosition(position.x, position.y);
    }

    /**
     * Move within a desired range of a target ship. Moves towards target
     * if too far away, or away from target if too close
     * @param target The target ship
     * @param range The desired distance to the target
     * @param delta delta time
     */
    public void engage(Ship target, float range, float delta) {
        Vector2 targetPosition = new Vector2(target.getX(), target.getY());
        Vector2 targetDirection = targetPosition.sub(position);
        //Turn to face target
        if (facing.crs(targetDirection) > 0) {
            turn(true);
        } else {
            turn(false);
        }

        //Move forwards or backwards to be within range
        double distance = Point2D.distance(getX(), getY(), target.getX(), target.getY());
        if (distance > range + rangeDeviation) {
            move(true, delta);
        } else if (distance <= range - rangeDeviation) {
            move(false, delta);
        } else if (isReadyToShoot()) {
            shoot();
        }
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

    private boolean isReadyToShoot() {
        return !reloading() && !coolingDown() && ammoLeft > 0;
    }

    private void shoot() {
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

    @Override
    public Vector2 getPosition() {
        return position;
    }

    @Override
    public boolean isOffScreen() {
        return !(position.x > 0)
                || !(position.x < Gdx.graphics.getWidth())
                || !(position.y > 0)
                || !(position.y < Gdx.graphics.getHeight());
    }
}
