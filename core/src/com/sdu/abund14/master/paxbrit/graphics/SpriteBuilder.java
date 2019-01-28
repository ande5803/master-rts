package com.sdu.abund14.master.paxbrit.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class SpriteBuilder {

    private static TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("data/spritepack/packhigh.pack"));

    public static Sprite get(String textureName) {
        Sprite sprite;
        sprite = atlas.createSprite(textureName);
        if (sprite == null) {
            sprite = new Sprite(new Texture(Gdx.files.internal(textureName) + ".png"));
            if (sprite.getTexture() == null) {
                System.out.println("Error in SpriteBuilder.get: No texture found");
            }
        }
        return sprite;
    }
}
