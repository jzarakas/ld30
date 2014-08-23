package org.tukcity.ld30;

/**
 * Created by james on 8/23/14.
 */
public class World {

    private float modifier;
    private float time;
    private float cameraModifier;

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


}
