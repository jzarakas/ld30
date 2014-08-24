package org.tukcity.ld30.services;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import org.tukcity.ld30.World;
import org.tukcity.ld30.objects.PlayerObject;
import org.tukcity.ld30.objects.WObject;
import org.tukcity.ld30.objects.status.JumpStatus;

/**
 * Created by james on 8/23/14.
 */
public final class InputService {

    private InputService() {
    }

    public static void update(float delta, World world, WObject player) {
        PlayerObject p = (PlayerObject)player;
        p.isStanding = true;

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            p.incX(delta * world.getPlayerVelocity() * world.getModifier());
            p.isStanding = false;

        }

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            p.incX(-delta * world.getPlayerVelocity() * world.getModifier());
            p.isStanding = false;

        }

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            p.incY(delta * world.getPlayerVelocity() * world.getModifier() * 2f);
            p.isStanding = false;

        }

        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            p.incY(-delta * world.getPlayerVelocity() * world.getModifier());
            p.isStanding = false;

        }

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && (player.getJumpStatus() != JumpStatus.UP)) {
            p.setJumpStatus(JumpStatus.UP);
            System.out.println("jump");
            p.isStanding = false;

        }


        //camera scroll speed
        if (Gdx.input.isKeyPressed(Input.Keys.EQUALS)) {
            world.incCameraVelocity(delta * 10);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.MINUS)) {
            world.incCameraVelocity(-delta * 10);
        }

        //camera modifier
        if (Gdx.input.isKeyPressed(Input.Keys.SEMICOLON)) {
            world.incCameraModifier(-delta * 10);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.APOSTROPHE)) {
            world.incCameraModifier(delta * 10);
        }

        //player velocity modifier
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT_BRACKET)) {
            world.incPlayerVelocity(-delta * 10);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT_BRACKET)) {
            world.incPlayerVelocity(delta * 10);
        }

    }
}
