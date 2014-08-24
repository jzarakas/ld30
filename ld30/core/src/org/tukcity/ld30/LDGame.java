package org.tukcity.ld30;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import org.tukcity.ld30.objects.CollisionObject;
import org.tukcity.ld30.objects.MountainWorld;
import org.tukcity.ld30.objects.WObject;
import org.tukcity.ld30.objects.WaterWorld;
import org.tukcity.ld30.services.CollisionService;
import org.tukcity.ld30.services.InputService;
import org.tukcity.ld30.services.JumpService;
import org.tukcity.ld30.utils.DebugInputProcessor;
import org.tukcity.ld30.utils.LevelBuilder;

import java.util.LinkedList;
import java.util.Queue;

public class LDGame extends ApplicationAdapter {

    SpriteBatch batch;
    Texture img;
    ShapeRenderer shapeRenderer;

    OrthographicCamera camera;
    OrthographicCamera staticCamera;

    float elapsedTime = 0;

    World currentWorld;
    World pastWorld = currentWorld;

    Music music;


    final Queue<World> shifts = new LinkedList<World>();

    final FPSLogger fpsLogger = new FPSLogger();


    DebugInputProcessor debugInputProcessor;

    WObject player;


    @Override
    public void create() {


        currentWorld = new WaterWorld(1.0f, 1.0f, 1.0f);


        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        img = new Texture("red.png");

        currentWorld.spawnPlayer();
        player = currentWorld.getPlayer();

        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        staticCamera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        staticCamera.position.x = 0;
        staticCamera.position.y = 0;
        staticCamera.setToOrtho(false);
        camera.position.x = 0;
        camera.position.y = 0;

        music = Gdx.audio.newMusic(Gdx.files.internal("symphony.mp3"));
        music.play();

        shifts.add(new World(1.0f, 17.3f, 0.5f));
        shifts.add(new World(2.0f, 22.5f, 1.5f));
        shifts.add(new World(1.0f, 29.9f, 0.5f));
        shifts.add(new World(2.0f, 34.8f, 1.5f));
        shifts.add(new World(1.0f, 36.2f, 0.5f));
        shifts.add(new World(1.0f, 46.2f, 1.5f));
        shifts.add(new World(1.0f, 51.1f, 1.0f));
        shifts.add(new World(1.0f, 56.3f, 1.0f));
        shifts.add(new World(1.0f, 57.8f, 1.0f));
        shifts.add(new World(1.0f, 73.1f, 1.0f));
        shifts.add(new World(1.0f, 81.4f, 1.0f));

        debugInputProcessor = new DebugInputProcessor(this, currentWorld);
        Gdx.input.setInputProcessor(debugInputProcessor);


    }


    @Override
    public void render() {

        //fpsLogger.log();


        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        //draw bg layers
        batch.setProjectionMatrix(staticCamera.combined);
        batch.begin();

        batch.end();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        currentWorld.draw(batch);

        currentWorld.drawObjects(batch);
        currentWorld.getPlayer().draw(batch);

        batch.end();


        batch.setProjectionMatrix(staticCamera.combined);
        batch.begin();
        //draw hud here
        batch.end();


        if (World.getDebugRenderer()) {
            shapeRenderer.setProjectionMatrix(camera.combined);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(Color.YELLOW);

            for (CollisionObject o : currentWorld.getColliders()) {
                shapeRenderer.rect(o.getRect().x, o.getRect().y, o.getRect().width, o.getRect().height);
            }

            for (WObject o : currentWorld.getObjects()) {
                shapeRenderer.rect(o.getRect().x, o.getRect().y, o.getRect().width, o.getRect().height);
            }


            shapeRenderer.rect(player.getRect().x, player.getRect().y, player.getRect().width, player.getRect().height);

            shapeRenderer.end();
        }

        update(Gdx.graphics.getDeltaTime());


        //System.out.println(x + ", " + y + " -- " + camera.position);
    }

    @Override
    public void dispose() {
        music.dispose();
    }

    public void update(float delta) {
        //System.out.println(player.getX() + "," + player.getY());

        elapsedTime += delta;
        //System.out.println("t: " + elapsedTime);

        if (shifts.peek() != null && shifts.peek().getTime() <= elapsedTime) {

            //shiftWorld(shifts.poll());
        }

        camera.translate(delta * currentWorld.getCameraVelocity() * currentWorld.getCameraModifier(), 0);
        camera.position.y = player.getY();
        JumpService.update(delta, currentWorld, player);
        InputService.update(delta, currentWorld, player);

        for (WObject o : currentWorld.getObjects()) {
            o.update(delta, currentWorld);
        }

        player.update(delta, currentWorld);

        CollisionService.update(delta, currentWorld, currentWorld.getObjects(), player);
        CollisionService.update(delta, currentWorld, currentWorld.getColliders(), player);
    }

    public void shiftWorld(World world) {
        pastWorld = currentWorld;
        currentWorld = world;
        debugInputProcessor.setWorld(world);
        player = currentWorld.getPlayer();
        System.out.println("world shift");
    }

    public OrthographicCamera getCamera() {
        return camera;
    }
}
