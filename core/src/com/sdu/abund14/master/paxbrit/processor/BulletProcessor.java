package com.sdu.abund14.master.paxbrit.processor;

import com.badlogic.gdx.Gdx;
import com.sdu.abund14.master.paxbrit.PaxBritannicaGame;
import com.sdu.abund14.master.paxbrit.bullet.Bullet;
import com.sdu.abund14.master.paxbrit.interfaces.Processor;

import java.util.ListIterator;

public class BulletProcessor implements Processor {

    public void process(float delta) {
        ListIterator<Bullet> bulletIterator = PaxBritannicaGame.currentMatch.getBullets().listIterator();
        while (bulletIterator.hasNext()) {
            Bullet bullet = bulletIterator.next();
            if (!bullet.isAlive() && bullet.getPlayerNumber() != 0) {
                PaxBritannicaGame.currentMatch.getBulletPool().returnBullet(bullet);
                bulletIterator.remove();
                continue;
            }
            //Propagate forward
            bullet.setPosition(
                    (float) (bullet.getX() + Math.cos(Math.toRadians(bullet.getRotation())) * bullet.getSpeed() * delta),
                    (float) (bullet.getY() + Math.sin(Math.toRadians(bullet.getRotation())) * bullet.getSpeed() * delta)
            );
            //Remove if out of bounds
            if (bullet.getX() < 0 ||
                bullet.getX() > Gdx.graphics.getWidth() ||
                bullet.getY() < 0 ||
                bullet.getY() > Gdx.graphics.getHeight()) {
                bullet.destroy();
            }
        }
    }
}
