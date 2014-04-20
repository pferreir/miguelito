package org.indico_software.games.miguelito;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by josebenitogonzalezlopez on 20/04/14.
 */
public class GameCredits implements Screen{

    Game game;
    SpriteBatch batch;
    Sprite sprite;
    Texture spriteTexture;
    float scale = 1.0f;

    public GameCredits(Game game) {
        this.game = game;
        batch = new SpriteBatch();
        spriteTexture = new Texture(Gdx.files.internal("the-end.jpg"));
        sprite = new Sprite(spriteTexture);
        sprite.setScale(scale);
    }

    @Override
    public void render(float delta) {
        batch.begin();
        sprite.draw(batch);
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
