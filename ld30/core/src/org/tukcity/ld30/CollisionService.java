package org.tukcity.ld30;

import org.tukcity.ld30.objects.ObjectStatus;
import org.tukcity.ld30.objects.WObject;

import java.util.List;

/**
 * Created by james on 8/23/14.
 */
public class CollisionService {

    private CollisionService() { }

    public static void update(float delta, World world, List<WObject> objects, WObject player) {
        //collision checks?
        //we really need to save 2 iterations, but this works for the moment
        //we might be able to break out of the loop once we hit objects that are > 300 away
        //but we have to worry about objects behind us.
        boolean foundCollision = false;
        boolean onTop = false;
        WObject o;

        int len = 50;
        if (len > objects.size()) len = objects.size();
        for (int i = 0; i < len; i++) {

            o = objects.get(i);
            if (World.getDistance(player, o) < 40) { //close enough to check collision

                if (World.isColliding(player, o)) {
                    //checks to see if player is on top of object

                    if (player.getY() > 0 && player.getStatus() != ObjectStatus.JUMPING) {
                        //System.out.println("found collision: " + playerRect + " -- " + o.getRect() + " -- " + Rectangle.tmp);

                        player.setY(o.getTop() - 2);
                        if (player.getY() >= o.getTop() - 2) {
                            player.setStatus(ObjectStatus.COLLIDING);
                            //y = o.getY() + 32;
                            player.setY(o.getTop() - 2);
                            foundCollision = true;
                            onTop = true;
                            o.onCollisionTop();
                        }
                    }


                    //check to see if player is hitting bottom of object
//                    if (y > o.getBottom()) {
//                        o.onCollisionBottom();
//                        while (y > o.getBottom())
//                            y--;
//                    }
                    //checks to see if player is to left of object
                    //TODO fix this. doesn't work for ground level blocks
                    if (player.getRight() > o.getLeft() ) {
                        o.onCollisionLeft();
                        while ((player.getX() + player.getRect().getWidth()) >= o.getLeft() && player.getStatus() == ObjectStatus.NORMAL) {
                            player.incX(-1);
                            System.out.println("x: " + player.getX());
                        }
                    }

                    //check to see if player is to right of object
                    if (player.getLeft() < o.getRight() && player.getStatus() == ObjectStatus.NORMAL) {
                        o.onCollisionRight();
                        while (player.getX() <= o.getRight())
                            player.incX(1);
                    }


                }
            }
        }

        if (!foundCollision) {
            if (player.getStatus() == ObjectStatus.COLLIDING) {
                player.setStatus(ObjectStatus.POST_JUMP);
                //y = 0;
                //System.out.println("collision gone");
            }
        }
    }
}
