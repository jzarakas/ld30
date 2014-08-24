package org.tukcity.ld30.editor;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.google.gson.Gson;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Editor extends ApplicationAdapter {

    public class Rect {
        public float x;
        public float y;
        public float w;
        public float h;

        public Rect() {
        }

        public void config(Vector2[] points) {
            x = points[0].x;
            y = points[0].y;
            w = points[1].x - points[0].x;
            h = points[1].y - points[0].y;
        }
    }

    SpriteBatch batch;
    Texture img;

    List<Rect> rectList = new LinkedList<Rect>();

    Vector2[] points = new Vector2[2];

    OrthographicCamera camera;

    Rect currentRect = new Rect();
    int clicks = 0;


    @Override
    public void create() {

        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        batch = new SpriteBatch();
        img = new Texture("mountains/foreground.png");
        Gdx.input.setInputProcessor(new InputProcessor() {
            @Override
            public boolean keyDown(int keycode) {
                return false;
            }

            @Override
            public boolean keyUp(int keycode) {
                if (keycode == Input.Keys.P) {
                    Gson g = new Gson();

                    FileHandle fileHandle = Gdx.files.local("mountain.json");
                    fileHandle.writeString(g.toJson(rectList), false);
                    System.out.println(g.toJson(rectList));
                }
                return true;
            }

            @Override
            public boolean keyTyped(char character) {
                return false;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                return false;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                Vector3 v = camera.unproject(new Vector3(screenX, screenY, 0));
                System.out.println(screenX + "," + screenY);
                System.out.println(v.x + ", " + v.y);
                System.out.println("--------------------");

                if (clicks == 2) {
                    System.out.println("got rect");
                    //we're oob
                    currentRect.config(points);
                    rectList.add(currentRect);
                    currentRect = new Rect();

                    points[0] = new Vector2(v.x, v.y);
                    clicks = 1;
                } else {
                    System.out.println("got point " + clicks);
                    points[clicks] = new Vector2(v.x, v.y);
                    clicks++;
                }

                return true;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                return false;
            }

            @Override
            public boolean mouseMoved(int screenX, int screenY) {
                return false;
            }

            @Override
            public boolean scrolled(int amount) {
                camera.zoom += amount * 0.75f;

                if (camera.zoom < 0)
                    camera.zoom = 0;
                return true;
            }
        });

        exportLevel();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        float v = 180.0f;
        float d = Gdx.graphics.getDeltaTime();

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            camera.position.x -= v * d;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            camera.position.x += v * d;
        if (Gdx.input.isKeyPressed(Input.Keys.UP))
            camera.position.y += v * d;
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
            camera.position.y -= v * d;

        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(img, 0, 0);
        batch.end();
    }

    public void exportLevel() {

        Texture map = new Texture("mountains/alpha.png");
        //width and height in pixels
        int width = map.getWidth();
        int height = map.getHeight();

        //Create a SpriteBatch to handle the drawing.
        SpriteBatch sb = new SpriteBatch();

        //Set the projection matrix for the SpriteBatch.
        Matrix4 projectionMatrix = new Matrix4();

        //because Pixmap has its origin on the topleft and everything else in LibGDX has the origin left bottom
        //we flip the projection matrix on y and move it -height. So it will end up side up in the .png
        projectionMatrix.setToOrtho2D(0, -height, width, height).scale(1, -1, 1);

        //Set the projection matrix on the SpriteBatch
        sb.setProjectionMatrix(projectionMatrix);

        //Create a frame buffer.
        FrameBuffer fb = new FrameBuffer(Pixmap.Format.RGBA8888, width, height, false);

        //Call begin(). So all next drawing will go to the new FrameBuffer.
        fb.begin();

        //Set up the SpriteBatch for drawing.
        sb.begin();

        sb.draw(map, 0, 0);

        //End drawing on the SpriteBatch. This will flush() any sprites remaining to be drawn as well.
        sb.end();

        //Then retrieve the Pixmap from the buffer.
        Pixmap pm = ScreenUtils.getFrameBufferPixmap(0, 0, width, height);

        //Close the FrameBuffer. Rendering will resume to the normal buffer.
        fb.end();

        System.out.println(pm.getWidth() + ", " + pm.getHeight());

        //Save the pixmap as png to the disk.
        FileHandle levelTexture = Gdx.files.local("levelTexture.png");
        //PixmapIO.writePNG(levelTexture, pm);

        //Dispose of the resources.
        fb.dispose();
        sb.dispose();
    }
}

