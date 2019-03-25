package com.sdu.abund14.master.paxbrit.processor;

import com.badlogic.gdx.Gdx;
import com.sdu.abund14.master.paxbrit.PaxBritannicaGame;
import com.sdu.abund14.master.paxbrit.bullet.Bullet;
import com.sdu.abund14.master.paxbrit.interfaces.Processor;

import java.util.ListIterator;

public class BulletProcessor implements Processor {

    public void process(float delta) {
        for (Bullet bullet : PaxBritannicaGame.currentMatch.getBullets()) {
            if (!bullet.isAlive()) {
                PaxBritannicaGame.currentMatch.getBullets().remove(bullet);
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
