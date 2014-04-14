package org.indico_software.games.miguelito;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;

/**
 * Created by pedro on 4/14/14.
 */
public class SplashScreen implements Screen {

    SpriteBatch batch;
    Texture fg_img;
    Texture bg_img;

    Sprite fg_sprite;
    Sprite bg_sprite;

    float scale;
    long start_t;

    public SplashScreen() {
        batch = new SpriteBatch();
        bg_img = new Texture(Gdx.files.internal("singapore_splash.png"));
        fg_img = new Texture(Gdx.files.internal("miguelito_splash.png"));

        fg_sprite = new Sprite(fg_img);
        bg_sprite = new Sprite(bg_img);

        scale = 1.0f;

        start_t = TimeUtils.nanoTime();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        double t = TimeUtils.timeSinceNanos(start_t);

        scale = (float) (0.1 * Math.sin(t * 4E-10) + 0.95);

        bg_sprite.setPosition((Gdx.graphics.getWidth() - bg_img.getWidth()) / 2,
                (Gdx.graphics.getHeight() - bg_img.getHeight()) / 2);
        bg_sprite.setScale(scale);

        batch.begin();
        bg_sprite.draw(batch);
        fg_sprite.draw(batch);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
