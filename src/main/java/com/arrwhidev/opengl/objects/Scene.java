package com.arrwhidev.opengl.objects;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by arran on 26/08/2016.
 */
public class Scene {

    private Pointer pointer;
    private List<Quad> quads;
    private Quad tempQuad;

    public Scene() {
        quads = new CopyOnWriteArrayList<>();
        for(int i = 0; i < 1; i++) {
            quads.add(new Quad());
        }
        pointer = new Pointer(quads.get(0));
    }

    public void render() {
        pointer.render();
        quads.stream().forEach(q -> q.render());

        if(tempQuad != null) tempQuad.render();
    }

    public void update(int delta) {
        pointer.update(delta);
        quads.stream().forEach(q -> q.update(delta));

        if(Mouse.isButtonDown(0)) {
            float mx = Mouse.getX();
            float my = Display.getHeight() - Mouse.getY(); // LWJGL uses bottom left as origin, need to fix it to be top left.

            if(tempQuad == null) {
                tempQuad = new Quad(mx, my);
            }
            tempQuad.setWidth(mx - tempQuad.getInitialPosition().x);
            tempQuad.setHeight(my - tempQuad.getInitialPosition().y);
        } else {
            if(tempQuad != null) {
                tempQuad.create();
                quads.add(tempQuad);
                tempQuad = null;
            }
        }
    }
}
