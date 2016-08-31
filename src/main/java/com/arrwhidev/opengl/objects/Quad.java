package com.arrwhidev.opengl.objects;

import com.arrwhidev.opengl.math.Vertex2;
import com.arrwhidev.opengl.math.Vertex4;
import com.arrwhidev.opengl.textures.Textures;
import org.lwjgl.opengl.Display;

import static org.lwjgl.opengl.GL11.*;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by arran on 26/08/2016.
 */
public class Quad extends GameObject {

    private static final int MIN_SIDE = 30;
    private static final int MAX_SIDE = 70;

    private Vertex2 initial;

    public Quad(float x, float y) {
        this.initial = new Vertex2(x, y);
        build(x, y, 0, 0, new Vertex2(0f, 0f));
    }

    public Quad() {
        ThreadLocalRandom r = ThreadLocalRandom.current();

        // Random dimensions.
        int width = r.nextInt(MIN_SIDE, MAX_SIDE + 1);
        int height = r.nextInt(MIN_SIDE, MAX_SIDE + 1);

        // Random position in bounds.
        int x = r.nextInt(0, (Display.getWidth() - width) + 1);
        int y = r.nextInt(0, (Display.getHeight() - height) + 1);

        // Random velocity.
        Vertex2 vel = new Vertex2(r.nextFloat() * 3, r.nextFloat() * 3);

        build(x, y, width, height, vel);
        setState(GameObjectState.LIVE);
    }

    private void build(float x, float y, int w, int h, Vertex2 vel) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.velocity = vel;

        // Random colour.
        ThreadLocalRandom r = ThreadLocalRandom.current();
        this.c = new Vertex4(r.nextFloat(), r.nextFloat(), r.nextFloat(), 0.8f);
    }

    protected String getTextureName() {
        return Textures.QUAD;
    }

    public Vertex2 getCenter() {
        return new Vertex2(x + (w/2), y + (h/2));
    }

    public void update(int delta) {
        if (state.equals(GameObjectState.LIVE)) {
            if (x + w >= 800 || x <= 0) velocity.x *= -1;
            if (y + h >= 600 || y <= 0) velocity.y *= -1;

            x += velocity.x;
            y += velocity.y;

            // if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) rotation += Math.PI * 0.15f;
            // rotation += Math.PI * 0.15f;
        }
    }

    public void render() {
        super.render();
        glPushMatrix();
            // Rotate
            glTranslatef(x, y, 0);
            glRotatef(rotation, 0f, 0f, 1f);
            glTranslatef(-x, -y, 0);

            // Draw Quad
            glBegin(GL_QUADS);
                glTexCoord2f(0,0);
                glVertex2f(x,y);

                glTexCoord2f(1,0);
                glVertex2f(x+w,y);

                glTexCoord2f(1,1);
                glVertex2f(x+w,y+h);

                glTexCoord2f(0,1);
                glVertex2f(x,y+h);
            glEnd();
        glPopMatrix();
    }

    public void create() {
        // Fix position if quad was created with negative width or height.
        if(w < 0) {
            w = w * -1;
            x = x - w;
        }
        if(h < 0) {
            h = h * -1;
            y = y - h;
        }

        applyRandomVelocity();
        setState(GameObjectState.LIVE);
    }

    private void applyRandomVelocity() {
        ThreadLocalRandom r = ThreadLocalRandom.current();
        this.velocity = new Vertex2(r.nextFloat() * 3, r.nextFloat() * 3);
    }

    public void setState(GameObjectState state) {
        this.state = state;
    }

    public void setWidth(float w) {
        this.w = w;
    }

    public void setHeight(float h) {
        this.h = h;
    }

    public Vertex2 getInitialPosition() {
        return initial;
    }
}
