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
    MiguelitoSingapore game;
    Music gameMusic;
    SpriteBatch batch;
    Sprite bgSprite;
    Texture spriteTexture;
    Newton newton;
    Character character;
    Texture hazardImage;
    Array<Rectangle> hazards;


    float scrollTimer = 0.0f;
    float scale = 1.0f;
    long lastHazardTime;

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
        hazardImage = new Texture(Gdx.files.internal("bridge-stone.png"));
        spriteTexture = new Texture(Gdx.files.internal("sea.jpg"));
        bgSprite = new Sprite(spriteTexture);
        bgSprite.setScale(scale);

        camera = new OrthographicCamera(width, height);

        hazards = new Array<Rectangle>();
    }

    protected void drawFloor() {
        shapeRenderer.setProjectionMatrix(camera.combined);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(0, floorLevel - 20, width, 20);
        shapeRenderer.end();
    }

    private void spawnHazard() {
        Rectangle hazard = new Rectangle();
        hazard.x = width;
        hazard.y = floorLevel - 20;
        hazard.width = 64;
        hazard.height = 64;
        hazards.add(hazard);
        lastHazardTime = TimeUtils.millis();
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
        character.draw(batch, scrollTimer);
        for(Rectangle hazard: hazards) {
            batch.draw(hazardImage, hazard.x, hazard.y);
        }
        batch.end();

        drawFloor();

        newton.update(deltaT);

        // check if we need to create a new hazard
        if(TimeUtils.millis() - lastHazardTime > 3000*MathUtils.random(1, 10)) spawnHazard();

        // remove any hazard that is left behind or kill if hit
        Iterator<Rectangle> iter = hazards.iterator();
        Rectangle characterRect = new Rectangle();
        Vector2 pos = character.getPosition();
        characterRect.x = pos.x - 5.0f;
        characterRect.y = pos.y + floorLevel;
        characterRect.width = 23;
        characterRect.height = 60;
        System.out.println("CH x:"+pos.x+"y:"+pos.y);
        while(iter.hasNext()) {
            Rectangle hazard = iter.next();
            hazard.x -= 50 * deltaT;
            if(hazard.x + 64 < 0) iter.remove();
            System.out.println("HH x:"+hazard.x+"y:"+hazard.y);
            if(characterRect.overlaps(hazard)) {
                // dieeeee!
                game.setScreen(game.gameCredits);
            }
        }


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
