/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jengine3.framework;


import org.lwjgl.glfw.GLFWCursorPosCallback;

/**
 * 
 * @author CSLAB313-1740
 */
public class CursorPosHandler extends GLFWCursorPosCallback {

	private static double mouseX, mouseY;

	@Override
	public void invoke(long window, double xpos, double ypos) {
		mouseX = xpos;
		mouseY = ypos;
	}

	public static double getXPos() {
		return mouseX;
	}

	public static double getYPos() {
		return mouseY;
	}
}
