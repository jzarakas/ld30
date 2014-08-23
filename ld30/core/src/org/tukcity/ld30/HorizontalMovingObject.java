package org.tukcity.ld30;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by james on 8/23/14.
 */
public class HorizontalMovingObject extends WObject {

    private float xMax = 50f;
    private float currentX = 0;
    private State currentState = State.Left;

    private enum State { Left, Right};

    public HorizontalMovingObject(Texture texture, float x, float y) {
        super(texture, x, y);
    }

    @Override
    public void update(float delta, World world) {
        if (currentState == State.Left) {
            if (currentX >= xMax) {
                currentState = State.Right;
                x -= delta * world.getModifier();
                currentX = 0;
            } else {
                x += delta * world.getModifier();
                currentX += delta;

            }
        } else if (currentState == State.Right) {
            if (currentX >= xMax) {
                currentState = State.Left;
                x -= delta * world.getModifier();
                currentX = 0;

            } else {
                x += delta * world.getModifier();
                currentX += delta * world.getModifier();

            }
        }

        super.update(delta, world);
    }

}
