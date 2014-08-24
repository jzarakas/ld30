package org.tukcity.ld30.services;

import org.tukcity.ld30.World;
import org.tukcity.ld30.objects.WObject;
import org.tukcity.ld30.objects.status.JumpStatus;

/**
 * Created by james on 8/23/14.
 */
public final class JumpService {
    private static final float MAX_JUMP_TIME = 0.75f;
    private static final float JUMP_VELOCITY = 100f;
    private static final float MAX_JUMP_PEAK_TIME = 0f;

    private static float elapsedJumpTime = 0f;


    private JumpService() {
    }

    public static void reset() {
        elapsedJumpTime = 0f;
    }

    @Deprecated
    public static void add(WObject player) {
        if (player.getJumpStatus() != JumpStatus.NONE) {
            player.setJumpStatus(JumpStatus.UP);
        }
    }

    public static void update(float delta, World world, WObject player) {
        //if (player.getCollisionStatus() != CollisionStatus.TOP) {

        if (player.getJumpStatus() != JumpStatus.NONE)
            elapsedJumpTime += delta;
//            System.out.println("Starting jump");
//            System.out.println("-------------");
        if (player.getJumpStatus() == JumpStatus.UP) {
            player.incY(delta * JUMP_VELOCITY);
            //System.out.println("going up");

            if (elapsedJumpTime >= MAX_JUMP_TIME) {
                //System.out.println("resetting status");
                player.setJumpStatus(JumpStatus.DOWN);
                //change to JUMP_PEAK to enable the peak bit
                elapsedJumpTime = 0f;
            }
        } else if (player.getJumpStatus() == JumpStatus.DOWN) {
            //System.out.println("going down");

            player.incY(-delta * JUMP_VELOCITY);

            if (elapsedJumpTime >= MAX_JUMP_TIME) {
                if (player.getY() > 0f) {
                    elapsedJumpTime = 0f;
                } else {
                    player.setJumpStatus(JumpStatus.NONE);
                    elapsedJumpTime = 0f;
                    player.setY(0);
                }
            }
        }
        //}
    }
}
