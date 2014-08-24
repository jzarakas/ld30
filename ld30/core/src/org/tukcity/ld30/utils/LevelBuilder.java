package org.tukcity.ld30.utils;

import com.badlogic.gdx.Gdx;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.tukcity.ld30.objects.CollisionObject;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by james on 8/23/14.
 */
public final class LevelBuilder {

    private LevelBuilder() {
    }

    public static List<CollisionObject> generate(String file) {
        String json = Gdx.files.local(file).readString();

        List<CollisionObject> list = new LinkedList<CollisionObject>();

        Type collectionType = new TypeToken<Collection<RectWrapper>>() {
        }.getType();
        Collection<RectWrapper> rects = new Gson().fromJson(json, collectionType);

        for (RectWrapper r : rects) {
            list.add(new CollisionObject(r));
        }

        return list;
    }

    //lower is inclusive, upper is exclusive.
    public static int random(int lower, int upper) {
        return (int)(Math.random() * (upper - lower)) + lower;
    }

    public static int random(Object[] o) {
        return random(0, o.length);
    }

    public static boolean random() {
        int i = random(0, 2);

        if (i == 1)
            return true;

        return false;
    }

    public static final class RectWrapper {
        public float x;
        public float y;
        public float h;
        public float w;

        public RectWrapper() {
        }
    }


}
