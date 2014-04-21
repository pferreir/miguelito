package org.indico_software.games.miguelito;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by pedro on 4/14/14.
 */
public class GameScreen implements Screen {

    private final OrthographicCamera camera;
    MiguelitoSingapore game;
    Music gameMusic;
    SpriteBatch batch;
    Sprite bgSprite;
    Texture spriteTexture;
    Newton newton;
    Character character;

    float scrollTimer = 0.0f;
    float scale = 1.0f;

    protected final float width = Gdx.graphics.getWidth();
    protected final float height = Gdx.graphics.getHeight();
    private final float floorLevel = height / 3;
    private ShapeRenderer shapeRenderer;

    public GameScreen(MiguelitoSingapore game) {
        this.game = game;

        character = new Character(new Vector2(50.0f, 100f), floorLevel);
        newton = new Newton(character);

        batch = new SpriteBatch();
        gameMusic = Gdx.audio.newMusic(Gdx.files.internal("mp3/miguelito-ingame.mp3"));

        shapeRenderer = new ShapeRenderer();
        spriteTexture = new Texture(Gdx.files.internal("sea.jpg"));
        bgSprite = new Sprite(spriteTexture);
        bgSprite.setScale(scale);

        camera = new OrthographicCamera(width, height);
    }

    protected void drawFloor() {
        shapeRenderer.setProjectionMatrix(camera.combined);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(0, floorLevel - 20, width, 20);
        shapeRenderer.end();
    }

    @Override
    public void render(float delta) {
        float deltaT = Gdx.graphics.getDeltaTime();

        scrollTimer += deltaT * 0.15;
        if(scrollTimer > 10.0f)
            game.setScreen(game.gameCredits);

        camera.position.set(width / 2.0f, height / 2.0f, 0f);
        camera.update();

        bgSprite.setU(scrollTimer);
        bgSprite.setU2(scrollTimer + 1);

        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        bgSprite.draw(batch);
        batch.end();

        drawFloor();

        newton.update(deltaT);

        character.draw(shapeRenderer);

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && character.getPosition().y == 0) {
            character.accelerate(new Vector2(0, 100f));
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {
        gameMusic.play();
        gameMusic.setLooping(true);
    }

    @Override
    public void hide() {
        gameMusic.stop();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        gameMusic.dispose();
    }
}
