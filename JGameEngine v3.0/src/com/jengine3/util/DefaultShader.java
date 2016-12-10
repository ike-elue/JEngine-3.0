/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jengine3.util;

/**
 *
 * @author Jonathan Elue
 */
public class DefaultShader extends ShaderProgram{
    public DefaultShader(String vertexFile, String fragmentFile) throws Exception {
            super(vertexFile, fragmentFile, "default");
    }

    @Override
    protected void bindAttributes() {
            super.bindAttribute(0, "position");
            super.bindAttribute(1, "textureCoordinates");
    }

    @Override
    protected void createUniforms() {
            try {
                    super.createUniform("transformationMatrix");
                    super.createUniform("isTexture"); // if yes the vao outputs texture
                    super.createUniform("color");

            } catch (Exception e) {
                    e.printStackTrace();
            }
    }
}
