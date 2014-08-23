package org.tukcity.ld30.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import org.tukcity.ld30.World;

/**
 * Created by james on 8/23/14.
 */
public class WObject {

    protected float x;
    protected float y;

    private ObjectStatus currentStatus;
    private ObjectStatus lastStatus;

    private Rectangle cRect; //collision rectangle

    private Texture mTexture;

    public WObject(Texture texture, float x, float y) {
        mTexture = texture;
        this.x = x;
        this.y = y;

        setStatus(ObjectStatus.NORMAL);

        cRect = new Rectangle(x, y, mTexture.getWidth(), mTexture.getHeight());
    }

    public void draw(SpriteBatch sb) {
        sb.draw(mTexture, x, y);
    }

    public void update(float delta, World world) {
        //unused but rfu
        cRect.x = x;
        cRect.y = y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void incX(float x) {
        this.x += x;
    }

    public void incY(float y) {
        this.y += y;
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
}
