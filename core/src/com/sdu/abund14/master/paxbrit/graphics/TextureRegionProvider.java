package com.sdu.abund14.master.paxbrit.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TextureRegionProvider {

    private TextureAtlas atlas;

    public TextureRegionProvider() {
        atlas = new TextureAtlas(Gdx.files.internal("data/spritepack/packhigh.pack"));
    }

    public TextureRegion get(String textureName) {
        TextureRegion region;
        region = atlas.findRegion(textureName);
        if (region == null) {
            region = new TextureRegion(new Texture(Gdx.files.internal(textureName) + ".png"));
        }
        return region;
    }
}
