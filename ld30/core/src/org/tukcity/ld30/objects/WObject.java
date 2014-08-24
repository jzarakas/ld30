package org.tukcity.ld30.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import org.tukcity.ld30.services.ICollideable;
import org.tukcity.ld30.services.JumpService;
import org.tukcity.ld30.World;
import org.tukcity.ld30.objects.status.CollisionStatus;
import org.tukcity.ld30.objects.status.JumpStatus;
import org.tukcity.ld30.objects.status.ObjectStatus;

/**
 * Created by james on 8/23/14.
 */
public class WObject implements ICollideable {

    float x;
    protected float y;

    private ObjectStatus currentStatus;
    private ObjectStatus lastStatus;

    private JumpStatus jumpStatus;
    private CollisionStatus collisionStatus;

    final private Rectangle cRect; //collision rectangle

    final private Texture mTexture;


    public WObject(Texture texture, float x, float y) {
        mTexture = texture;
        this.x = x;
        this.y = y;

        setStatus(ObjectStatus.NORMAL);
        setJumpStatus(JumpStatus.NONE);
        setCollisionStatus(CollisionStatus.NONE);

        cRect = new Rectangle(x, y, mTexture.getWidth(), mTexture.getHeight());
    }

    public void draw(SpriteBatch sb) {
        if (mTexture == null)
            return;
        sb.draw(mTexture, x, y);
    }

    public void update(float delta, World world) {
        //unused but rfu

        updateRect();
    }

    private void updateRect() {
        cRect.x = x;
        cRect.y = y;
    }


    public void setX(float x) {
        this.x = x;
        updateRect();

    }

    public void setY(float y) {
        this.y = y;
        updateRect();

    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void incX(float x) {
        this.x += x;
        updateRect();

    }

    public void incY(float y) {
        this.y += y;
        updateRect();

    }

    public Rectangle getRect() {
        return cRect;
    }

    public float getTop() {
        return cRect.y + cRect.height;
    }

    public float getBottom() {
        return cRect.y;
    }

    public float getLeft() {
        return cRect.x;
    }

    public float getRight() {
        return cRect.x + cRect.width;
    }

    public void onCollisionTop() {

    }

    public void onCollisionBottom() {

    }

    public void onCollisionLeft() {

    }

    public void onCollisionRight() {

    }

    public ObjectStatus getStatus() {
        return currentStatus;
    }

    public ObjectStatus getLastStatus() {
        return lastStatus;
    }

    public void setStatus(ObjectStatus status) {
        lastStatus = currentStatus;
        currentStatus = status;
    }

    public JumpStatus getJumpStatus() {
        return jumpStatus;
    }

    public void setJumpStatus(JumpStatus jumpStatus) {
        this.jumpStatus = jumpStatus;
        JumpService.reset();
    }

    public CollisionStatus getCollisionStatus() {
        return collisionStatus;
    }

    public void setCollisionStatus(CollisionStatus collisionStatus) {
        this.collisionStatus = collisionStatus;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WObject wObject = (WObject) o;

        if (Float.compare(wObject.x, x) != 0) return false;
        if (Float.compare(wObject.y, y) != 0) return false;
        if (!cRect.equals(wObject.cRect)) return false;
        if (currentStatus != wObject.currentStatus) return false;
        if (lastStatus != wObject.lastStatus) return false;
        if (!mTexture.equals(wObject.mTexture)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (x != +0.0f ? Float.floatToIntBits(x) : 0);
        result = 31 * result + (y != +0.0f ? Float.floatToIntBits(y) : 0);
        result = 31 * result + currentStatus.hashCode();
        result = 31 * result + lastStatus.hashCode();
        result = 31 * result + cRect.hashCode();
        result = 31 * result + mTexture.hashCode();
        return result;
    }
}
