/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jengine3.framework;

import org.lwjgl.glfw.GLFWScrollCallback;

/**
 *
 * @author CSLAB313-1740
 */
public class ScrollWheelHandler extends GLFWScrollCallback{

    private static double x_scroll, y_scroll;
    
    @Override
    public void invoke(long window, double xOffset, double yOffset) {
        x_scroll = xOffset;
        y_scroll = yOffset;
    }
    
    public static double getXScroll() {
        return x_scroll;
    }
    
    public static double getYScroll() {
        return y_scroll;
    }
}
