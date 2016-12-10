/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jengine3.framework;

import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

import org.lwjgl.glfw.GLFWMouseButtonCallback;

/**
 * 
 * @author CSLAB313-1740
 */
public class MouseButtonHandler extends GLFWMouseButtonCallback {
    
	public static boolean[] buttons = new boolean[16];
	public static boolean[] buttonsLast = new boolean[16];

	@Override
	public void invoke(long window, int button, int action, int mods) {
		buttonsLast = buttons.clone();
		try {
                    buttons[button] = (action != GLFW_RELEASE);
		} catch (ArrayIndexOutOfBoundsException exc) {
			exc.printStackTrace();
			System.out.println("Invalid Button");
		}
	}

	public static void update() {
		buttonsLast = buttons.clone();
	}
	
	public static boolean isMouseButtonDown(MouseButton button) {
		return buttons[button.getButtonCode()];
	}

	public static boolean isMouseButtonPressed(MouseButton button) {
		return buttons[button.getButtonCode()]
				&& !buttonsLast[button.getButtonCode()];
	}

	public static boolean isMouseButtonReleased(MouseButton button) {
		return !buttons[button.getButtonCode()]
				&& buttonsLast[button.getButtonCode()];
	}
}
