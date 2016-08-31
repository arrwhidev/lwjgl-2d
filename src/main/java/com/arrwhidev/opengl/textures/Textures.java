package com.arrwhidev.opengl.textures;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by arran on 27/08/2016.
 */
public class Textures {

    private static final Map<String, Texture> TEXTURES = loadTextures();
    public static final String QUAD = "QUAD";

    public static Texture get(String name) {
        return TEXTURES.get(name);
    }

    private static Map<String, Texture> loadTextures() {
        Map<String, Texture> textures = new HashMap<>();
        textures.put(QUAD, loadTexture("quad.png"));
        return textures;
    }

    private static Texture loadTexture(String name) {
        try {
            return TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(name));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // TODO - handle this better.
        return null;
    }
}
