package com.sdu.abund14.master.paxbrit.ship;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.sdu.abund14.master.paxbrit.GameStage;
import com.sdu.abund14.master.paxbrit.graphics.TextureRegionProvider;
import com.sdu.abund14.master.paxbrit.util.NumbersUtil;

public class FactoryShip extends Ship {

    private static final float FIGHTER_SPAWN_TIME = 0.5f;
    private static final float BOMBER_SPAWN_TIME = 4f;
    private static final float FRIGATE_SPAWN_TIME = 10f;
    private static final float UPGRADE_TIME = 20f;

    private ProductionButton button;
    private long productionStartedAt = 0;

    public FactoryShip(String textureName, boolean isPlayerControlled) {
        super(textureName);
        if (isPlayerControlled) {
            button = new ProductionButton(this);
            GameStage.getInstance().addActor(button);
        }
    }

    @Override
    public void draw(Batch batch, float alpha) {
        super.draw(batch, alpha);
        if (button != null) {
            button.draw(batch, alpha);
        }
    }

    private boolean touchDownFired() {
        System.out.println("touchdown");
        productionStartedAt = System.nanoTime();
        return true;
    }

    private void touchUpFired() {
        long durationHeld = System.nanoTime() - productionStartedAt;
        if (durationHeld > NumbersUtil.toNanoSeconds(UPGRADE_TIME)) {
            upgrade();
        } else if (durationHeld > NumbersUtil.toNanoSeconds(FRIGATE_SPAWN_TIME)) {
            spawnFrigate();
        } else if (durationHeld > NumbersUtil.toNanoSeconds(BOMBER_SPAWN_TIME)) {
            spawnBomber();
        } else if (durationHeld > NumbersUtil.toNanoSeconds(FIGHTER_SPAWN_TIME)) {
            spawnFighter();
        }
        productionStartedAt = 0;
    }

    private void upgrade() {
        System.out.println("upgrade");
    }

    private void spawnFrigate() {
        System.out.println("frigate");
    }

    private void spawnBomber() {
        System.out.println("bomber");
    }

    private void spawnFighter() {
        System.out.println("fighter");
    }

    class ProductionButton extends Image {

        private static final float SHIP_BOUNDS_TO_BUTTON_RADIUS_RATIO = 0.2f;

        private FactoryShip parent;
        private ShapeRenderer sr;

        ProductionButton(FactoryShip parent) {
            super();
            this.parent = parent;
            sr = new ShapeRenderer();
            init();
        }

        private void init() {
            addListener(new ClickListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    return parent.touchDownFired();
                }
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    parent.touchUpFired();
                }
            });
            setTouchable(Touchable.enabled);
            setOrigin(getWidth() / 2, getHeight() / 2);
        }

        @Override
        public void draw(Batch batch, float alpha) {
            Rectangle parentBounds = parent.getBoundingRectangle();
            float radius = 50;
            float x = parentBounds.x + parentBounds.width / 2 - getWidth() / 2;
            float y = parentBounds.y + parentBounds.height / 2 - getHeight() / 2;
            setPosition(x, y);
            setOrigin(x + radius / 2, y + radius / 2);
            setBounds(x, y, radius, radius);

            //Debug
            batch.end();
            sr.begin(ShapeRenderer.ShapeType.Line);
            sr.setColor(Color.PURPLE);
            sr.rect(x, y, radius, radius);
            sr.rect(parentBounds.x, parentBounds.y, parentBounds.width, parentBounds.height);
            sr.setColor(Color.ORANGE);
            sr.rect(parent.getX(), parent.getY(), parent.getWidth(), parent.getHeight());
            sr.end();
            batch.begin();

            String buttonDecorationTexture;
            if (productionStartedAt != 0) {
                long durationHeld = System.nanoTime() - productionStartedAt;
                if (durationHeld > NumbersUtil.toNanoSeconds(UPGRADE_TIME)) {
                    buttonDecorationTexture = "upgradeoutline";
                } else if (durationHeld > NumbersUtil.toNanoSeconds(FRIGATE_SPAWN_TIME)) {
                    buttonDecorationTexture = "frigateoutline";
                } else if (durationHeld > NumbersUtil.toNanoSeconds(BOMBER_SPAWN_TIME)) {
                    buttonDecorationTexture = "bomberoutline";
                } else if (durationHeld > NumbersUtil.toNanoSeconds(FIGHTER_SPAWN_TIME)) {
                    buttonDecorationTexture = "fighteroutline";
                } else {
                    buttonDecorationTexture = "healthfull";
                }
            } else {
                buttonDecorationTexture = "healthfull"; //TODO: Change to reflect health
            }
            Sprite buttonDecoration = new Sprite(TextureRegionProvider.get(buttonDecorationTexture));
            buttonDecoration.setPosition(getX(), getY());
            buttonDecoration.draw(batch, 1);
        }
    }
}
