package org.tukcity.ld30;

import org.tukcity.ld30.objects.ObjectStatus;
import org.tukcity.ld30.objects.WObject;

/**
 * Created by james on 8/23/14.
 */
public class JumpService {
    private static final float MAX_JUMP_TIME = 0.75f;
    private static final float JUMP_VELOCITY = 100f;
    private static final float MAX_JUMP_PEAK_TIME = 0f;

    private static float elapsedJumpTime;



    private JumpService() {
    }

    public static void add(WObject player) {
        if (player.getStatus() == ObjectStatus.NORMAL || player.getStatus() == ObjectStatus.COLLIDING) {
            player.setStatus(ObjectStatus.JUMPING);
        }
    }

    public static void update(float delta, World world, WObject player) {
        if (player.getStatus() != ObjectStatus.NORMAL && player.getStatus() != ObjectStatus.COLLIDING) {
            elapsedJumpTime += delta;

            if (player.getStatus() == ObjectStatus.JUMPING) {
                player.incY(delta * JUMP_VELOCITY);


                if (elapsedJumpTime >= MAX_JUMP_TIME) {
                    player.setStatus(ObjectStatus.POST_JUMP);
                    //change to JUMP_PEAK to enable the peak bit
                    elapsedJumpTime = 0f;
                }
            } else if (player.getStatus() == ObjectStatus.POST_JUMP) {
                player.incY(-delta * JUMP_VELOCITY);

                if (player.getY() < 0)
                    player.setY(0);

                if (elapsedJumpTime >= MAX_JUMP_TIME) {
                    if (player.getY() > 0f) {
                        elapsedJumpTime = 0f;
                    } else {
                        player.setStatus(ObjectStatus.NORMAL);
                        elapsedJumpTime = 0f;
                        player.setY(0);
                    }
                }
            } else if (player.getStatus() == ObjectStatus.JUMP_PEAK) {
                player.incX(delta * world.getPlayerVelocity());

                if (elapsedJumpTime >= MAX_JUMP_PEAK_TIME) {
                    player.setStatus(ObjectStatus.POST_JUMP);
                    elapsedJumpTime = 0f;
                }
            }
        }
    }

}
