package org.tukcity.ld30.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.tukcity.ld30.World;
import org.tukcity.ld30.utils.LevelBuilder;

/**
 * Created by james on 8/24/14.
 */
public class WaterWorld extends World {
    public WaterWorld(float modifier, float time, float cameraModifier) {
        super(modifier, time, cameraModifier);

        //colliders = LevelBuilder.generate("mountain.json");


        textures.put("gradient", new Texture("water/gradient.png"));
        textures.put("lowerbg", new Texture("water/lowerbg.png"));
        textures.put("upperbg", new Texture("water/upperbg.png"));

        colliders.add(new CollisionObject(0, 0, textures.get("gradient").getWidth() ,10));


        loadTexture(LILLY_PINK_LG);
        loadTexture(LILLY_PINK_SM);
        loadTexture(LILLY_WHITE_LG);
        loadTexture(LILLY_WHITE_SM);
        loadTexture(FISH_BLUE);
        loadTexture(FISH_GREEN_LG);
        loadTexture(FISH_GREEN_SM);
        loadTexture(BUBBLE_LG);
        loadTexture(BUBBLE_SM);

        startx = 0;
        starty = textures.get("gradient").getHeight() - 128f;

        //lets add some lillies
        WObject tmp = new WObject(textures.get(LILLY_WHITE_SM), 12 * textures.get( LILLY_WHITE_SM).getWidth(), starty - 475f);
        tmp.setCollisionDimensions(25, 40, 90 - 27, 40);
        objs.add(tmp);

        WObject last;

        String[] plants = {LILLY_PINK_LG, LILLY_PINK_SM, LILLY_WHITE_LG, LILLY_WHITE_SM};
        for (int i = 0; i < 10; i++) {

            last = tmp;
            String plant = plants[LevelBuilder.random(plants)];

            tmp = new WObject(textures.get(plant), i * textures.get( plant).getWidth() + tmp.getRect().width, starty - 475f);
            tmp.setCollisionDimensions(25, 40, 90 - 27, 40);
            objs.add(tmp);
        }

        //and lets add some fishies
        String fishes[] = {FISH_BLUE, FISH_GREEN_LG, FISH_GREEN_SM};
        String fish;
        Texture bg = textures.get("gradient");
        for (int i = 0; i < 5; i++) {
           fish = fishes[LevelBuilder.random(fishes)];

            boolean type = LevelBuilder.random();

            if (type) {
                tmp = new HorizontalMovingObject(textures.get(fish), LevelBuilder.random(0, bg.getWidth()), LevelBuilder.random(0, (int)(starty - 500f)), 50f);
            } else {
                tmp = new VerticalMovingObject(textures.get(fish), LevelBuilder.random(0, bg.getWidth()), LevelBuilder.random(0, (int)(starty - 500f)), 50f, 3.0f);

            }
            //tmp = new WObject(textures.get(fish), LevelBuilder.random(0, bg.getWidth()), LevelBuilder.random(0, (int)(starty - 500f)));
            objs.add(tmp);
        }
    }

    @Override
    public void draw(SpriteBatch sb) {
        sb.draw(textures.get("gradient"), 0, 0);
    }

    private void loadTexture(String name) {
        textures.put(name, new Texture("water/" + name + ".png"));
    }

    private final String LILLY_WHITE_LG = "Lily-Large-white";
    private final String LILLY_WHITE_SM = "Lilly-Small-White";
    private final String LILLY_PINK_LG = "Lily-Large-Pink";
    private final String LILLY_PINK_SM = "Lily-Small-Pink";
    private final String FISH_BLUE = "Fish-blue";
    private final String FISH_GREEN_LG = "Fish-green-large";
    private final String FISH_GREEN_SM = "Fish-green-small";
    private final String BUBBLE_LG = "waterBubble-large";
    private final String BUBBLE_SM = "waterBubble-small";
}
