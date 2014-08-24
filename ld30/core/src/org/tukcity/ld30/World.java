package org.tukcity.ld30;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import org.tukcity.ld30.objects.CollisionObject;
import org.tukcity.ld30.objects.WObject;
import org.tukcity.ld30.services.ICollideable;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by james on 8/23/14.
 */
public class World {

    private float modifier;
    private float time;
    private float cameraModifier;
    private float playerVelocity = 75f;
    private float cameraVelocity = 20f;
    private static boolean debugRenderer = false;

    protected float startx;
    protected float starty;

    protected final List<WObject> objs = new LinkedList<WObject>();
    protected final List<WObject> oldObjs = new LinkedList<WObject>();
    protected List<CollisionObject> colliders = new LinkedList<CollisionObject>();

    protected WObject player;



    protected final HashMap<String, Texture> textures = new HashMap<String, Texture>();


    public World(float modifier, float time, float cameraModifier) {
        this.modifier = modifier;
        this.time = time;
        this.cameraModifier = cameraModifier;

        textures.put("player", new Texture("red.png"));
    }

    public float getModifier() {
        return modifier;
    }

    public float getTime() {
        return time;
    }

    public float getCameraModifier() {
        return cameraModifier;
    }

    public float getPlayerVelocity() {
        return playerVelocity;
    }

    public void incCameraVelocity(float v) {
        cameraVelocity += v;
    }

    public float getCameraVelocity() {
        return cameraVelocity;
    }

    public static float getDistance(WObject a, ICollideable b) {
        return Vector2.dst(a.getX(), a.getY(), b.getRect().x, b.getRect().y);
    }

    public void incPlayerVelocity(float v) {
        playerVelocity += v;
    }

    public void incCameraModifier(float v) {
        cameraModifier += v;
    }

    public static void toggleDebugRenderer() {
        debugRenderer = !debugRenderer;
    }

    public static boolean getDebugRenderer() {
        return debugRenderer;
    }

    public void draw(SpriteBatch sb) {
    }

    public List<CollisionObject> getColliders() {
        return colliders;
    }

    public float getStartX() {
        return startx;
    }

    public float getStartY() {
        return starty;
    }

    public List<WObject> getObjects() {
        return objs;
    }

    public void drawObjects(SpriteBatch sb) {
        for (WObject o : objs) {
            o.draw(sb);
        }
    }

    public void spawnPlayer() {
        player = new WObject(textures.get("player"), startx, starty);
}

    public WObject getPlayer() {
        return player;
    }
}


