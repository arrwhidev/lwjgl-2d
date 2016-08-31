package com.arrwhidev.opengl.objects;

import com.arrwhidev.opengl.math.Vertex2;
import com.arrwhidev.opengl.math.Vertex4;
import com.arrwhidev.opengl.textures.Textures;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glColor4f;

/**
 * Created by arran on 26/08/2016.
 */
public abstract class GameObject {

    GameObjectState state = GameObjectState.PAUSED;
    Vertex4 c;
    Vertex2 velocity;
    float x, y, w, h;
    float rotation;

    abstract String getTextureName();
    abstract Vertex2 getCenter();
    public abstract void update(int delta);

    public void render() {
        glColor4f(c.x, c.y, c.z, c.a);

        if(getTextureName() != null) {
            glBindTexture(GL_TEXTURE_2D, Textures.get(getTextureName()).getTextureID());
        }
    }
}
