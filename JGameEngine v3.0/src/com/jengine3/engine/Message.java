/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jengine3.engine;

/**
 *
 * @author Jonathan Elue
 */
public class Message {
    // Every message is made available after time update, the ones that are repeated will just be set available again
    // Messages that have the same exact data aren't changed
    private boolean available;
    private boolean decayed;
    
    private double clearedData;
    
    private final double[] data;
    private Classification dataClassifier;
    
    public Message() {
        available = true;
        decayed = false;
        clearedData = 0;
        data = new double[64]; // default data length
        dataClassifier = null;
    }
    
    public void clearData() {
        for(int i = 0; i < data.length; i++)
            data[i] = clearedData;
    }
    
    public boolean isAvailable() {
        return available;
    }
    
    public boolean isDecayed() {
        return decayed;
    }
    
    public Classification getClassification() {
        return dataClassifier;
    }
    
    public void setAvailble(boolean available) {
        this.available = available;
    }
    
    public void setDecayed(boolean decayed) {
        this.decayed = decayed;
    }
    
    public void setClearedData(double clearedData) {
        this.clearedData = clearedData;
    }
    
    public void setData(double[] data) {
        clearData();
        System.arraycopy(data, 0, this.data, 0, data.length);
    } 
    
    public void setClassification(Classification dataClassifier) {
        this.dataClassifier = dataClassifier;
    }
    
}
