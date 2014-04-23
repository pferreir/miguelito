package org.indico_software.games.miguelito;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;

/**
 * Created by pedro on 4/14/14.
 */
public class SplashScreen implements Screen {

    private final BitmapFont font;
    MiguelitoSingapore game;
    SpriteBatch batch;
    Texture fg_img;
    Texture bg_img;
    Music splash_music;

    Sprite fg_sprite;
    Sprite bg_sprite;

    float scale;
    boolean keep_running;
    long start_t;

    InputProcessor anyKeyProcessor;

    public SplashScreen(MiguelitoSingapore game) {
        this.game = game;

        keep_running = true;
        batch = new SpriteBatch();

        font = new BitmapFont(Gdx.files.internal("fonts/8bitwonder-gradient.fnt"),
                Gdx.files.internal("fonts/8bitwonder-gradient.png"), false);

        bg_img = new Texture(Gdx.files.internal("singapore_splash.png"));
        fg_img = new Texture(Gdx.files.internal("miguelito_splash.png"));

        splash_music = Gdx.audio.newMusic(Gdx.files.internal("mp3/miguelito-splash.mp3"));

        fg_sprite = new Sprite(fg_img);
        bg_sprite = new Sprite(bg_img);

        scale = 1.0f;

        start_t = TimeUtils.nanoTime();

        anyKeyProcessor = new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                keep_running = false;
                return false;
            }
        };
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glEnable(GL20.GL_BLEND);

        double t = TimeUtils.timeSinceNanos(start_t);

        scale = (float) (0.1 * Math.sin(t * 4E-10) + 0.95);

        bg_sprite.setPosition((Gdx.graphics.getWidth() - bg_img.getWidth()) / 2,
                (Gdx.graphics.getHeight() - bg_img.getHeight()) / 2);
        bg_sprite.setScale(scale);

        batch.begin();
        bg_sprite.draw(batch);
        fg_sprite.draw(batch);

        font.draw(batch, "PRESS ANY KEY", 120, 40);

        batch.end();

        if (!keep_running) {
            game.setScreen(new GameScreen(game));
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {
        splash_music.play();
        splash_music.setLooping(true);
        Gdx.input.setInputProcessor(anyKeyProcessor);
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
        splash_music.stop();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        splash_music.dispose();
    }
}
