package org.tukcity.ld30.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import org.tukcity.ld30.World;
import org.tukcity.ld30.objects.status.JumpStatus;

/**
 * Created by james on 8/24/14.
 */
public class PlayerObject extends WObject {

    private final float ANIMATION_LENGTH = 4/60f;
    private float animationTimer = 0.0f;
    private int animationIndex = 0;
    private Texture[] animationTextures = {new Texture("character/walk1.png"), new Texture("character/walk2.png"), new Texture("character/walk3.png")};
    private Texture jumpingTexture = new Texture("character/jump.png");
    private Texture standingTexture = new Texture("character/standing.png");

    public boolean isStanding = true;

    public PlayerObject(Texture texture, float x, float y) {
        super(texture, x, y);

        setCRectHack(new Rectangle(x, y, animationTextures[0].getWidth(), animationTextures[0].getHeight()));
    }

    @Override
    public void draw(SpriteBatch sb) {
        if (isStanding) {
            sb.draw(standingTexture, x, y);
            return;
        }
        if (getJumpStatus() == JumpStatus.DOWN)
            sb.draw(animationTextures[animationIndex], x, y);
        else if (getJumpStatus() == JumpStatus.UP)
            sb.draw(jumpingTexture, x, y);


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
