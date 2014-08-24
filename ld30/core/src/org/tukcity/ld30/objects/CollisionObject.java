package org.tukcity.ld30.objects;

import com.badlogic.gdx.math.Rectangle;
import org.tukcity.ld30.services.ICollideable;
import org.tukcity.ld30.objects.status.CollisionStatus;
import org.tukcity.ld30.utils.LevelBuilder;

/**
 * Created by james on 8/23/14.
 */
public final class CollisionObject implements ICollideable {

    private Rectangle rect;
    private CollisionStatus collisionStatus;

    public CollisionObject(Rectangle r) {
        rect = new Rectangle(r);
    }

    public CollisionObject(float x, float y, float w, float h) {
        rect = new Rectangle(x, y, w, h);
    }

    public CollisionObject(LevelBuilder.RectWrapper wrapper) {
        if (wrapper.h < 10f) wrapper.h = 10f;
        rect = new Rectangle(wrapper.x, wrapper.y, wrapper.w, wrapper.h);
    }

    @Override
    public Rectangle getRect() {
        return rect;
    }

    @Override
    public float getTop() {
        return rect.y + rect.height;
    }

    @Override
    public float getBottom() {
        return rect.y;
    }

    @Override
    public float getLeft() {
        return rect.x;
    }

    @Override
    public float getRight() {
        return rect.x + rect.width;
    }

    @Override
    public void onCollisionTop() {

    }

    @Override
    public void onCollisionBottom() {

    }

    @Override
    public void onCollisionLeft() {

    }

    @Override
    public void onCollisionRight() {

    }

    @Override
    public CollisionStatus getCollisionStatus() {
        return collisionStatus;
    }

    @Override
    public void setCollisionStatus(CollisionStatus status) {
        collisionStatus = status;
    }
}
