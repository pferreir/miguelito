package org.indico_software.games.miguelito;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by pedro on 4/23/14.
 */
public class Hazard extends Body {
    private final Texture hazardImage;

    private boolean bypassed = false;

    public Hazard(Vector2 position, float width, float height, float floorLevel) {
        super(position, width, height, floorLevel);

        hazardImage = new Texture(Gdx.files.internal("bridge-stone.png"));
    }

    public void setBypassed(boolean bp) {
        bypassed = bp;
    }

    public boolean isBypassed() {
        return bypassed;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(hazardImage, position.x - width / 2, position.y);
    }
}
