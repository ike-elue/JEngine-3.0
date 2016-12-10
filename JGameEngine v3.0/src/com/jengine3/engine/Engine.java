package com.jengine3.engine;

import java.util.ArrayList;

import com.jengine3.framework.Debug;
import com.jengine3.framework.Framework;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Engine implements Runnable {

    private final String tag;
    private int id;
    private final int defaultDataValue; 
    
    private EngineManager eManager;
    private DataManager dm;
    private Framework framework;
    private final Debug debug;

    protected float[] outData;

    private final List<float[][]> storedData;
    private final List<float[]> outputDataList;
    private final List<Float> genericDataToGet, typedDataToGet;
    
    private final Map<Float, Map<Integer, String>> classifications;
    
    public Engine(String tag, int standardDataLength, Framework framework) {
        this.tag = tag;
        defaultDataValue = 0;
        
        resetOutData(standardDataLength);
        
        storedData = new ArrayList<>();
        outputDataList = new ArrayList<>(); 
        genericDataToGet = new ArrayList<>();
        typedDataToGet = new ArrayList<>();
        debug = new Debug();
        classifications = new HashMap<>();
        initializeClassifications();
    }
    
    public Engine(String tag, int standardDataLength, Framework framework, int defaultDataValue) {
        this.tag = tag;
        this.defaultDataValue = defaultDataValue;
        
        resetOutData(standardDataLength);
        
        storedData = new ArrayList<>();
        outputDataList = new ArrayList<>(); 
        genericDataToGet = new ArrayList<>();
        typedDataToGet = new ArrayList<>();
        debug = new Debug();
        classifications = new HashMap<>();
        initializeClassifications();
    }

    private void initializeClassifications() {
        // Create list of keys assciated with type
        classifications.put(0f, new HashMap<>());

        createClassification(0f, "Log");
        encodeClassification(0f, 4, "Start Time (ns)");
        encodeClassification(0f, 5, "End Time (ns)");
        encodeClassification(0f, 6, "Elasped Time (s)");
        
        createClassification(1f, "Render");
        
        createClassification(2f, "Asset");
        
        createClassification(3f, "Unknown");
    }

    @Override
    public void run() {
        if (eManager.getPointer() == -3) {
            preupdate();
        } else if (eManager.getPointer() == -2) {
            postOutputData();
        } else if (eManager.getPointer() == -1) {
            clearStoredData();
            postOutputData();
        } else {
            update(eManager.getPointer());
        }
    }

    /**
     * Used to tell engine what info to get overall, variable initialization, classifications it has for data, or resource gathering
     */
    public abstract void init();

    private void preupdate() {
        for (int i = 0; i < genericDataToGet.size(); i++) {
            addStoredData(getGenericData(genericDataToGet.get(i)));
        }

        for (int i = 0; i < typedDataToGet.size(); i++) {
            addStoredData(getTypedData(typedDataToGet.get(i)));
        }
    }
    
    private void postOutputData() {
        for(int i = 0; i < outputDataList.size(); i++) {
            addData(outputDataList.get(i));
        }
        
        outputDataList.clear();
    }

    public abstract void update(double delta);

    public abstract void dispose();

    public void postTestData() {
        int length = outData.length;
        resetOutData(4);
        postData(outData);
        resetOutData(length);
        
    }
    
    public final void resetOutData(int standardDataLength) {
        if (standardDataLength < 4) {
            outData = new float[4];
            if(defaultDataValue != 0)
                clearOutData();
        } else {
            outData = new float[standardDataLength];
            if(defaultDataValue != 0)
                clearOutData();
        }

        // The Standard Unknown DataType
        outData[1] = 3;
    }
    
    /**
     * Creates the ability to create a classification
     * @param type the type of classification
     * @param typeName the name of the type as this is what it's referred to as 
     */
    public final void createClassification(float type, String typeName) {
        // Create list of keys assciated with type
        classifications.put(type, new HashMap<>());
        encodeInitalClassification(type, typeName);
    }

    private void encodeInitalClassification(float type, String typeName) {
        classifications.get(type).put(0, getTag() + " id");
        classifications.get(type).put(1, typeName);
        classifications.get(type).put(2, "Repeated Data?");
        classifications.get(type).put(3, "Real Engine (If Repeated)");
    }
    
    public final void encodeClassification(float type, int index, String classification) {
        if (!classifications.containsKey(type)) {
            System.out.println("Type does not exist");
            return;
        }
        
        if(index == 3 || index == 2 || index <= 0) {
            System.out.println("Index isn't acceptable");
            return;
        }
        
        if(classifications.get(type).containsKey(index))
            classifications.get(type).replace(index, classification);
        else 
            // Add the actual values
            classifications.get(type).put(index, classification);
    }

    /**
     * 
     * @param type
     * @param index
     * @return 
     */
    public final String getClassification(float type, int index) {
        
        int temp = hasIndexClassification(index, type);
        if(temp == 0) return null; 

        if(temp == 1) return classifications.get(type).get(index);
        
        return classifications.get(type).get(1) + "-" + index;
    }
 
    /**
     * Checks if index of the type actually exist
     * @param index 
     * @param type 
     * @return 
     */
    public final int hasIndexClassification(int index, float type) {
        if (!classifications.containsKey(type)) {
            // Type does not exist
            return 0;
        }

        if(classifications.get(type).containsKey(index)) {
            return 1; // Is there
        }
        
        return 2; // Unknown Classification
    }

    public final void setQuit(boolean quit) {
        eManager.setQuit(quit);
    }

    /**
     * Gets data from DataManager and stores it in the Engine.
     * Always stored First;
     * @param f the engine id
     */
    public final void addGenericDataToGet(float f) {
        genericDataToGet.add(f);
    }

    public final void removeGenericDataToGet(float f) {
        genericDataToGet.remove(genericDataToGet.indexOf(f));
    }

    /**
     * Gets data from DataManager and stores it in the Engine.
     * Always stored Second;
     * @param f the data type
     */
    public final void addTypedDataToGet(float f) {
        typedDataToGet.add(f);
    }

    public final void removeTypedDataToGet(float f) {
        typedDataToGet.remove(typedDataToGet.indexOf(f));
    }

    public final void postData(float[] data) {
        outputDataList.add(data.clone());
    }
    
    /**
     * Data to be sent to the manager
     * @param data float array of data to be sent to manager 
     */
    private void addData(float[] data) {
        if (data != null) {
            data[0] = this.id;
            
            if(data[2] != 0f) 
                data[2] = 1; 
            dm.addData(data);        
        }
    }

    public final void repeatData(int indexOfSet, int indexOfData) {
        if(!getStoredData().isEmpty()) {
            if(getStoredData().size() > indexOfSet) {
                if(getStoredData(indexOfSet).length > indexOfData) {
                    float[] data = getStoredData(indexOfSet)[indexOfData].clone();
                    data[2] = 1;
                    data[3] = data[0];
                    postData(data);
                }
            }
            else {
                System.out.println("Wrong Index of Set");
            }
        }
        else {
            System.out.println("No Data Stored");
        }
    }
    
    public void clearOutData() {
        for(int i = 4; i < outData.length; i++) {
            outData[i] = defaultDataValue;
        }
    }
    
    private void disposalLine() {
        System.out.println("Cleaned up the " + getTag() + " system!");
    }

    public final void cleanUp() {
        dispose();
        disposalLine();
    }

    public final String getTag() {
        return tag;
    }

    public final boolean storedDataIsNotEmpty() {
        return !storedData.isEmpty();
    }
    
    private void addStoredData(float[][] data) {
        if (data != null) {
            storedData.add(data);
        }
    }

    private float[][] getGenericData(float type) {
        return dm.findGenericData(type);
    }

    private float[][] getTypedData(float type) {
        return dm.findTypedData(type);
    }

    public final List<float[][]> getStoredData() {
        return storedData;
    }

    public final float[][] getStoredData(int i) {
        if (storedData.size() > i) {
            return storedData.get(i);
        }
        return null;
    }

    private void clearStoredData() {
        storedData.clear();
    }
    
    public final EngineManager getEngineManager() {
        return eManager;
    }

    public final void setEngineManager(EngineManager eManager) {
        this.eManager = eManager;
    }

    public final DataManager getDataManager() {
        return dm;
    }

    public final void setDataManager(DataManager dm) {
        this.dm = dm;
    }

    public final Framework getFramework() {
        return framework;
    }

    public final void setFramework(Framework framework) {
        this.framework = framework;
    }

    public final Debug getDebug() {
        return debug;
    }

    public final int getId() {
        return id;
    }

    public final void setId(int id) {
        this.id = id;
    }
}
