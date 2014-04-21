package org.indico_software.games.miguelito;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by pedro on 4/14/14.
 */
public class GameScreen implements Screen {

    MiguelitoSingapore game;
    Music game_music;
    SpriteBatch batch;
    Sprite sprite;
    Texture spriteTexture;
    float scrollTimer = 0.0f;
    float scale = 1.0f;


    public GameScreen(MiguelitoSingapore game) {
        this.game = game;
        batch = new SpriteBatch();
        game_music = Gdx.audio.newMusic(Gdx.files.internal("mp3/miguelito-ingame.mp3"));

        spriteTexture = new Texture(Gdx.files.internal("sea.jpg"));
        sprite = new Sprite(spriteTexture);
        sprite.setScale(scale);


    }

    @Override
    public void render(float delta) {
        scrollTimer += Gdx.graphics.getDeltaTime()*0.15;
        if(scrollTimer > 10.0f)
            game.setScreen(game.gameCredits);

        sprite.setU(scrollTimer);
        sprite.setU2(scrollTimer+1);

        batch.begin();
        sprite.draw(batch);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {
        game_music.play();
        game_music.setLooping(true);
    }

    @Override
    public void hide() {
        game_music.stop();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        game_music.dispose();
    }
}
