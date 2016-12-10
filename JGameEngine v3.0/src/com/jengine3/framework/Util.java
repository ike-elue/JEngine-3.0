/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jengine3.framework;

/**
 *
 * @author CSLAB313-1740
 */
public class Util {

    public static float clamp(float var, float min, float max) {
        if (var <= min) {
            return min;
        } else if (var >= max) {
            return max;
        } else {
            return var;
        }
    }

}
