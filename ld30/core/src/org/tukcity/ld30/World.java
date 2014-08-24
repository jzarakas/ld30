package org.tukcity.ld30;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.sun.org.apache.bcel.internal.generic.ICONST;
import org.tukcity.ld30.objects.WObject;

/**
 * Created by james on 8/23/14.
 */
public class World {

    private float modifier;
    private float time;
    private float cameraModifier;
    private float playerVelocity = 75f;
    private float cameraVelocity = 20f;

    public World(float modifier, float time, float cameraModifier) {
        this.modifier = modifier;
        this.time = time;
        this.cameraModifier = cameraModifier;
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

}
