package org.indico_software.games.miguelito;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.nio.channels.spi.SelectorProvider;

/**
 * Created by pedro on 4/21/14.
 */
public class Character extends Body {

    private static final float WIDTH = 23;
    private static final float HEIGHT = 60;
    private Texture walkSheet;
    private TextureRegion[] walkFrames;
    private Animation walkAnimation;
    private TextureRegion currentFrame;

    public Character(Vector2 position, float floorLevel) {
        super(position, WIDTH, HEIGHT, floorLevel);
        this.createSprite();
    }

    private void createSprite() {
        walkSheet = new Texture(Gdx.files.internal("player.png"));
        TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth() / 2, walkSheet.getHeight());
        walkFrames = new TextureRegion[2];

        int index = 0;
        for (int j = 0; j < 2; j++) {
            walkFrames[index++] = tmp[0][j];
        }

        walkAnimation = new Animation(0.1f, walkFrames);
    }

    public void draw(SpriteBatch spriteBatch, float time) {
        currentFrame = walkAnimation.getKeyFrame(time, true);
        spriteBatch.draw(currentFrame, this.position.x - WIDTH / 2, this.position.y);
    }

    public void die() {
        this.accelerate(new Vector2(0, 300f));
    }
}
