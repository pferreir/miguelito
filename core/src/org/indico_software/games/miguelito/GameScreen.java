package org.indico_software.games.miguelito;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

/**
 * Created by pedro on 4/14/14.
 */
public class GameScreen implements Screen {

    private final OrthographicCamera camera;
    private final BitmapFont font;
    MiguelitoSingapore game;
    Music gameMusic;
    SpriteBatch batch;
    Sprite bgSprite;
    Texture spriteTexture;
    Newton newton;
    Character character;
    Array<Hazard> hazards;
    long counter = 0;

    final boolean SHOW_BOUNDING_BOXES = false;

    float scrollTimer = 0.0f;
    float scale = 1.0f;
    long lastHazardTime;

    protected final float width = Gdx.graphics.getWidth();
    protected final float height = Gdx.graphics.getHeight();
    private final float floorLevel = 20;
    private ShapeRenderer shapeRenderer;
    private boolean alive;

    private float redStart;

    public GameScreen(MiguelitoSingapore game) {
        this.game = game;
        this.alive = true;

        redStart = 0;

        character = new Character(new Vector2(50.0f, 100f), floorLevel);
        newton = new Newton(character, floorLevel);

        batch = new SpriteBatch();
        gameMusic = Gdx.audio.newMusic(Gdx.files.internal("mp3/miguelito-ingame.mp3"));

        font = new BitmapFont(Gdx.files.internal("fonts/8bitwonder-white.fnt"),
                Gdx.files.internal("fonts/8bitwonder-white.png"), false);

        shapeRenderer = new ShapeRenderer();
        spriteTexture = new Texture(Gdx.files.internal("gnv_skyline.png"));
        bgSprite = new Sprite(spriteTexture);
        bgSprite.setScale(scale);

        camera = new OrthographicCamera(width, height);

        hazards = new Array<Hazard>();
    }

    protected void drawFloor(ShapeRenderer sr) {
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(Color.DARK_GRAY);
        sr.rect(0, floorLevel - 10, width, 10);
        sr.end();
    }

    private void spawnHazard() {
        Hazard hazard = new Hazard(new Vector2(width, floorLevel), 64, 32, floorLevel);
        hazards.add(hazard);
        lastHazardTime = TimeUtils.millis();
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glEnable(GL20.GL_BLEND);

        float deltaT = Gdx.graphics.getDeltaTime();

        scrollTimer += deltaT * 0.15;

        camera.position.set(width / 2.0f, height / 2.0f, 0f);
        camera.update();

        bgSprite.setU(scrollTimer / 40);
        bgSprite.setU2(scrollTimer / 40 + 1);

        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(bgSprite, 0, 0);
        character.draw(batch, scrollTimer);

        for(Hazard hazard: hazards) {
            hazard.draw(batch);
        }

        if (redStart > 0) {
            batch.setColor(Color.RED);
            font.draw(batch, "PRESS SPACE", 20, 100);
            font.draw(batch, "TO RESTART", 20, 60);
        }

        font.draw(batch, String.valueOf(counter), width/2, height- 50);



        batch.end();

        ShapeRenderer sr = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(camera.combined);

        drawFloor(sr);
        if (SHOW_BOUNDING_BOXES) {
            character.drawBoundingBox(sr);
        }

        for(Hazard hazard: hazards) {
            hazard.updatePosition(deltaT);

            if (SHOW_BOUNDING_BOXES) {
                hazard.drawBoundingBox(sr);
            }
        }
        newton.update(deltaT);

        // check if we need to create a new hazard
        if(TimeUtils.millis() - lastHazardTime > 3000*MathUtils.random(1, 10)) spawnHazard();

        // remove any hazard that is left behind or kill if hit
        Iterator<Hazard> iter = hazards.iterator();

        while(iter.hasNext()) {
            Hazard hazard = iter.next();
            Vector2 hazardPos = hazard.getPosition();
            hazard.setPosition(new Vector2(hazardPos.x - 50 * deltaT, hazardPos.y));
            if(hazardPos.x + 64 < 0) {
                iter.remove();
            } else if (hazardPos.x < character.getPosition().x && redStart == 0) {
                counter += 1;
            }

            if(alive && character.overlaps(hazard)) {
                character.die();
                gameMusic.stop();
                fadeToRed(scrollTimer);
                alive = false;
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            if (alive && character.getPosition().y == floorLevel) {
                character.accelerate(new Vector2(0f, 150f));
            } else if (!alive && (scrollTimer - redStart > 0.2)) {
                game.setScreen(new SplashScreen(game));
            }
        }
    }

    private void fadeToRed(float time) {
        redStart = time;
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
