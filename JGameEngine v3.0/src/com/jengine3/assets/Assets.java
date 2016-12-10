/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jengine3.assets;

import com.jengine3.engine.Engine;
import com.jengine3.framework.Framework;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author CSLAB313-1740
 */
public class Assets extends Engine{

    private final List<String> assetsToImplement, assetsToImplementNow;
    private int pointer, maximum;
    private boolean handledFile;
    
    public Assets(List<String> assetsToImplement, List<String> assetsToImplementNow, Framework framework) {
        super("assets", assetsToImplement.size(), framework);
        if(assetsToImplementNow != null) {
            resetOutData(outData.length + assetsToImplementNow.size());
        } 
        maximum = outData.length;
        this.assetsToImplement = new ArrayList<>(assetsToImplement);
        this.assetsToImplementNow = new ArrayList<>(assetsToImplementNow);
    }

    @Override
    public void init() {
        addTypedDataToGet(2);
        pointer = 0;
        handledFile = false;
    }

    @Override
    public void update(double delta) {
        if(storedDataIsNotEmpty()) {
            for (int i = 0; i < getStoredData().size(); i++) {
                if (getStoredData(i) != null) {
                    for (float[] data : getStoredData(i)) {
                        if (data != null) {
                            if(data[0] != getId()) {
                                handledFile = smartHandleFile();
                            }    
                        }
                    }
                }
            }
        }
        
        // Handle one file per execution
        if(!handledFile && pointer < maximum)
            handleNextFile();
        
        handledFile = false;
        
    }

    private boolean smartHandleFile() {
        pointer++;
        return true;
    }
    
    private void handleNextFile() {
        pointer++;
    }
    
    @Override
    public void dispose() {
    
    }
}
