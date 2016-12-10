package com.jengine3.console;

import com.jengine3.engine.Engine;
import com.jengine3.framework.Framework;
import java.util.ArrayList;
import java.util.List;

public class Console extends Engine {

    private DebugWindow dwindow;
    private String message, name, totalMessage;
    
    // Example of how debugwindow could sort output data and such
    private final List<Integer> omit;
    
    private boolean isDebug;
    
    public Console(Framework framework, boolean isDebug) {
        super("console", 7, framework);
        omit = new ArrayList<>();
        this.isDebug = isDebug;
    }

    @Override
    public void init() {
        dwindow = new DebugWindow(this, isDebug);
        message = "";
        totalMessage = "";
        name = "";
        if(isDebug) {
            this.addGenericDataToGet(0); // Console
            this.addGenericDataToGet(1); // Assets
            this.addGenericDataToGet(2); // Input
            this.addGenericDataToGet(3); // Test Engine
            this.addGenericDataToGet(4); // Test Rendering Engine

            this.addTypedDataToGet(2);


            //omit.add(2);
            //omit.add(3);

            // Specifically omits rendering data because it is the only one of that length
            //omit.add(7);
            //omit.add(8);
            //omit.add(9);
        }
    }

    @Override
    public void update(double delta) {
        // Checks if the window has closed. If so, quit
        if(dwindow.dispose(true))
            setQuit(true);
        //repeatData(1, 0);
        getDebug().endQuery("console", this);
        getDebug().startQuery("console");
        if(isDebug) {
            dwindow.setFPS(getFramework().getFpsString());
            iterateConsole(message, name);
        }
    }

    private void iterateConsole(String message, String name) {
        if (storedDataIsNotEmpty() && getFramework().isFirstFrame()) {
            totalMessage = "";
            dwindow.setData("");
            for (int i = 0; i < getStoredData().size(); i++) {
                if (getStoredData(i) != null) {
                    for (float[] data : getStoredData(i)) {
                        if (data != null) {
                            for(int j = 0; j < data.length; j++) {
                                if(!omit.contains(j)) {
                                    name = getEngineManager().getEngine((int) data[0]).getClassification(data[1], j) + ": ";

                                    message += (name + data[j] + ", ");
                                }
                            }
                            //dwindow.appendData("<br><br>" + message.substring(0, message.length() - 2));
                            //System.out.println(message.substring(0, message.length() - 2));
                            totalMessage += "<br><br>" + message.substring(0, message.length() - 2);
                            message = "";
                        }
                    }
                }
            }
            dwindow.setData("<html>" + totalMessage.substring(0, totalMessage.length() - 2) + "</html>");
        }
    }
    
    @Override
    public void dispose() {
        dwindow.dispose(false);
    }

    public DebugWindow getDwindow() {
        return dwindow;
    }
}
