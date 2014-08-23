package org.tukcity.ld30;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by james on 8/23/14.
 */
public class VerticalMovingObject extends WObject {

    private float yMax = 50f;
    private State currentState = State.Up;

    private enum State { Up, Down};

    public VerticalMovingObject(Texture texture, float x, float y) {
        super(texture, x, y);
    }

    @Override
    public void update(float delta, World world) {
        if (currentState == State.Up) {
            if (y >= yMax) {
                currentState = State.Down;
                y -= delta * world.getModifier();
            } else {
                y += delta * world.getModifier();
            }
        } else if (currentState == State.Down) {
            if (y <= 0) {
                currentState = State.Up;
                y+= delta * world.getModifier();
            } else {
                y -= delta * world.getModifier();
            }
        }

        super.update(delta, world);
    }

}
