package com.jengine3.core;

import com.jengine3.engine.EngineManager;
import com.jengine3.framework.Framework;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractGame {

	private final GameContainer g;
	private final List<String> assets, rightNowAssets;
        
        /**
         * Constructor
         * @param title title of game
         * @param width width of screen
         * @param height height of screen
         * @param frameRate frame rate of screen
         */
	public AbstractGame(String title, int width, int height, double frameRate, boolean isDebug) {
		g = new GameContainer(title, width, height, frameRate, this, isDebug);
                assets = new ArrayList<>();
                rightNowAssets = new ArrayList<>();
	}
	
        /**
         * Initialize special features in game if any (None Yet)
         */
	public abstract void init();
        
        /**
         * Adds created Engines into game engine to be utilized in the actual game
         * @param framework
         * @param eManager 
         */
	public abstract void initEngines(Framework framework, EngineManager eManager);
	
        /**
         * Starts the game functions
         */
	public final void start() {
		g.start();
	}
	
        public final void addAsset(String file) {
            assets.add(file);
        }
        
        public final void addRightNowAsset(String file) {
            rightNowAssets.add(file);
        }
        
        /**
         * @return returns GameContainer 
         */
	public final GameContainer getGameContainer() {
		return g;
	}
        
        public final List<String> getAssets() {
            return assets;
        }
        
        public final List<String> getRightNowAssets() {
            return rightNowAssets;
        }
}
