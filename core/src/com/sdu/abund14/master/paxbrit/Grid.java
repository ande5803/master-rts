package com.sdu.abund14.master.paxbrit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;

import java.util.LinkedList;
import java.util.List;

public class Grid {
    public static final int NUM_CELLS = 10;
    public static final int CELL_SIZE_X = Gdx.graphics.getWidth() / NUM_CELLS;
    public static final int CELL_SIZE_Y = Gdx.graphics.getHeight() / NUM_CELLS;

    @SuppressWarnings("unchecked")
    private List<GameEntity>[][] cells = new LinkedList[NUM_CELLS][NUM_CELLS];

    public Grid() {
        for (int x = 0; x < NUM_CELLS; x++) {
            for (int y = 0; y < NUM_CELLS; y++) {
                cells[x][y] = new LinkedList<GameEntity>();
            }
        }
    }

    public List<GameEntity>[][] getCells() {
        return cells;
    }

    public void add(GameEntity entity) {
        for (int x = 0; x < NUM_CELLS; x++) {
            for (int y = 0; y < NUM_CELLS; y++) {
                if (isCollisionPolygonInCell(entity.getCollisionPolygon(), x, y)) {
                    cells[x][y].add(0, entity);
                }
            }
        }
    }

    public void move(GameEntity entity, float x, float y) {
        if (entity.getX() == 0 && entity.getY() == 0) return;

        for (int cellX = 0; cellX < NUM_CELLS; cellX++) {
            for (int cellY = 0; cellY < NUM_CELLS; cellY++) {
                Polygon translatedCollisionPolygon = new Polygon(entity.getCollisionPolygon().getTransformedVertices());
                translatedCollisionPolygon.translate(x - entity.getX(), y - entity.getY());

                if (isCollisionPolygonInCell(entity.getCollisionPolygon(), cellX, cellY)) {
                    if (!isCollisionPolygonInCell(translatedCollisionPolygon, cellX, cellY)) {
                        cells[cellX][cellY].remove(entity); //Entity is no longer in cell
                    }
                } else {
                    if (isCollisionPolygonInCell(translatedCollisionPolygon, cellX, cellY)) {
                        cells[cellX][cellY].add(0, entity); //Entity just entered this cell
                    }
                }
            }
        }
    }

    private boolean isCollisionPolygonInCell(Polygon polygon, int cellX, int cellY) {
        float[] cellVertices = new float[] {
                cellX * CELL_SIZE_X, cellY * CELL_SIZE_Y,
                cellX * CELL_SIZE_X, cellY * CELL_SIZE_Y + CELL_SIZE_Y,
                cellX * CELL_SIZE_X + CELL_SIZE_X, cellY * CELL_SIZE_Y + CELL_SIZE_Y,
                cellX * CELL_SIZE_X + CELL_SIZE_X, cellY * CELL_SIZE_Y
        };
        return Intersector.overlapConvexPolygons(polygon.getTransformedVertices(), cellVertices, null);
    }
}
