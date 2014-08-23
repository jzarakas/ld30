package org.tukcity.ld30.objects;

import com.badlogic.gdx.graphics.Texture;
import org.tukcity.ld30.World;

/**
 * Created by james on 8/23/14.
 */
public class HorizontalMovingObject extends WObject {

    private final float xMax = 50f;
    private float currentX = 0;
    private State currentState = State.Left;
    private float velocity;


    private enum State {Left, Right}

    public HorizontalMovingObject(Texture texture, float x, float y, float velocity) {
        super(texture, x, y);

        this.velocity = velocity;
    }

    @Override
    public void update(float delta, World world) {
        if (currentState == State.Left) {
            if (currentX >= xMax) {
                currentState = State.Right;
                x -= delta * velocity * world.getModifier();
                currentX = 0;
            } else {
                x += delta * velocity * world.getModifier();
                currentX += delta;

            }
        } else if (currentState == State.Right) {
            if (currentX >= xMax) {
                currentState = State.Left;
                x -= delta * velocity * world.getModifier();
                currentX = 0;

            } else {
                x += delta * world.getModifier();
                currentX += delta * velocity * world.getModifier();

            }
        }

        super.update(delta, world);
    }

}
