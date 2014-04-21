package org.indico_software.games.miguelito;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by pedro on 4/21/14.
 */
public class Body {
    protected Vector2 position;
    protected final Vector2 velocity;

    public Body(Vector2 position) {
        this.position = position;
        this.velocity = new Vector2(0, 0);
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public void accelerate(Vector2 delta) {
        this.velocity.add(delta);
    }

    public void move(Vector2 delta) {
        this.position.add(delta);
    }

    public Vector2 getPosition() {
        return position;
    }

    public void updatePosition(float delta) {
        this.position.add(this.velocity.cpy().scl(delta));

        if (this.position.y < 0) {
            this.velocity.set(0, 0);
            this.position.set(this.position.x, 0);
        }
    }
}
