package org.tukcity.ld30;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import org.tukcity.ld30.objects.WObject;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class LDGame extends ApplicationAdapter {

    SpriteBatch batch;
    Texture img;
    ShapeRenderer shapeRenderer;

    OrthographicCamera camera;
    OrthographicCamera staticCamera;

    WObject player;
    float elapsedTime = 0;

    World currentWorld = new World(1.0f, 0, 1.0f);
    World pastWorld = currentWorld;

    Texture ground;

    Music music;

    List<WObject> objs = new LinkedList<WObject>();
    List<WObject> oldObjs = new LinkedList<WObject>();

    Queue<World> shifts = new LinkedList<World>();

    FPSLogger fpsLogger = new FPSLogger();


    @Override
    public void create() {

        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        img = new Texture("red.png");

        player = new WObject(img, -64, 0);

        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        staticCamera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        staticCamera.position.x = 0;
        staticCamera.position.y = 0;
        staticCamera.setToOrtho(false);
        camera.position.x = 0;
        camera.position.y = 0;

        music = Gdx.audio.newMusic(Gdx.files.internal("symphony.mp3"));
        //music.play();

        ground = new Texture("ground.png");
        Texture textures[] = {new Texture("blue.png"), new Texture("orange.png"), new Texture("purple.png"),
                new Texture("green.png")};

        int spacing = 128 * textures.length + 196;
        for (int i = 0; i < 10; i++) {
            WObject tmp;

            for (int j = 0; j < textures.length; j++) {
                tmp = new WObject(textures[j], i * spacing + j * 32, j * 32);
                objs.add(tmp);
            }

            for (int j = 0; j < 7; j++) {
                tmp = new WObject(textures[0], (4*32) + j*32, 96);
                objs.add(tmp);
            }

            for (int j = 0; j < textures.length; j++) {
                tmp = new WObject(textures[j], (11*32) + (j*32), 96 - (j*32));
                objs.add(tmp);
            }
        }

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

    }


    @Override
    public void render() {

        fpsLogger.log();


        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        player.draw(batch);

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

        shapeRenderer.rect(player.getRect().x, player.getRect().y, player.getRect().width, player.getRect().height);

        shapeRenderer.end();

        update(Gdx.graphics.getDeltaTime());


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

        camera.translate(delta * currentWorld.getCameraVelocity() * currentWorld.getCameraModifier(), 0);

        InputService.update(delta, currentWorld, player);
        JumpService.update(delta, currentWorld, player);

        player.update(delta, currentWorld);

        for (WObject o : objs) {

            //clean this
            if (o.getRight() < (camera.position.x - (Gdx.graphics.getWidth() / 2f))) {
                oldObjs.add(o);
            } else {
                o.update(delta, currentWorld);
            }
        }

        objs.removeAll(oldObjs);
        oldObjs.clear();

       CollisionService.update(delta, currentWorld, objs, player);

    }
}
