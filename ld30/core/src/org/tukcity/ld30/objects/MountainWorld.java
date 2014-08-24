package org.tukcity.ld30.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.tukcity.ld30.World;
import org.tukcity.ld30.utils.LevelBuilder;

/**
 * Created by james on 8/24/14.
 */
public class MountainWorld extends World {
    public MountainWorld(float modifier, float time, float cameraModifier) {
        super(modifier, time, cameraModifier);

        colliders = LevelBuilder.generate("mountain.json");
        textures.put("gradient", new Texture("mountains/gradient.png"));
        textures.put("lowerbg", new Texture("mountains/lowerbg.png"));
        textures.put("upperbg", new Texture("mountains/upperbg.png"));
        textures.put("foreground", new Texture("mountains/foreground.png"));

        startx = 2.3920102f;
        starty = 1758.2645f;
    }
    
    @Override
    public void draw(SpriteBatch sb) {
        sb.draw(textures.get("gradient"), 0, 0);
        sb.draw(textures.get("foreground"), 0, 0);
        sb.draw(textures.get("upperbg"), 0, textures.get("gradient").getHeight() - textures.get("upperbg").getHeight());
        sb.draw(textures.get("lowerbg"), 0, textures.get("gradient").getHeight() - textures.get("upperbg").getHeight());
    }
}
