/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jengine3.util;

import org.joml.Matrix4f;
import org.joml.Vector3f;


/**
 *
 * @author CSLAB313-1740
 */
public class Maths {
	
    private final static Matrix4f TRANSFORMMATRIX = new Matrix4f();
   
    public static Matrix4f createTransformationMatrix(Vector3f positions, float width, float height) {
            TRANSFORMMATRIX.identity();
            TRANSFORMMATRIX.translate(positions, TRANSFORMMATRIX);
            TRANSFORMMATRIX.scale(new Vector3f(width, height, 1), TRANSFORMMATRIX);
            return TRANSFORMMATRIX;
    }

}
