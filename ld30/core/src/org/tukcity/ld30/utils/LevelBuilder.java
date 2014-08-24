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

    public static final class RectWrapper {
        public float x;
        public float y;
        public float h;
        public float w;

        public RectWrapper() {
        }
    }
}
