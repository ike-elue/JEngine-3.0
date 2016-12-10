package com.jengine3.test;

import com.jengine3.core.AbstractGame;
import com.jengine3.engine.EngineManager;
import com.jengine3.framework.Framework;

public class Game extends AbstractGame {

	public static void main(String[] args) {
		Game g = new Game("Test", 640, 480, 60, true);
		g.start();
	}
	
	public Game(String title, int width, int height, double frameRate, boolean isDebug) {
		super(title, width, height, frameRate, isDebug);
		
	}

	@Override
	public void init() {
		
	}

	@Override
	public void initEngines(Framework framework, EngineManager eManager) {
		eManager.addEngine(new TestEngine(framework));
		eManager.addRenderingEngine(new TestRenderingEngine(framework));
	}

}
