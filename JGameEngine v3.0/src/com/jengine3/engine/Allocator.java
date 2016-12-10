/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jengine3.engine;

import java.util.ArrayList;

/**
 *
 * @author Jonathan Elue
 */
public class Allocator {
    private Message[] messages;
    private int dataToAdd;
    
    private final ArrayList<Double> extraClearedData;
    
    private final ArrayList<double[]> extraData;
    private final ArrayList<Classification> extraClassifier;
    
    public Allocator(int n) {
        messages = new Message[n];
        dataToAdd = 0;
        extraClearedData = new ArrayList<>();
        extraData = new ArrayList<>();
        extraClassifier = new ArrayList<>();
        createDefaultMessages();
    }
    
    private void createDefaultMessages() {
        for(int i = 0; i < messages.length; i++)
            messages[i] = new Message();
    }
    
    public void expand() {
        if(dataToAdd != 0) {
            Message[] temp = messages.clone();
            messages = new Message[temp.length + dataToAdd];
            System.arraycopy(temp, 0, messages, 0, temp.length);
            for(int i = temp.length, j = 0; i < messages.length; i++, j++) {
                messages[i].setClearedData(extraClearedData.get(j));
                messages[i].setData(extraData.get(j));
                messages[i].setClassification(extraClassifier.get(j));
            }
            extraClearedData.clear();
            extraData.clear();
            extraClassifier.clear();
            dataToAdd = 0;
        }
    }
    
    public int checkSpace() {
        for(int i = 0; i < messages.length; i++) {
            if(messages[i].isAvailable() || !messages[i].isDecayed())
                return i;
        }
        return -1;
    }
    
    public void allocateMessage(double[] data, Classification dataClassifier, double clearedData) {
        int index = checkSpace();
        if(index == -1) {
            extraData.add(data);
            extraClassifier.add(dataClassifier);
            extraClearedData.add(clearedData);
            dataToAdd++;
        }
        messages[index].setClearedData(clearedData);
        messages[index].setData(data);
        messages[index].setClassification(dataClassifier);
    }
}
