package com.flappybird.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Random;

public class FlappyBird extends ApplicationAdapter {

    private SpriteBatch batch;
    private Texture bird[];
    private Texture background;
    private Texture lowPipe;
    private Texture highPipe;
    private Texture lowPipe2;
    private Texture highPipe2;
    private BitmapFont font;
    private BitmapFont message;
    private BitmapFont bestScore;
    private Circle circleBirde;
    private Rectangle highPipeRectangle;
    private Rectangle lowPipeRectangle;
    private Circle circleBirde2;
    private Rectangle highPipeRectangle2;
    private Rectangle lowPipeRectangle2;
    private Texture gOver;

    private int DEVICE_WIDTH;
    private int DEVICE_HEIGHT;

    private float imageVariation = 0;
    private int dropSpeed = 0;
    private float birdVerticalPosition;
    private float pipesHorizontalPosition;
    private float highPipeVerticalPosition;
    private float lowPipeVerticalPosition;
    private float pipesHorizontalPosition2;
    private float highPipeVerticalPosition2;
    private float lowPipeVerticalPosition2;
    private float deltaTime;
    private Random rand;
    private int randomHigh;
    private int randomHigh2;
    private boolean isPlaying = false;
    private boolean gameOver = false;
    private boolean gotScore = false;
    private int score = 0;

    private static final int birdHorizontalPosition = 120;
    private static final int SPACE_BETWEEN_PIPE = 150;
    private static final int DISTANCE_BETWEEN_PIPES = 450;
    private static final int PIPES_VELOCITY = 200;
    private static final int HIGH_WHEN_TOUCHED = 18;
    private static final String PREFERENCES = "My Preferences";
    private static final String BEST_SCORE_KEY = "Score";

    //Camera config
    private OrthographicCamera camera;
    private Viewport viewport;
    private static final int VIRTUAL_WIDTH = 768;
    private static final int VIRTUAL_HEIGHT = 1024;

	@Override
	public void create () {
        batch = new SpriteBatch();

        font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().setScale(6);

        message = new BitmapFont();
        message.setColor(Color.WHITE);
        message.getData().setScale(3);

        bestScore = new BitmapFont();
        bestScore.setColor(Color.WHITE);
        bestScore.getData().setScale(3);

        bird = new Texture[3];
        bird[0] = new Texture("passaro1.png");
        bird[1] = new Texture("passaro2.png");
        bird[2] = new Texture("passaro3.png");

        background = new Texture("fundo.png");

        lowPipe = new Texture("cano_baixo.png");
        highPipe = new Texture("cano_topo.png");

        lowPipe2 = new Texture("cano_baixo.png");
        highPipe2 = new Texture("cano_topo.png");

        gOver = new Texture("game_over.png");

        circleBirde = new Circle();
        highPipeRectangle = new Rectangle();
        lowPipeRectangle = new Rectangle();

        circleBirde2 = new Circle();
        highPipeRectangle2 = new Rectangle();
        lowPipeRectangle2 = new Rectangle();

        //Camera config to fit in several resolutions
        camera = new OrthographicCamera();
        camera.position.set(VIRTUAL_WIDTH/2, VIRTUAL_HEIGHT/2, 0);
        viewport = new StretchViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);

        DEVICE_WIDTH = VIRTUAL_WIDTH;
        DEVICE_HEIGHT = VIRTUAL_HEIGHT;

        birdVerticalPosition = DEVICE_HEIGHT/2;

        pipesHorizontalPosition = DEVICE_WIDTH;
        highPipeVerticalPosition = DEVICE_HEIGHT/2;
        lowPipeVerticalPosition = DEVICE_HEIGHT/2 - lowPipe.getHeight();

        pipesHorizontalPosition2 = DEVICE_WIDTH + DISTANCE_BETWEEN_PIPES;
        highPipeVerticalPosition2 = DEVICE_HEIGHT/2;
        lowPipeVerticalPosition2 = DEVICE_HEIGHT/2 - lowPipe.getHeight();

        rand = new Random();
        randomHigh = rand.nextInt(401) - 200;
        randomHigh2 = rand.nextInt(401) - 200;
	}

	@Override
	public void render () {
	    camera.update();

	    //Clearing all past frames
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        deltaTime = Gdx.graphics.getDeltaTime();

        imageVariation += deltaTime * 5;
        if(imageVariation > 3) {
            imageVariation = 0;
        }

        if(!isPlaying){
	        if(Gdx.input.justTouched()){
	            isPlaying = true;
            }
        }
        else {
            if(birdVerticalPosition > 0 || dropSpeed < 0) {
                birdVerticalPosition -= dropSpeed++;
            }

            if(!gameOver){
                if(Gdx.input.justTouched()){
                    dropSpeed = -HIGH_WHEN_TOUCHED;
                }

                pipesHorizontalPosition -= deltaTime * PIPES_VELOCITY;
                if(pipesHorizontalPosition < -highPipe.getWidth()){
                    pipesHorizontalPosition = DEVICE_WIDTH;
                    randomHigh = rand.nextInt(401) - 200;
                    gotScore = false;
                }

                pipesHorizontalPosition2 -= deltaTime * PIPES_VELOCITY;
                if(pipesHorizontalPosition2 < -highPipe2.getWidth()){
                    pipesHorizontalPosition2 = DEVICE_WIDTH;
                    randomHigh2 = rand.nextInt(401) - 200;
                    gotScore = false;
                }

                if(pipesHorizontalPosition < birdHorizontalPosition){
                    if (!gotScore) {
                        score++;
                        gotScore = true;
                    }
                }

                if(pipesHorizontalPosition2 < birdHorizontalPosition){
                    if (!gotScore) {
                        score++;
                        gotScore = true;
                    }
                }
            }
            else{
                if(Gdx.input.justTouched()){
                    gameOver = false;
                    score = 0;
                    gotScore = false;
                    dropSpeed = 0;
                    birdVerticalPosition = DEVICE_HEIGHT/2;
                    pipesHorizontalPosition = DEVICE_WIDTH;
                    pipesHorizontalPosition2 = DEVICE_WIDTH + DISTANCE_BETWEEN_PIPES;
                }
            }
        }

        batch.setProjectionMatrix(camera.combined);
	    batch.begin();

        batch.draw(background, 0, 0, DEVICE_WIDTH, DEVICE_HEIGHT);

        batch.draw(highPipe, pipesHorizontalPosition, highPipeVerticalPosition + SPACE_BETWEEN_PIPE + randomHigh);
        batch.draw(lowPipe, pipesHorizontalPosition, lowPipeVerticalPosition - SPACE_BETWEEN_PIPE + randomHigh);

        batch.draw(highPipe2, pipesHorizontalPosition2, highPipeVerticalPosition2 + SPACE_BETWEEN_PIPE + randomHigh2);
        batch.draw(lowPipe2, pipesHorizontalPosition2, lowPipeVerticalPosition2 - SPACE_BETWEEN_PIPE + randomHigh2);

        batch.draw(bird[(int) imageVariation], birdHorizontalPosition, birdVerticalPosition);

        font.draw(batch, String.valueOf(score), DEVICE_WIDTH/2 - 10, DEVICE_HEIGHT - 50);

        if(!isPlaying){
            message.draw(batch,"Touch anywhere to start", DEVICE_WIDTH/4 - 60, DEVICE_HEIGHT/2 - 30);
            Preferences prefs = Gdx.app.getPreferences(PREFERENCES);
            String newScore = prefs.getString(BEST_SCORE_KEY, "0");
            bestScore.draw(batch, "Best score: " + newScore, DEVICE_WIDTH / 4 - 60, DEVICE_HEIGHT / 2 - 80);
        }

        if(gameOver){
            batch.draw(gOver, DEVICE_WIDTH/4, DEVICE_HEIGHT/2);
            message.draw(batch,"Touch anywhere to restart", DEVICE_WIDTH/4 - 60, DEVICE_HEIGHT/2 - 30);

            Preferences prefs = Gdx.app.getPreferences(PREFERENCES);
            String newScore = prefs.getString(BEST_SCORE_KEY, "0");
            int aux = Integer.parseInt(newScore);

            if(score > aux){
                prefs.putString(BEST_SCORE_KEY, String.valueOf(score));
                prefs.flush();
                bestScore.draw(batch, "Best score: " + score, DEVICE_WIDTH/4 - 60, DEVICE_HEIGHT/2 - 80);
            }
            else {
                bestScore.draw(batch, "Best score: " + newScore, DEVICE_WIDTH / 4 - 60, DEVICE_HEIGHT / 2 - 80);
            }
        }

        batch.end();

        //Getting the collisions
        circleBirde.set(birdHorizontalPosition + bird[0].getWidth()/2, birdVerticalPosition + bird[0].getHeight()/2, bird[0].getWidth()/2);
        highPipeRectangle.set(pipesHorizontalPosition, highPipeVerticalPosition + SPACE_BETWEEN_PIPE + randomHigh, highPipe.getWidth(), highPipe.getHeight());
        lowPipeRectangle.set(pipesHorizontalPosition, lowPipeVerticalPosition - SPACE_BETWEEN_PIPE + randomHigh, lowPipe.getWidth(), lowPipe.getHeight());

        circleBirde2.set(birdHorizontalPosition + bird[0].getWidth()/2, birdVerticalPosition + bird[0].getHeight()/2, bird[0].getWidth()/2);
        highPipeRectangle2.set(pipesHorizontalPosition2, highPipeVerticalPosition2 + SPACE_BETWEEN_PIPE + randomHigh2, highPipe2.getWidth(), highPipe2.getHeight());
        lowPipeRectangle2.set(pipesHorizontalPosition2, lowPipeVerticalPosition2 - SPACE_BETWEEN_PIPE + randomHigh2, lowPipe2.getWidth(), lowPipe2.getHeight());

        if(Intersector.overlaps(circleBirde, highPipeRectangle) || Intersector.overlaps(circleBirde, lowPipeRectangle) || birdVerticalPosition <= 0){
            gameOver = true;
        }
        if(Intersector.overlaps(circleBirde2, highPipeRectangle2) || Intersector.overlaps(circleBirde2, lowPipeRectangle2) || birdVerticalPosition <= 0){
            gameOver = true;
        }
	}

    @Override
    public void resize(int width, int height) {
	    viewport.update(width, height);
    }
}
