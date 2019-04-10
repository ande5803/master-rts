package com.sdu.abund14.master.paxbrit;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;

public class GameEntity extends Sprite {
    private Polygon collisionPolygon;
    private GameEntity prev = null;
    private GameEntity next = null;

    public GameEntity(TextureRegion region, Grid grid, float x, float y) {
        super(region);

        collisionPolygon = new Polygon(new float[] {
                0, 0,
                region.getRegionWidth(), 0,
                region.getRegionWidth(), region.getRegionHeight(),
                0, region.getRegionHeight()
        });
        collisionPolygon.setOrigin(region.getRegionWidth() / 2, region.getRegionHeight() / 2);
        setPosition(x, y);
        grid.add(this);
    }

    public Polygon getCollisionPolygon() {
        return collisionPolygon;
    }

    public GameEntity getPrevious() {
        return prev;
    }

    public void setPrevious(GameEntity prev) {
        this.prev = prev;
    }

    public GameEntity getNext() {
        return next;
    }

    public void setNext(GameEntity next) {
        this.next = next;
    }

    @Override
    public void setPosition(float x, float y) {
        PaxBritannicaGame.currentMatch.getGrid().move(this, x, y);
        super.setPosition(x, y);
        collisionPolygon.setPosition(x, y);
    }

    @Override
    public void setRotation(float degrees) {
        super.setRotation(degrees);
        collisionPolygon.setRotation(degrees);
    }
}
