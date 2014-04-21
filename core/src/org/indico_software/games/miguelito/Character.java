package org.indico_software.games.miguelito;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by pedro on 4/21/14.
 */
public class Character extends Body {

    private final float floorLevel;

    public Character(Vector2 position, float floorLevel) {
        super(position);
        this.floorLevel = floorLevel;
    }

    public void draw(ShapeRenderer shapeRenderer) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.rect(this.position.x - 5.0f, this.position.y + floorLevel, 10f, 20f);
        shapeRenderer.end();
    }
}
