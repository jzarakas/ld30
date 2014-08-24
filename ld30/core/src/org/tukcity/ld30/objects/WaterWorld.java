package org.tukcity.ld30.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.tukcity.ld30.World;
import org.tukcity.ld30.utils.LevelBuilder;

/**
 * Created by james on 8/24/14.
 */
public class WaterWorld extends World {

    private final float BREATH_LIMIT = 15f;//seconds
    private float breathTimer = 0f;
    private BitmapFont font = new BitmapFont();

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
        WObject tmp = new WObject(textures.get(LILLY_WHITE_SM), 12, starty - 475f);
        tmp.setCollisionDimensions(25, 140, 90 - 27, 40);
        objs.add(tmp);

        WObject last;

        String[] plants = {LILLY_PINK_LG, LILLY_PINK_SM, LILLY_WHITE_LG, LILLY_WHITE_SM};
        for (int i = 0; i < 10; i++) {

            last = tmp;
            String plant = plants[LevelBuilder.random(plants)];

            tmp = new WObject(textures.get(plant), i * textures.get( plant).getWidth() + tmp.getRect().width, starty - 475f);
            tmp.setCollisionDimensions(25, 140, 90 - 27, 40);
            objs.add(tmp);
        }

        //and lets add some fishies
        String fishes[] = {FISH_BLUE, FISH_GREEN_LG, FISH_GREEN_SM};
        String fish;
        Texture bg = textures.get("gradient");
        for (int i = 0; i < 15; i++) {
           fish = fishes[LevelBuilder.random(fishes)];

            boolean type = LevelBuilder.random();

            if (type) {
                tmp = new HorizontalMovingObject(textures.get(fish), LevelBuilder.random(0, bg.getWidth()), LevelBuilder.random(0, (int)(starty - 500f)), LevelBuilder.random(40, 120), LevelBuilder.random(0, 3));
            } else {
                tmp = new VerticalMovingObject(textures.get(fish), LevelBuilder.random(0, bg.getWidth()), LevelBuilder.random(0, (int)(starty - 500f)), LevelBuilder.random(40, 120), LevelBuilder.random(0, 3));

            }
            //tmp = new WObject(textures.get(fish), LevelBuilder.random(0, bg.getWidth()), LevelBuilder.random(0, (int)(starty - 500f)));
            objs.add(tmp);
        }

        //and lets add some bubbles
        String bubbles[] = {BUBBLE_LG, BUBBLE_SM};
        String bubble;
        for (int i = 0; i < 25; i++) {
            bubble = bubbles[LevelBuilder.random(bubbles)];

            boolean type = LevelBuilder.random();

            if (type) {
                tmp = new HorizontalMovingObject(textures.get(bubble), LevelBuilder.random(0, bg.getWidth()), LevelBuilder.random(0, (int)(starty - 500f)), LevelBuilder.random(40, 120), LevelBuilder.random(0, 3));
            } else {
                tmp = new VerticalMovingObject(textures.get(bubble), LevelBuilder.random(0, bg.getWidth()), LevelBuilder.random(0, (int)(starty - 500f)), LevelBuilder.random(40, 120), LevelBuilder.random(0, 3));

            }

            tmp.setBubble(true);
            //tmp = new WObject(textures.get(fish), LevelBuilder.random(0, bg.getWidth()), LevelBuilder.random(0, (int)(starty - 500f)));
            objs.add(tmp);
        }
    }

    @Override
    public void draw(SpriteBatch sb) {
        sb.draw(textures.get("gradient"), 0, 0);
    }

    @Override
    public void update(float delta) {
        breathTimer += delta;

        if (breathTimer >= BREATH_LIMIT) {
            //death!
            System.out.println("you died!");

            breathTimer = 0f;
        }
    }

    @Override
    public void drawHud(SpriteBatch sb) {
        font.draw(sb, "breath: " + Math.round( ((BREATH_LIMIT - breathTimer) /BREATH_LIMIT) * 100f) + "%", 15, Gdx.graphics.getHeight());
    }

    public void addBreath(float amount) {
        breathTimer -= amount;
        if (breathTimer < 0)
            breathTimer = 0;
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
