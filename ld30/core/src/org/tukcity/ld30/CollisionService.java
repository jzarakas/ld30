package org.tukcity.ld30;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import org.tukcity.ld30.objects.WObject;
import org.tukcity.ld30.objects.status.CollisionStatus;
import org.tukcity.ld30.objects.status.JumpStatus;

import java.util.List;

/**
 * Created by james on 8/23/14.
 */
public class CollisionService {

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

             checkCollision(objects.get(i), player);

        }

        if (player.getCollisionStatus() == CollisionStatus.NONE && player.getJumpStatus() == JumpStatus.NONE)
            player.setJumpStatus(JumpStatus.DOWN);

    }

    public static void checkCollision(ICollideable o, WObject player) {

        if (World.getDistance(player, o) < 4000) { //close enough to check collision

            //ok we have a collision.
            if (isColliding(player, o)) {

                //see if the player's bottom is on top of a collider
                if (player.getBottom() < (o.getTop() - 1)) { //yep.
                    player.setY(o.getTop() - 1);

                    //if we're on the down portion of our jump, stop.
                    if (player.getJumpStatus() == JumpStatus.DOWN) {
                        player.setJumpStatus(JumpStatus.NONE);
                    }
                    player.setCollisionStatus(CollisionStatus.BOTTOM);
                }

            }

        }

    }

    private static boolean isColliding(WObject player, ICollideable b) {
        return Intersector.intersectRectangles(player.getRect(), b.getRect(), Rectangle.tmp);
    }

}
