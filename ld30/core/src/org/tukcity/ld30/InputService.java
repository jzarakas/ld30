package org.tukcity.ld30;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import org.tukcity.ld30.objects.status.JumpStatus;
import org.tukcity.ld30.objects.status.ObjectStatus;
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

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            player.incY(delta * world.getPlayerVelocity() * world.getModifier() * 2f);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            player.incY(-delta * world.getPlayerVelocity() * world.getModifier());
        }

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && (player.getJumpStatus() == JumpStatus.NONE)) {
            player.setJumpStatus(JumpStatus.UP);
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
