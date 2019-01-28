package com.sdu.abund14.master.paxbrit.ship;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.sdu.abund14.master.paxbrit.GameStage;

public class FactoryShip extends Ship {

    private ProductionButton button;

    public FactoryShip(String textureName, boolean isPlayerControlled) {
        super(textureName);
        if (isPlayerControlled) {
            button = new ProductionButton(this);
        }
    }

    @Override
    public void draw(Batch batch, float alpha) {
        super.draw(batch, alpha);
        if (button != null) {
            button.draw(batch, alpha);
        }
    }

    class ProductionButton extends Actor {

        private FactoryShip parent;
        private ShapeRenderer renderer;

        ProductionButton(FactoryShip parent) {
            this.parent = parent;
            renderer = new ShapeRenderer();
            addListener(new ClickListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    System.out.println("touchdown");
                    event.handle();
                    return true;
                }
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    System.out.println("touchup");
                }
            });
            setTouchable(Touchable.enabled);
            GameStage.getInstance().addActor(this);
        }

        @Override
        public void draw(Batch batch, float parentAlpha) {
            super.draw(batch, parentAlpha);
            batch.end();
            renderer.begin(ShapeRenderer.ShapeType.Filled);
            renderer.setColor(Color.BLACK);
            Rectangle bounds = parent.getBoundingRectangle();
            renderer.circle(
                    bounds.x + bounds.width / 2,
                    bounds.y + bounds.height / 2,
                    parent.getWidth() / 5
            );
            renderer.end();
            batch.begin();
        }
    }
}
