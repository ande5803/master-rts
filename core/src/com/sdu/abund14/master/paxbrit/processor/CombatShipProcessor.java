package com.sdu.abund14.master.paxbrit.renderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.sdu.abund14.master.paxbrit.GameScreen;
import com.sdu.abund14.master.paxbrit.PaxBritannicaGame;
import com.sdu.abund14.master.paxbrit.interfaces.Renderer;
import com.sdu.abund14.master.paxbrit.ship.CombatShip;
import com.sdu.abund14.master.paxbrit.ship.Ship;

public class CombatShipRenderer implements Renderer {

    private GameScreen screen;

    private void init(Screen screen) {
        if (screen instanceof GameScreen) {
            this.screen = (GameScreen) screen;
        } else {
            System.out.println("Error in CombatShipRenderer.init: Attempt to render combat ships on non-GameScreen instance");
        }
    }

    @Override
    public void render(Screen screen) {
        if (this.screen == null) init(screen);
//        for (Ship ship : PaxBritannicaGame.currentMatch.getShips()) {
//            if (ship instanceof CombatShip) {
//
//            }
//        }
    }

    @Override
    public void dispose() {
    }
}
