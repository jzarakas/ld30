package org.tukcity.ld30;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class LDGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
    ShapeRenderer shapeRenderer;

    OrthographicCamera camera;
    OrthographicCamera staticCamera;

    float x = -64;
    float y = 0;
    float velocity = 75.0f;
    float elapsedTime = 0;

    float cameraVelocity = 20f;

    final float maxJumpTime = .75f;
    float elapsedJumpTime = 0.0f;
    float jumpVelocity = 128.0f;

    float elapsedPeakTime = 0f;
    final float maxJumpPeakTime = .4f;

    boolean isColliding = false;

    World currentWorld = new World(1.0f, 0, 1.0f);
    World pastWorld = currentWorld;

    Rectangle playerRect;

    Texture ground;

    PlayerStatus playerStatus = PlayerStatus.NORMAL;
    PlayerStatus lastStatus = playerStatus;

    Music music;

    List<WObject> objs = new LinkedList<WObject>();
    List<WObject> oldObjs = new LinkedList<WObject>();

    Queue<World> shifts = new LinkedList<World>();

    FPSLogger fpsLogger = new FPSLogger();



    @Override
	public void create () {

		batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
		img = new Texture("red.png");

        playerRect = new Rectangle(x, y, img.getWidth(), img.getHeight());
        camera  = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        staticCamera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        staticCamera.position.x = 0;
        staticCamera.position.y = 0;
        staticCamera.setToOrtho(false);
        camera.position.x = 0;
        camera.position.y = 0;

        music = Gdx.audio.newMusic(Gdx.files.internal("symphony.mp3"));
        music.play();

        ground = new Texture("ground.png");
        Texture textures[] = {new Texture("blue.png"), new Texture("orange.png"), new Texture("purple.png"),
                new Texture("green.png")};

        int spacing = 128*textures.length + 196;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < textures.length; j++) {
                WObject tmp;
//                if (i % 5 == 0){
//                    tmp = new HorizontalMovingObject(textures[j], i * spacing + j * 196, 0);
//                } else if (i % 6 == 0) {
//                    tmp = new VerticalMovingObject(textures[j], i * spacing + j * 196, 0);
//                } else {
//                    tmp = new WObject(textures[j], i * spacing + j * 196, 0);
//                }
                tmp = new WObject(textures[j], i * spacing + j * 196, 0);
                objs.add(tmp);
            }
        }

        shifts.add(new World(1.0f, 17.3f, 0.5f));
        shifts.add(new World(2.0f, 22.5f, 1.5f));
        shifts.add(new World(1.0f, 29.9f, 0.5f));
        shifts.add(new World(2.0f,34.8f, 1.5f));
        shifts.add(new World(1.0f, 36.2f, 0.5f));
        shifts.add(new World(1.0f, 46.2f, 1.5f));
        shifts.add(new World(1.0f, 51.1f, 1.0f));
        shifts.add(new World(1.0f, 56.3f, 1.0f));
        shifts.add(new World(1.0f, 57.8f, 1.0f));
        shifts.add(new World(1.0f, 73.1f, 1.0f));
        shifts.add(new World(1.0f, 81.4f, 1.0f));

    }


	@Override
	public void render () {

        fpsLogger.log();
        update(Gdx.graphics.getDeltaTime());


		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(img, x, y);

        for (WObject o : objs) {
            //cull rendering here?
            o.draw(batch);


        }


        batch.end();



        batch.setProjectionMatrix(staticCamera.combined);
        batch.begin();
        batch.draw(ground, 0, 0);
        batch.end();

        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.YELLOW);

        for (WObject o : objs) {
            shapeRenderer.rect(o.getRect().x, o.getRect().y, o.getRect().width, o.getRect().height);
        }

        shapeRenderer.rect(playerRect.x, playerRect.y, playerRect.width, playerRect.height);

        shapeRenderer.end();




        //System.out.println(x + ", " + y + " -- " + camera.position);
	}

    @Override
    public void dispose() {
        music.dispose();
    }

    public void update(float delta) {

       elapsedTime += delta;
       //System.out.println("t: " + elapsedTime);

       if (shifts.peek() != null && shifts.peek().getTime() <= elapsedTime) {
           pastWorld = currentWorld;
           currentWorld = shifts.poll();
           System.out.println("world shift");
       }

       camera.translate(delta*cameraVelocity*currentWorld.getCameraModifier(), 0);

       handleInput(delta);

        playerRect.x = x;
        playerRect.y = y;

        if (playerStatus != PlayerStatus.NORMAL && playerStatus != PlayerStatus.COLLIDING) {
            elapsedJumpTime += delta;

            if (playerStatus == PlayerStatus.JUMPING) {
                y += delta * jumpVelocity;


                if (elapsedJumpTime >= maxJumpTime) {
                    switchStatus(PlayerStatus.POST_JUMP);
                    //change to JUMP_PEAK to enable the peak bit
                    elapsedJumpTime = 0f;
                }
            } else if (playerStatus == PlayerStatus.POST_JUMP) {
                y -= delta * jumpVelocity;

                if (y < 0)
                    y = 0;

                if (elapsedJumpTime >= maxJumpTime) {
                    switchStatus(PlayerStatus.NORMAL);
                    elapsedJumpTime = 0f;
                    y = 0f;
                }
            } else if (playerStatus == PlayerStatus.JUMP_PEAK) {
                x += delta * velocity;

                if (elapsedJumpTime >= maxJumpPeakTime) {
                    switchStatus(PlayerStatus.POST_JUMP);
                    elapsedJumpTime = 0f;
                }
            }
        }

        for (WObject o : objs) {

            //clean this
            if (o.getX() < (camera.position.x - (Gdx.graphics.getWidth()/2f))) {
                oldObjs.add(o);
            } else {
                o.update(delta, currentWorld);
            }
        }

        objs.removeAll(oldObjs);
        oldObjs.clear();

        //System.out.println("objs: " + objs.size());

        //collision checks?
        //we really need to save 2 iterations, but this works for the moment
        //we might be able to break out of the loop once we hit objects that are > 300 away
        //but we have to worry about objects behind us.
        boolean foundCollision = false;
        WObject o;
        for (int i = 0; i < 5; i++) {

            o = objs.get(i);
            if (Vector2.dst(x, y, o.getX(), o.getY()) < 40) { //close enough to check collision
                if (playerRect.overlaps(o.getRect()) && y > 0 ) {
                    //System.out.println("found collision: " + playerRect + " -- " + o.getRect());

                    if (y >= 29) {
                        switchStatus(PlayerStatus.COLLIDING);
                        //y = o.getY() + 32;
                        y = 30;
                        foundCollision = true;
                    }

                }
            }
        }

        if (!foundCollision) {
            if (playerStatus == PlayerStatus.COLLIDING) {
                switchStatus(PlayerStatus.NORMAL);
                y = 0;
                System.out.println("collision gone");
            }
        }

    }

    public void switchStatus(PlayerStatus nStatus) {
        lastStatus = playerStatus;
        playerStatus = nStatus;
    }

    public void handleInput(float delta) {

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            x += delta*velocity*currentWorld.getModifier();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            x -= delta*velocity*currentWorld.getModifier();
        }

//        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
//            y += delta*velocity*modifier;
//        }

//        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
//            y -= delta*velocity*modifier;
//        }

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) &&  (playerStatus == PlayerStatus.NORMAL || playerStatus == PlayerStatus.COLLIDING)) {
            switchStatus(PlayerStatus.JUMPING);
        }


        //camera scroll speed
        if (Gdx.input.isKeyPressed(Input.Keys.EQUALS)) {
            cameraVelocity += delta*10;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.MINUS)) {
            cameraVelocity -= delta*10;
        }

    }
}
