package com.jengine3.engine;

import java.util.ArrayList;

import com.jengine3.framework.Framework;
import java.util.List;

public class EngineManager {

	private final List<Engine> engines;
	private final List<Engine> enginesR;
	
	private volatile DataManager dm;
	private volatile Framework framework;
	
	private volatile double pointer;
	
	private volatile boolean quit;
	
	private int engineCounter;
	
	public EngineManager(DataManager dm, Framework framework) {
		this.dm = dm;
		this.framework = framework;
		quit = false;
		pointer = -1;
		engineCounter = 0;
                engines = new ArrayList<>();
                enginesR = new ArrayList<>();
	}
	
	public final void init() {
            engines.stream().forEach((e) -> {
                e.init();
            });
            enginesR.stream().forEach((e) -> {
                e.init();
            });
	}
	
	public final void addEngine(Engine engine) {
		engine.setEngineManager(this);
		engine.setDataManager(dm);
		engine.setFramework(framework);
		engine.setId(engineCounter);
		engineCounter++;
		engines.add(engine);
	}
	
	public final void addRenderingEngine(Engine engine) {
		engine.setEngineManager(this);
		engine.setDataManager(dm);
		engine.setFramework(framework);
		engine.setId(engineCounter);
		engineCounter++;
		enginesR.add(engine);
	}
	
	public final Engine getEngine(int id) {
		Engine e = null;
		
		for(Engine ee : engines) {
			if(ee.getId() == id) {
				e = ee; 
				break;
			}
		}
		
		if(e == null) {
			for(Engine ee : enginesR) {
				if(ee.getId() == id) {
					e = ee; 
					break;
				}
			}
		}
		
		return e;
	}
	
	public final void removeEngine(String s) {
		Engine ee = null;
		for(Engine e: engines) {
			if(e.getTag().equalsIgnoreCase(s)) {
				ee = e;
			}
		}
		
		if(ee == null) return;
		
		engines.remove(ee);
	}
	
	public final void removeRenderingEngine(String s) {
		Engine ee = null;
		for(Engine e: enginesR) {
			if(e.getTag().equalsIgnoreCase(s)) {
				ee = e;
			}
		}
		
		if(ee == null) return;
		
		enginesR.remove(ee);
	}

	public final boolean isQuit() {
		return quit;
	}

	public final void setQuit(boolean quit) {
		this.quit = quit;
	}

	public final List<Engine> getEngines() {
		return engines;
	}
        
        public final List<Engine> getEnginesR() {
		return enginesR;
	}

	public final DataManager getDm() {
		return dm;
	}

	public final double getPointer() {
		return pointer;
	}

	public final void setPointer(double pointer) {
		this.pointer = pointer;
	}
}
