package org.tukcity.ld30.objects;

import com.badlogic.gdx.graphics.Texture;
import org.tukcity.ld30.World;

/**
 * Created by james on 8/23/14.
 */
public class VerticalMovingObject extends WObject {

    private float yMax = 50f;
    private State currentState = State.Up;
    private float velocity;

    private float currentTime;

    private enum State {Up, Down}

    public VerticalMovingObject(Texture texture, float x, float y, float velocity, float time) {

        super(texture, x, y);

        this.velocity = velocity;

        yMax = time;
    }

    @Override
    public void update(float delta, World world) {

        currentTime += delta;

        if (currentState == State.Up) {
            if (currentTime >= yMax) {
                currentState = State.Down;
                currentTime = 0f;
                y -= delta * velocity * world.getModifier();
            } else {
                y += delta * velocity * world.getModifier();
            }
        } else if (currentState == State.Down) {
            if ( currentTime >= yMax) {
                currentState = State.Up;
                currentTime = 0f;
                y += delta * velocity * world.getModifier();
            } else {
                y -= delta * velocity * world.getModifier();
            }
        }

        super.update(delta, world);
    }


    @Override
    public void onCollisionTop() {
        super.onCollisionTop();

        currentState = State.Down;
        currentTime = 0f;
    }

    @Override
    public void onCollisionBottom() {
        super.onCollisionBottom();

        currentState = State.Up;
        currentTime = 0f;
    }
}
