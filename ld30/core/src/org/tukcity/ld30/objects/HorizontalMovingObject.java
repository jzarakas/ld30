package org.tukcity.ld30.objects;

import com.badlogic.gdx.graphics.Texture;
import org.tukcity.ld30.World;

/**
 * Created by james on 8/23/14.
 */
public class HorizontalMovingObject extends WObject {

    private  float xMax = 50f;
    private State currentState = State.Left;
    private float velocity;

    private float currentTime;


    private enum State {Left, Right}

    public HorizontalMovingObject(Texture texture, float x, float y, float velocity, float time) {
        super(texture, x, y);

        this.velocity = velocity;
        xMax = time;
    }

    @Override
    public void update(float delta, World world) {
        currentTime += delta;
        if (currentState == State.Left) {
            if (currentTime >= xMax) {
                currentState = State.Right;
                x -= delta * velocity * world.getModifier();
                currentTime = 0;
            } else {
                x += delta * velocity * world.getModifier();
            }
        } else if (currentState == State.Right) {
            if (currentTime >= xMax) {
                currentState = State.Left;
                x -= delta * velocity * world.getModifier();
                currentTime = 0;

            } else {
                x += delta * velocity * world.getModifier();

            }
        }

        super.update(delta, world);
    }

    @Override
    public void onCollisionRight() {
        super.onCollisionRight();

        currentState = State.Left;
        currentTime = 0;
    }

    @Override
    public void onCollisionLeft() {
        super.onCollisionLeft();

        currentState = State.Right;
        currentTime = 0;
    }
}
