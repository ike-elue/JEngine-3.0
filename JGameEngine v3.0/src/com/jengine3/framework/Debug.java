package com.jengine3.framework;

import com.jengine3.engine.Engine;
import java.util.HashMap;
import java.util.Map;

public class Debug {

	private final Map<String, Float> profiles = new HashMap<>();
	
	public final void startQuery(String name) {
		profiles.put(name, (float) System.nanoTime());
	}
	
	public final void endQuery(String name, Engine e) {
                if(!profiles.containsKey(name)) {return;}
		
                // First 4 indexes are 0, so they don't matter
                float[] logData = new float[7];
		logData[5] = System.nanoTime();
                logData[4] = profiles.get(name);
		logData[6] = (logData[5] - logData[4])/1000000000;
		e.postData(logData);
	}
}
