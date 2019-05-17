package com.sdu.abund14.master.paxbrit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;

import java.util.LinkedList;
import java.util.List;

public class Grid {
    public static final int NUM_CELLS = 5;
    private static final int CELL_SIZE_X = Gdx.graphics.getWidth() / NUM_CELLS;
    private static final int CELL_SIZE_Y = Gdx.graphics.getHeight() / NUM_CELLS;

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


    //FIXME: Possible reason for bug where some ships are not hit by bullets!
    //Entities occupying more than one cell are added to more than one linked list,
    //could be that they are not recognized by the same ID and therefore do not
    //exist in the list data structures! (Pointer stuff)
    public void add(GameEntity entity) {
        for (int x = 0; x < NUM_CELLS; x++) {
            for (int y = 0; y < NUM_CELLS; y++) {
                if (isPolygonInCell(entity.getCollisionPolygon(), x, y)) {
                    cells[x][y].add(0, entity);
                }
            }
        }
    }

    public void move(GameEntity entity, float x, float y) {
        if (entity.getX() == 0 && entity.getY() == 0) return;

        Rectangle boundingRect = entity.getBoundingRectangle();

        //Grid bounds (Minimum / maximum grid coordinates) before move
        int beforeMinCellX = (int) (boundingRect.x / CELL_SIZE_X);
        int beforeMaxCellX = (int) ((boundingRect.x + boundingRect.width) / CELL_SIZE_X);
        int beforeMinCellY = (int) (boundingRect.y / CELL_SIZE_Y);
        int beforeMaxCellY = (int) ((boundingRect.y + boundingRect.height) / CELL_SIZE_Y);

        float translationX = x - entity.getX();
        float translationY = y - entity.getY();

        //Grid bounds after move
        int afterMinCellX = (int) ((boundingRect.x + translationX) / CELL_SIZE_X);
        int afterMaxCellX = (int) ((boundingRect.x + boundingRect.width + translationX) / CELL_SIZE_X);
        int afterMinCellY = (int) ((boundingRect.y + translationY) / CELL_SIZE_Y);
        int afterMaxCellY = (int) ((boundingRect.y + boundingRect.width + translationY) / CELL_SIZE_Y);

        //Update cells if entity's grid bounds have changed
        if (afterMinCellX != beforeMinCellX
                || afterMaxCellX != beforeMaxCellX
                || afterMinCellY != beforeMinCellY
                || afterMaxCellY != beforeMaxCellY) {

            Polygon translatedCollisionPolygon = new Polygon(entity.getCollisionPolygon().getTransformedVertices());
            translatedCollisionPolygon.translate(translationX, translationY);
            for (int cellX = 0; cellX < NUM_CELLS; cellX++) {
                for (int cellY = 0; cellY < NUM_CELLS; cellY++) {
                    if (isPolygonInCell(entity.getCollisionPolygon(), cellX, cellY)) {
                        if (!isPolygonInCell(translatedCollisionPolygon, cellX, cellY)) {
                            cells[cellX][cellY].remove(entity); //Entity is no longer in cell
                        }
                    } else {
                        if (isPolygonInCell(translatedCollisionPolygon, cellX, cellY)) {
                            cells[cellX][cellY].add(0, entity); //Entity just entered this cell
                        }
                    }
                }
            }
        }
    }

    public void remove(GameEntity entity) {
        for (int x = 0; x < NUM_CELLS; x++) {
            for (int y = 0; y < NUM_CELLS; y++) {
                cells[x][y].remove(entity);
            }
        }
    }

    private boolean isPolygonInCell(Polygon polygon, int cellX, int cellY) {
        float[] cellVertices = new float[] {
                cellX * CELL_SIZE_X, cellY * CELL_SIZE_Y,
                cellX * CELL_SIZE_X, cellY * CELL_SIZE_Y + CELL_SIZE_Y,
                cellX * CELL_SIZE_X + CELL_SIZE_X, cellY * CELL_SIZE_Y + CELL_SIZE_Y,
                cellX * CELL_SIZE_X + CELL_SIZE_X, cellY * CELL_SIZE_Y
        };
        return Intersector.overlapConvexPolygons(polygon.getTransformedVertices(), cellVertices, null);
    }
}
