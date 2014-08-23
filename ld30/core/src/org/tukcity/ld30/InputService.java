package org.tukcity.ld30;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import org.tukcity.ld30.objects.ObjectStatus;
import org.tukcity.ld30.objects.WObject;

/**
 * Created by james on 8/23/14.
 */
public final class InputService {

    private InputService() {
    }

    public static void update(float delta, World world, WObject player) {
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            player.incX(delta * world.getPlayerVelocity() * world.getModifier());
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            player.incX(-delta * world.getPlayerVelocity() * world.getModifier());
        }

//        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
//            y += delta*velocity*modifier;
//        }

//        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
//            y -= delta*velocity*modifier;
//        }

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && (player.getStatus() == ObjectStatus.NORMAL || player.getStatus() == ObjectStatus.COLLIDING)) {
            player.setStatus(ObjectStatus.JUMPING);
            System.out.println("jump");
            //JumpService.add(player);
        }


        //camera scroll speed
        if (Gdx.input.isKeyPressed(Input.Keys.EQUALS)) {
            world.incCameraVelocity(delta * 10);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.MINUS)) {
            world.incCameraVelocity(-delta * 10);
        }

    }
}
