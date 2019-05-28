package com.sdu.abund14.master.paxbrit;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;

import java.util.UUID;

public class GameEntity extends Sprite {
    private Polygon collisionPolygon;
    private UUID id = UUID.randomUUID();
    private int playerNumber;
    private boolean alive = true;
    private Grid grid;

    public GameEntity(int playerNumber, TextureRegion region, Grid grid, float x, float y) {
        super(region);
        this.playerNumber = playerNumber;
        this.grid = grid;

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

    public int getPlayerNumber() {
        return playerNumber;
    }

    public UUID getId() {
        return id;
    }

    public Polygon getCollisionPolygon() {
        return collisionPolygon;
    }

    public boolean isDead() {
        return !alive;
    }

    public void destroy() {
        alive = false;
        grid.remove(this);
    }

    @Override
    public void setPosition(float x, float y) {
        NautilusGame.currentMatch.getGrid().move(this, x, y);
        super.setPosition(x, y);
        collisionPolygon.setPosition(x, y);
    }

    @Override
    public void setRotation(float degrees) {
        super.setRotation(degrees);
        collisionPolygon.setRotation(degrees);
    }
}
