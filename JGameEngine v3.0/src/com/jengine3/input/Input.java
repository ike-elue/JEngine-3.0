/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jengine3.input;

import com.jengine3.engine.Engine;
import com.jengine3.framework.*;

/**
 *
 * @author CSLAB313-1740
 */
public class Input extends Engine {
    
    private int internalPointer;
    private int maxData;
    
    public Input(Framework framework) {
        super("input", 20, framework, -2); 
        // real number is maxData - 4 because first 4 elements are already determined
        // -2 because of scroll wheel
    }

    @Override
    public void init() {
        maxData = 20;
        // Gathers no data 
        internalPointer = 4;
        createClassification(4, "Key Down");
        
        createClassification(5, "Key Pressed");
        
        createClassification(6, "Key Released");
        
        createClassification(7, "Mouse Button Down");
        
        createClassification(8, "Mouse Button Pressed");
        
        createClassification(9, "Mouse Button Released");
        
        createClassification(10, "Cursor Position");
        encodeClassification(10, 4, "X Position");
        encodeClassification(10, 5, "Y Position");
        
        createClassification(11, "Scroll Wheel");
        encodeClassification(11, 4, "X Scroll");
        encodeClassification(11, 5, "Y Scroll");
        
    }

    @Override
    public void update(double delta) {
        //postTestData();
        postInputs();
    }
    
    private void postInputs() {
        postKeyDown();
        postKeyPressed();
        postKeyReleased();
        postMouseButtonDown();
        postMouseButtonPressed();
        postMouseButtonReleased();
        postCursorPos();
        postScrollWheel();
    }
    
    private void postKeyDown() {
        internalPointer = 4;
        outData[1] = 4;
        for(int i = 0; i < KeyHandler.keys.length; i++) {
            if(KeyHandler.keys[i]) {
                outData[internalPointer] = i;
                internalPointer++;
            }
            if(internalPointer >= maxData)
                break;
        }
        postData(outData);
        clearOutData();
    }
    private void postKeyPressed() {
        internalPointer = 4;
        outData[1] = 5;
        for(int i = 0; i < KeyHandler.keys.length; i++) {
            if(KeyHandler.keys[i] && !KeyHandler.keysLast[i]) {
                outData[internalPointer] = i;
                internalPointer++;
            }
            if(internalPointer >= maxData)
                break;
        }
        postData(outData);
        clearOutData();
    }
    private void postKeyReleased() {
        internalPointer = 4;
        outData[1] = 6;
        for(int i = 0; i < KeyHandler.keys.length; i++) {
            if(!KeyHandler.keys[i] && KeyHandler.keysLast[i]) {
                outData[internalPointer] = i;
                internalPointer++;
            }
            if(internalPointer >= maxData)
                break;
        }
        postData(outData);
        clearOutData();
    }
    private void postMouseButtonDown() {
        internalPointer = 4;
        outData[1] = 7;
        for(int i = 0; i < MouseButtonHandler.buttons.length; i++) {
            if(MouseButtonHandler.buttons[i]) {
                outData[internalPointer] = i;
                internalPointer++;
            }
            if(internalPointer >= maxData)
                break;
        }
        postData(outData);
        clearOutData();
    }
    private void postMouseButtonPressed() {
        internalPointer = 4;
        outData[1] = 8;
        for(int i = 0; i < MouseButtonHandler.buttons.length; i++) {
            if(MouseButtonHandler.buttons[i] && !MouseButtonHandler.buttonsLast[i]) {
                outData[internalPointer] = i;
                internalPointer++;
            }
            if(internalPointer >= maxData)
                break;
        }
        postData(outData);
        clearOutData();
    }
    private void postMouseButtonReleased() {
        internalPointer = 4;
        outData[1] = 9;
        for(int i = 0; i < MouseButtonHandler.buttons.length; i++) {
            if(!MouseButtonHandler.buttons[i] && MouseButtonHandler.buttonsLast[i]) {
                outData[internalPointer] = i;
                internalPointer++;
            }
            if(internalPointer >= maxData)
                break;
        }
        postData(outData);
        clearOutData();
    }
    private void postCursorPos() {
        internalPointer = 4;
        outData[1] = 10;
        outData[internalPointer] = (float)CursorPosHandler.getXPos();
        internalPointer++;
        outData[internalPointer] = getFramework().getHeight() - (float)CursorPosHandler.getYPos(); // Gets Cartesian Coords
        postData(outData);
        clearOutData();
    }
    private void postScrollWheel() {
        internalPointer = 4;
        outData[1] = 11;
        outData[internalPointer] = (float)ScrollWheelHandler.getXScroll();
        internalPointer++;
        outData[internalPointer] = (float)ScrollWheelHandler.getYScroll();
        postData(outData);
        clearOutData();
    }

    @Override
    public void dispose() {
        
    }
    
}
