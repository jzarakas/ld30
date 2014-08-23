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

    private enum State {Up, Down}

    ;

    public VerticalMovingObject(Texture texture, float x, float y, float velocity) {

        super(texture, x, y);

        this.velocity = velocity;
    }

    @Override
    public void update(float delta, World world) {
        if (currentState == State.Up) {
            if (y >= yMax) {
                currentState = State.Down;
                y -= delta * velocity * world.getModifier();
            } else {
                y += delta * velocity * world.getModifier();
            }
        } else if (currentState == State.Down) {
            if (y <= 0) {
                currentState = State.Up;
                y += delta * velocity * world.getModifier();
            } else {
                y -= delta * velocity * world.getModifier();
            }
        }

        super.update(delta, world);
    }

}
