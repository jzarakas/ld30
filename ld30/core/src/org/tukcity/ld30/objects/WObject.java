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

    protected float startx;
    protected float starty;

    float x;
    protected float y;

    private ObjectStatus currentStatus;
    private ObjectStatus lastStatus;

    private JumpStatus jumpStatus;
    private CollisionStatus collisionStatus;

    private Rectangle cRect; //collision rectangle

    final private Texture mTexture;

    private float xoff = 0;
    private float yoff = 0;


    public WObject(Texture texture, float x, float y) {
        mTexture = texture;
        this.x = x;
        this.y = y;



        setStatus(ObjectStatus.NORMAL);
        setJumpStatus(JumpStatus.NONE);
        setCollisionStatus(CollisionStatus.NONE);

        cRect = new Rectangle(x, y, mTexture.getWidth(), mTexture.getHeight());

    }

    public void setCollisionDimensions(float x, float y, float w, float h) {
        yoff = mTexture.getHeight() - yoff - h;
        xoff = x;
        System.out.println(yoff);
        cRect = new Rectangle(this.x + x, this.y + yoff, w, h);
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

        cRect.x = x + xoff;
        cRect.y = y + yoff;
        System.out.println(yoff);
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
}
