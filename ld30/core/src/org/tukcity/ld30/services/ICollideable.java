package org.tukcity.ld30.services;

import com.badlogic.gdx.math.Rectangle;
import org.tukcity.ld30.objects.status.CollisionStatus;

/**
 * Created by james on 8/23/14.
 */
public interface ICollideable {

    public Rectangle getRect();

    public float getTop();

    public float getBottom();

    public float getLeft();

    public float getRight();

    public void onCollisionTop();

    public void onCollisionBottom();

    public void onCollisionLeft();

    public void onCollisionRight();

    public CollisionStatus getCollisionStatus();

    public void setCollisionStatus(CollisionStatus status);
}
