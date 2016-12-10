/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jengine3.framework;

import com.jengine3.util.DefaultShader;
import com.jengine3.util.Loader;
import com.jengine3.util.Maths;
import com.jengine3.util.Pixel;
import com.jengine3.util.RawModel;
import com.jengine3.util.ShaderProgram;
import org.joml.Vector3f;
import static org.lwjgl.opengl.ARBVertexArrayObject.glBindVertexArray;
import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;

/**
 *
 * @author CSLAB313-1740
 */
public class Draw {
    
    private final int width;
    private final int height;
    
    private final int hWidth;
    private final int hHeight;
    
    private RawModel standardFillQuad;
    private RawModel standardDrawQuad;

    private ShaderProgram shader;
    
    public Draw(int width, int height) {
        this.width = width;
        this.height = height;
        hWidth = width/2;
        hHeight = height/2;
        init();
    }
    
    private void init() {
	try {
            shader = new DefaultShader("res/standard_vertex_shader.vert", "res/standard_fragment_shader.frag");
        } catch (Exception e) {
            e.printStackTrace();
        }
        float[] positions = new float[] { 0, 0, 0, 0, 2f/height, 0, 2f/width, 2f/height, 0, 2f/width, 0, 0 };
	float[] textureCoords = new float[] {0f, 1f, 0f, 0f, 1f, 0f, 1f, 1f};
	int[] indices = new int[] { 0, 1, 2, 2, 3, 0 };
	int[] indices2 = new int[] { 0, 1, 1, 2, 2, 3, 3, 0};
		
        standardFillQuad = Loader.loadToVAO(positions, textureCoords, indices);
	standardDrawQuad = Loader.loadToVAO(positions, textureCoords, indices2);
    }

    public void start(boolean useDefaultCamera) {
        // Eventually do this: shader.setUniform("viewMatrix", Maths.createViewMatrix(currentCamera));
        if (shader != null)
                    shader.bind();
    }

    public void stop() {
            if (shader != null)
                    shader.unbind();
    }

    public Vector3f getRealCoords(Vector3f pos) {
            Vector3f position = new Vector3f(pos.x - hWidth, pos.y - hHeight, pos.z);
            return position.div(hWidth, hHeight, 1); // 1 should change to lowest layer once applicable
    }

    private void setUniforms(Vector3f position, float width, float height, boolean isTexture, int color) {
        shader.setUniform("transformationMatrix", Maths.createTransformationMatrix(getRealCoords(position), width, height));
        shader.setUniform("isTexture", isTexture);
        shader.setUniform("color", new Vector3f(Pixel.getRed(color), Pixel.getGreen(color), Pixel.getBlue(color)));
    }

    private void enableVAO(RawModel model) {
            // Bind the vertex array and enable our location
            glBindVertexArray(model.getVaoID());
            glEnableVertexAttribArray(0);
            glEnableVertexAttribArray(1);
    } 
    private void disableVAO() {
            // Disable our location
            glDisableVertexAttribArray(0);
            glDisableVertexAttribArray(1);
            glBindVertexArray(0);
    }

    public void drawRect(Vector3f pos, float width, float height, int color) {
        setUniforms(pos, width, height, false, color);
        enableVAO(standardDrawQuad);
        glDrawElements(GL_LINES, standardDrawQuad.getVertexCount(), GL_UNSIGNED_INT, 0);
        disableVAO();
    }
    
    public void fillRect(Vector3f pos, float width, float height, int color) {
        setUniforms(pos, width, height, false, color);
        enableVAO(standardFillQuad);
        glDrawElements(GL_TRIANGLES, standardFillQuad.getVertexCount(), GL_UNSIGNED_INT, 0);
        disableVAO();
    }
    
    public void dispose() {
        shader.cleanup();
        Loader.cleanUp();
    }
}
