package com.sdu.abund14.master.paxbrit;

import com.badlogic.gdx.Gdx;

public class Grid {
    public static final int NUM_CELLS = 10;
    private static final int CELL_SIZE_X = Gdx.graphics.getWidth() * 3  / NUM_CELLS;
    private static final int CELL_SIZE_Y = Gdx.graphics.getHeight() * 3 / NUM_CELLS;

    private GameEntity[][] cells = new GameEntity[NUM_CELLS][NUM_CELLS];

    public GameEntity[][] getCells() {
        return cells;
    }

    public void add(GameEntity entity) {
        int cellX = (int) entity.getX() / Grid.CELL_SIZE_X;
        int cellY = (int) entity.getY() / Grid.CELL_SIZE_Y;

        entity.setPrevious(null);
        entity.setNext(cells[cellX][cellY]);
        cells[cellX][cellY] = entity;

        if (entity.getNext() != null) {
            entity.getNext().setPrevious(entity);
        }
    }

    public void move(GameEntity entity, float x, float y) {
        //Old grid position
        int oldCellX = (int) (entity.getX() / CELL_SIZE_X);
        int oldCellY = (int) (entity.getY() / CELL_SIZE_Y);

        //New position
        int cellX = (int) (x / CELL_SIZE_X);
        int cellY = (int) (y / CELL_SIZE_Y);

        if (oldCellX == cellX && oldCellY == cellY) return;

        //Unlink from old cell's list
        if (entity.getPrevious() != null) {
            entity.getPrevious().setNext(entity.getNext());
        }
        if (entity.getNext() != null) {
            entity.getNext().setPrevious(entity.getPrevious());
        }

        //If head of a list, remove
        if (entity.equals(cells[oldCellX][oldCellY])) {
            cells[oldCellX][oldCellY] = entity.getNext();
        }

        //Re-add to new cell
        add(entity);
    }
}
