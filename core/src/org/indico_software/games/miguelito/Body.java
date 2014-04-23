package org.indico_software.games.miguelito;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.collision.BoundingBox;

/**
 * Created by pedro on 4/21/14.
 */
public class Body {
    protected final float floorLevel;
    protected final float width;
    protected final float height;
    protected Vector2 position;
    protected final Vector2 velocity;
    protected Rectangle boundingBox;


    public Body(Vector2 position, float width, float height, float floorLevel) {
        this.position = position;
        this.width = width;
        this.height = height;
        this.velocity = new Vector2(0, 0);

        boundingBox = new Rectangle();
        updateBoundingBox();

        this.floorLevel = floorLevel;
    }

    private void updateBoundingBox() {
        boundingBox.x = position.x - width / 2;
        boundingBox.y = position.y;
        boundingBox.width = width;
        boundingBox.height = height;
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

        if (this.position.y < floorLevel) {

            this.velocity.set(0, 0);
            this.position.set(this.position.x, floorLevel);
        }

        updateBoundingBox();
    }

    public void drawBoundingBox(ShapeRenderer sr) {
        sr.begin(ShapeRenderer.ShapeType.Line);
        sr.setColor(Color.CYAN);
        sr.rect(this.boundingBox.x, this.boundingBox.y,
                this.boundingBox.width, this.boundingBox.height);
        sr.end();
    }

    public boolean overlaps(Body other) {
        return this.boundingBox.overlaps(other.boundingBox);
    }
}
