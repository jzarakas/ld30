package org.tukcity.ld30.editor;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.google.gson.Gson;

import java.util.LinkedList;
import java.util.List;

public class Editor extends ApplicationAdapter {

    public class Rect {
        public float x;
        public float y;
        public float w;
        public float h;

        public Rect() {}

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
	public void create () {

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
                Vector3 v = camera.unproject(new Vector3(screenX, screenY,0));
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
	}

	@Override
	public void render () {
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
}
