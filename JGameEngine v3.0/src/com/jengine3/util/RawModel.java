/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jengine3.util;

/**
 *
 * @author CSLAB313-1740
 */
public class RawModel {
    private final int vaoID;
    private final int vertexCount;
	
    public RawModel(int vaoID, int vertexCount) {
            this.vaoID = vaoID;
            this.vertexCount = vertexCount;
    }

    public int getVaoID() {
            return vaoID;
    }

    public int getVertexCount() {
            return vertexCount;
    }
}
