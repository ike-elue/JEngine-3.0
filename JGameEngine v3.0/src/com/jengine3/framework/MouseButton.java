/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jengine3.framework;

import static org.lwjgl.glfw.GLFW.*;

/**
 *
 * @author CSLAB313-1740
 */
public enum MouseButton {
    GLFW_RBUTTON(GLFW_MOUSE_BUTTON_RIGHT),
    GLFW_LBUTTON(GLFW_MOUSE_BUTTON_LEFT),
    GLFW_MBUTTON(GLFW_MOUSE_BUTTON_MIDDLE);
	
    private int buttonCode;
	
    private MouseButton(int buttonCode) {
	this.buttonCode = buttonCode;
    }

    public int getButtonCode() {
        return buttonCode;
    }

    public void setButtonCode(int buttonCode) {
        this.buttonCode = buttonCode;
    }
}
