package com.arrwhidev.opengl.objects;

import com.arrwhidev.opengl.math.Vertex2;
import com.arrwhidev.opengl.math.Vertex4;
import org.lwjgl.opengl.Display;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created by arran on 27/08/2016.
 */
public class Pointer extends GameObject {

    private Quad quadToLookAt;

    String getTextureName() {
        return null;
    }

    Vertex2 getCenter() {
        return new Vertex2(x + (w / 2), y + (h / 2));
    }

    public Pointer(Quad q) {
        this.quadToLookAt = q;
        this.w = 30;
        this.h = 20;
        this.x = 10;
        this.y = Display.getHeight() / 2 - (h / 2);
        this.rotation = 0;
        this.c = new Vertex4(1f, 1f, 1f, 1f);
    }

    public void render() {
        super.render();
        glPushMatrix();
            glTranslatef(x, y, 0);
            glRotatef(rotation, 0f, 0f, 1f);
            glTranslatef(-x, -y, 0);

            glBegin(GL_LINES);
            glVertex2f(x, y);
            glVertex2f(x + w, y + (h / 2));
            glEnd();

            glBegin(GL_LINES);
            glVertex2f(x + w, y + (h / 2));
            glVertex2f(x, y + h);
            glEnd();
        glPopMatrix();
    }

    public void update(int delta) {
        // Workout degrees between pointer and quad to make pointer look at the quad.
        Vertex2 quadCenter = quadToLookAt.getCenter();
        double degrees = (float) Math.atan2(quadCenter.y - getCenter().y, quadCenter.x - getCenter().x) * 180f / Math.PI;
        rotation = (float) degrees;
    }
}
