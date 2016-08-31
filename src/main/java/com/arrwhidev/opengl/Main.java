package com.arrwhidev.opengl;

/**
 * Created by arran on 26/08/2016.
 */

import com.arrwhidev.opengl.objects.Scene;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;

class Main {

    private static final boolean VSYNC = true;
    private static final boolean FULLSCREEN = false;
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    private Scene scene;
    private long lastFrame;
    private int fps;
    private long lastFPS;

    public void start() throws LWJGLException {
        Display.setResizable(false);
        Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
        Display.setVSyncEnabled(VSYNC);
        Display.setFullscreen(FULLSCREEN);
        Display.create();

        createGameObjects();
        initOpenGL();

        getDelta();
        lastFPS = getTime();

        while (!Display.isCloseRequested()) {
            int delta = getDelta();

            scene.update(delta);
            updateFPS();

            // Clear the screen and depth buffer
            glClear(GL_COLOR_BUFFER_BIT);
            scene.render();

            Display.update();
            Display.sync(120);
        }

        Display.destroy();
    }

    private void createGameObjects() {
        scene = new Scene();
    }

    void initOpenGL() {
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, WIDTH, HEIGHT, 0, 1, -1);
        glMatrixMode(GL_MODELVIEW);

        glDisable(GL_DEPTH_TEST);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glEnable(GL_TEXTURE_2D);
    }

    /**
     * Calculate how many milliseconds have passed
     * since last frame.
     *
     * @return milliseconds passed since last frame
     */
    public int getDelta() {
        long time = getTime();
        int delta = (int) (time - lastFrame);
        lastFrame = time;

        return delta;
    }

    /**
     * Get the accurate system time
     *
     * @return The system time in milliseconds
     */
    public long getTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }

    /**
     * Calculate the FPS and set it in the title bar
     */
    public void updateFPS() {
        if (getTime() - lastFPS > 1000) {
            Display.setTitle("FPS: " + fps);
            fps = 0;
            lastFPS += 1000;
        }
        fps++;
    }


//    private void pollInput() {
//        if(Mouse.isButtonDown(0)) {
//            System.out.println("Mouse down @ " + Mouse.getX());
//        }
//
//        while(Keyboard.next()) {
//            if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
//                System.out.println("SPACE BAR PRESSED");
//            }
//        }
//    }

    public static void main(String[] argv) throws Exception {
        new Main().start();
    }
}