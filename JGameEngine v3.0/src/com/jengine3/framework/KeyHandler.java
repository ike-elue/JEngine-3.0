/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jengine3.framework;

import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

import org.lwjgl.glfw.GLFWKeyCallback;

/**
 *
 * @author CSLAB313-1740
 */
public class KeyHandler extends GLFWKeyCallback {
    
	public static boolean[] keys = new boolean[15000];
	public static boolean[] keysLast = new boolean[15000];

	@Override
	public void invoke(long window, int key, int scancode, int action, int mods) {
		keysLast = keys.clone();
		try {
			keys[key] = (action != GLFW_RELEASE);
		} catch (ArrayIndexOutOfBoundsException exc) {
			exc.printStackTrace();
			System.out.println("Invalid Key");
		}
	}

	public static void update() {
		keysLast = keys.clone();
	}

	public static boolean isKeyDown(Key key) {
		return keys[key.getKeycode()];
	}

	public static boolean isKeyPressed(Key key) {
		return keys[key.getKeycode()] && !keysLast[key.getKeycode()];
	}

	public static boolean isKeyReleased(Key key) {
		return !keys[key.getKeycode()] && keysLast[key.getKeycode()];
	}
}
