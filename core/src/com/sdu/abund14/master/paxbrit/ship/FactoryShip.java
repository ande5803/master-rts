package com.sdu.abund14.master.paxbrit.ship;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.sdu.abund14.master.paxbrit.PaxBritannicaGame;
import com.sdu.abund14.master.paxbrit.graphics.TextureRegionProvider;
import com.sdu.abund14.master.paxbrit.util.MathUtil;

public class FactoryShip extends Ship {


    private static final float UPGRADE_PRODUCTION_MULTIPLIER = 0.85f;
    private static final float BUTTON_OFFSET_DISTANCE = 4.6f;
    private static int count = 0;

    private float fighterSpawnTime = 0.8f;
    private float bomberSpawnTime = 2.7f;
    private float frigateSpawnTime = 5.8f;
    private float upgradeTime = 17.3f;

    private ProductionButton button;
    private TextureRegionProvider textureRegionProvider;
    private long productionStartedAt = 0;

    public FactoryShip(String textureName, boolean isPlayerControlled) {
        super(count + 1, textureName);
        textureRegionProvider = new TextureRegionProvider();
        count++;
        type = ShipType.FACTORY;
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

    public ProductionButton getButton() {
        return button;
    }

    private boolean touchDownFired() {
        System.out.println("touchdown");
        productionStartedAt = System.nanoTime();
        return true;
    }

    private void touchUpFired() {
        long durationHeld = System.nanoTime() - productionStartedAt;
        if (durationHeld > MathUtil.toNanoSeconds(upgradeTime)) {
            upgrade();
        } else if (durationHeld > MathUtil.toNanoSeconds(frigateSpawnTime)) {
            spawnFrigate();
        } else if (durationHeld > MathUtil.toNanoSeconds(bomberSpawnTime)) {
            spawnBomber();
        } else if (durationHeld > MathUtil.toNanoSeconds(fighterSpawnTime)) {
            spawnFighter();
        }
        productionStartedAt = 0;
    }

    private void upgrade() {
        System.out.println("upgrade");
        fighterSpawnTime *= UPGRADE_PRODUCTION_MULTIPLIER;
        bomberSpawnTime *= UPGRADE_PRODUCTION_MULTIPLIER;
        frigateSpawnTime *= UPGRADE_PRODUCTION_MULTIPLIER;
        upgradeTime *= UPGRADE_PRODUCTION_MULTIPLIER;
    }

    private void spawnFrigate() {
        System.out.println("frigate");
        new Frigate(getPlayerNumber(), getX(), getY());
    }

    private void spawnBomber() {
        System.out.println("bomber");
        new Bomber(getPlayerNumber(), getX(), getY());
    }

    private void spawnFighter() {
        System.out.println("fighter");
        new Fighter(getPlayerNumber(), getX(), getY());
    }

    private float getButtonOffsetX() {
        return (float) -Math.cos(Math.toRadians(getRotation() % 360)) * BUTTON_OFFSET_DISTANCE;
    }

    private float getButtonOffsetY() {
        return (float) -Math.sin(Math.toRadians(getRotation() % 360)) * BUTTON_OFFSET_DISTANCE;
    }

    class ProductionButton extends Image {

        static final float SIDE_LENGTH = 60;
        private FactoryShip parent;

        ProductionButton(FactoryShip parent) {
            super();
            this.parent = parent;
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
            PaxBritannicaGame.currentMatch.getScreen().getStage().addActor(this);
        }

        @Override
        public void draw(Batch batch, float alpha) {
            Rectangle parentBounds = parent.getBoundingRectangle();
            float x = parentBounds.x + parentBounds.width / 2 - SIDE_LENGTH / 2 + parent.getButtonOffsetX();
            float y = parentBounds.y + parentBounds.height / 2 - SIDE_LENGTH / 2 + parent.getButtonOffsetY();
            setBounds(x, y, SIDE_LENGTH, SIDE_LENGTH);
            setPosition(x, y);

            //Draw button decoration
            String buttonDecorationTexture = getButtonDecorationTexture();
            Sprite buttonDecoration = new Sprite(textureRegionProvider.get(buttonDecorationTexture));
            buttonDecoration.setPosition(x, y);
            if (buttonDecorationTexture.contains("health")) {
                //Health indicators rotate with the ship, production outlines do not
                buttonDecoration.setRotation(parent.getRotation());
            }
            buttonDecoration.draw(batch, 1);
        }

        private String getButtonDecorationTexture() {
            String buttonDecorationTexture;
            if (productionStartedAt != 0) {
                long durationHeld = System.nanoTime() - productionStartedAt;
                if (durationHeld > MathUtil.toNanoSeconds(upgradeTime)) {
                    buttonDecorationTexture = "upgradeoutline";
                } else if (durationHeld > MathUtil.toNanoSeconds(frigateSpawnTime)) {
                    buttonDecorationTexture = "frigateoutline";
                } else if (durationHeld > MathUtil.toNanoSeconds(bomberSpawnTime)) {
                    buttonDecorationTexture = "bomberoutline";
                } else if (durationHeld > MathUtil.toNanoSeconds(fighterSpawnTime)) {
                    buttonDecorationTexture = "fighteroutline";
                } else {
                    buttonDecorationTexture = "healthfull";
                }
            } else {
                buttonDecorationTexture = "healthfull"; //TODO: Change to reflect health
            }
            return buttonDecorationTexture;
        }
    }
}
