package org.tukcity.ld30.services;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import org.tukcity.ld30.World;
import org.tukcity.ld30.objects.WObject;
import org.tukcity.ld30.objects.WaterWorld;
import org.tukcity.ld30.objects.status.CollisionStatus;
import org.tukcity.ld30.objects.status.JumpStatus;

import java.util.List;

/**
 * Created by james on 8/23/14.
 */
public final class CollisionService {

    private CollisionService() {
    }

    public static void update(float delta, World world, List<? extends ICollideable> objects, WObject player) {
        //collision checks?
        //we really need to save 2 iterations, but this works for the moment
        //we might be able to break out of the loop once we hit objects that are > 300 away
        //but we have to worry about objects behind us.
        boolean foundCollision = false;

        player.setCollisionStatus(CollisionStatus.NONE);
        int len = 50;
        if (len > objects.size()) len = objects.size();

        for (int i = 0; i < len; i++) {
            checkCollision(objects.get(i), player, world);
        }

        if (player.getCollisionStatus() == CollisionStatus.NONE && player.getJumpStatus() == JumpStatus.NONE)
            player.setJumpStatus(JumpStatus.DOWN);

        if (player.getCollisionStatus() == CollisionStatus.BOTTOM && player.getJumpStatus() == JumpStatus.DOWN)
            player.setJumpStatus(JumpStatus.NONE);

    }

    private static void checkCollision(ICollideable o, WObject player, World world) {

        if (World.getDistance(player, o) < 4000) { //close enough to check collision

            //ok we have a collision.
            if (isColliding(player, o)) {

                if (o instanceof WObject && world instanceof WaterWorld) {
                    WObject w = (WObject) o;
                    if (w.isBubble()) {
                        ((WaterWorld) world).addBreath(20);
                        w.disable();
                        return;
                    }
                }

                //see if the player's bottom is on top of a collider
                if (player.getBottom() < (o.getTop())) { //yep.
                    player.setY(o.getTop());

                    //if we're on the down portion of our jump, stop.
                    if (player.getJumpStatus() == JumpStatus.DOWN) {
                        player.setJumpStatus(JumpStatus.NONE);
                    }
                    player.setCollisionStatus(CollisionStatus.BOTTOM);
                    return;
                }

            }

        }

    }

    private static boolean isColliding(WObject player, ICollideable b) {
        return Intersector.intersectRectangles(player.getRect(), b.getRect(), Rectangle.tmp);
    }

}
