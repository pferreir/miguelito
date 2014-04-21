package org.indico_software.games.miguelito;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by pedro on 4/21/14.
 * Basic physics, collision, etc...
 */
public class Newton {
    private final Character character;

    public Newton(Character character) {
        this.character = character;
    }

    public void update(float deltaT) {
        this.character.updatePosition(deltaT);
        Vector2 deltaVel = new Vector2(0, deltaT * -100);
        this.character.accelerate(deltaVel);
    }
}
