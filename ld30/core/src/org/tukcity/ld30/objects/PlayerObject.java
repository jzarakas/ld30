package org.tukcity.ld30.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import org.tukcity.ld30.World;
import org.tukcity.ld30.objects.status.CollisionStatus;
import org.tukcity.ld30.objects.status.JumpStatus;

/**
 * Created by james on 8/24/14.
 */
public class PlayerObject extends WObject {

    private final float ANIMATION_LENGTH = 4/60f;
    private float animationTimer = 0.0f;
    private int animationIndex = 0;
    private Texture[] animationTextures = {new Texture("character/walk1.png"), new Texture("character/walk2.png"),
            new Texture("character/walk3.png"), new Texture("character/walk4.png")};

    private Texture[] animationTexturesLeft = {new Texture("character/walk1Left.png"), new Texture("character/walk2Left.png")
            , new Texture("character/walk3Left.png"), new Texture("character/walk4Left.png")};

    private Texture jumpingTexture = new Texture("character/jump.png");
    private Texture standingTexture = new Texture("character/standing.png");

    private Texture jumpingTextureLeft = new Texture("character/jumpLeft.png");
    private Texture standingTextureLeft = new Texture("character/standingLeft.png");

    public boolean isStanding = true;

    public boolean isFacingLeft = false;

    public PlayerObject(Texture texture, float x, float y) {
        super(texture, x, y);

        setCRectHack(new Rectangle(x, y, animationTextures[0].getWidth(), animationTextures[0].getHeight()));
    }

    @Override
    public void draw(SpriteBatch sb) {

        if (isStanding && getJumpStatus() == JumpStatus.UP) {
            sb.draw(jumpingTexture, x, y);
            return;
        } else if (isStanding) {
            if (isFacingLeft)
                sb.draw(standingTextureLeft, x, y);
            else
                sb.draw(standingTexture, x, y);
            return;
        }
        if (getJumpStatus() == JumpStatus.DOWN && getCollisionStatus() != CollisionStatus.BOTTOM) {
            if (isFacingLeft)
                sb.draw(animationTexturesLeft[animationIndex], x, y);
            else
                sb.draw(animationTextures[animationIndex], x, y);
        } else if (getJumpStatus() == JumpStatus.UP) {
            if (isFacingLeft)
                sb.draw(jumpingTextureLeft, x, y);
            else
                sb.draw(jumpingTexture, x, y);

        }

    }

    @Override
    public void update(float delta, World world) {
        super.update(delta, world);

        animationTimer += delta;

        if (animationTimer >= ANIMATION_LENGTH) {
            animationIndex++;
            if (animationIndex == animationTextures.length)
                animationIndex = 0;
            animationTimer = 0;
        }
    }
}
