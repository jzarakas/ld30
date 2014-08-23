package org.tukcity.ld30;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by james on 8/23/14.
 */
public class WObject {

    protected float x;
    protected float y;

    private Rectangle cRect; //collision rectangle

    private Texture mTexture;

    public WObject(Texture texture, float x, float y) {
        mTexture = texture;
        this.x = x;
        this.y = y;

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

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Rectangle getRect() {
        return cRect;
    }
}
