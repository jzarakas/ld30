package org.tukcity.ld30.utils;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import org.tukcity.ld30.World;

/**
 * Created by james on 8/24/14.
 */
public class DebugInputProcessor implements InputProcessor {

    private World world;

    public DebugInputProcessor(World world) {
        this.world = world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {

        if (keycode == Input.Keys.P) {
            System.out.println("--------------------");
            System.out.println("Debug Info");
            System.out.println("Speed Modifier: " + world.getModifier());
            System.out.println("Camera Speed Modifier: " + world.getCameraModifier());
            System.out.println("Camera Velocity: " + world.getCameraVelocity());
            System.out.println("Player Velocity: " + world.getPlayerVelocity());
            System.out.println("Elapsed Game Time: " + world.getTime());
            System.out.println("--------------------");
            return true;
        } else if (keycode == Input.Keys.H) {
            System.out.println("--------------------");
            System.out.println("Commands");
            System.out.println("WASD - Move character. W will probably be disabled.");
            System.out.println("+ and - Increase/decrease camera velocity of current world.");
            System.out.println("{ and } - Increase/decrease player velocity of current world");
            System.out.println("; and \\ Increase/decrease speed modifier of current world (affects player and camera");
            System.out.println("/ Show collision boundaries");
            System.out.println("H this help message");
            System.out.println("P prints out some debug info primarily the current modifier values!");
            System.out.println("--------------------");
            return true;
        } else if (keycode == Input.Keys.SLASH) {
            World.toggleDebugRenderer();
            System.out.println("Show collision bounds: " + World.getDebugRenderer());
            return true;
        }

        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
