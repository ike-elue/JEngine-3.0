package com.jengine3.engine;

import java.util.List;
import java.util.ArrayList;

public class DataManager {

    private final List<float[]> currentData, pastData;

    public DataManager() {
        currentData = new ArrayList<>();
        pastData = new ArrayList<>();
    }

    public synchronized void addData(float[] data) {
        this.currentData.add(data);
    }

    public synchronized void swicthData() {
        pastData.clear();
        pastData.addAll(currentData);
        currentData.clear();
    }

    private float[][] findData(float type, int index) {
        if (!pastData.isEmpty()) {
            float[][] typedData;
            int amount = 0;
            int dataLimit = 0;

            // Get the amount of currentData that meets conditions
            for (int i = 0; i < pastData.size(); i++) {
                float[] storedData = pastData.get(i);
                if (storedData != null) {
                    if (storedData[index] == type) {
                        amount++;
                        dataLimit = storedData.length;
                    }
                }
            }

            if (amount == 0 || dataLimit == 0) {
                return null;
            }

            typedData = new float[amount][dataLimit];

            int amount2 = 0;

            // Store into multidimensional array
            for (int i = 0; i < pastData.size(); i++) {
                float[] storedData = pastData.get(i);
                if (storedData[index] == type) {
                    typedData[amount2] = storedData.clone();
                    amount2++;
                    if (amount2 == amount) {
                        break;
                    }
                }
            }
            return typedData;
        }
        return null;
    }
    
    public float[][] findGenericData(float engineID) {
        return findData(engineID, 0);
    }
    
    public float[][] findTypedData(float type) {
        return findData(type, 1);
    }
}
