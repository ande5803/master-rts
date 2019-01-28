package com.sdu.abund14.master.paxbrit;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class GameStage extends Stage {
    private GameStage() { }
    private static GameStage instance;
    public static GameStage getInstance() {
        if (instance == null) {
            instance = new GameStage();
        }
        return instance;
    }
}
