/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jengine3.util;

import java.awt.Color;

/**
 *
 * @author CSLAB313-1740
 */
public class Pixel {

	public static float getAlpha(int color) {
		return (0xff & (color >> 24)) / 255f;
	}

	public static float getRed(int color) {
		return (0xff & (color >> 16)) / 255f;
	}

	public static float getGreen(int color) {
		return (0xff & (color >> 8)) / 255f;
	}

	public static float getBlue(int color) {
		return (0xff & (color)) / 255f;
	}

	public static int getColor(int r, int g, int b) {
		return new Color(r, g, b).getRGB();
	}
	
	public static int getColor(Color color) {
		return color.getRGB();
	}

	public static int getColorf(float a, float r, float g, float b) {
		return ((int) (a * 255f) << 24 | (int) (r * 255f) << 16
				| (int) (g * 255f) << 8 | (int) (b * 255f));
	}    
}
