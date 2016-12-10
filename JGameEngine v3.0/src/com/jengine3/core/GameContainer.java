package com.jengine3.core;

import com.jengine3.assets.Assets;
import com.jengine3.console.Console;
import com.jengine3.engine.DataManager;
import com.jengine3.engine.EngineManager;
import com.jengine3.framework.Framework;
import com.jengine3.input.Input;

public class GameContainer implements Runnable {

    // Thread Main
    private volatile Thread thread;
    public volatile boolean running;
    private final double frameTime;
    private String fpsString;
    private final boolean isDebug;
    
    // Classes
    private final AbstractGame game;
    private final EngineManager eManager;
    private final ThreadPool threadPool;
    private final Framework framework;
    
    /**
     * Constructor
     * @param title title of game
     * @param width width of screen
     * @param height height of screen
     * @param frameRate frame rate of screen
     * @param game reference to abstract game
     * @param isDebug sets mode of game engine
     */
    public GameContainer(String title, int width, int height, double frameRate, AbstractGame game, boolean isDebug) {
        framework = new Framework(title, width, height);
        eManager = new EngineManager(new DataManager(), framework);
        frameTime = 1.0 / frameRate;
        threadPool = new ThreadPool();
        this.game = game;
        this.isDebug = isDebug;
    }

    /**
     * Initializes the default engines in the game engine
     */
    private void initEngines() {
        eManager.addEngine(new Console(framework, isDebug));
        eManager.addEngine(new Assets(game.getAssets(), game.getRightNowAssets(), framework));
        eManager.addEngine(new Input(framework));
    }

    /**
     * Starts the main thread of the game
     */
    public void start() {
        if (thread == null) {
            thread = new Thread(this, "Main Thread");
            running = true;
            thread.start();
        }
    }

    @Override
    public void run() {
        fpsString = "";
        
        // Initializing
        game.init();
        framework.init();
        initEngines();
        game.initEngines(framework, eManager);
        eManager.init();

        // Time Setup
        int frames = 0;
        double unprocessedTime = 0.0;
        double frameCounterTime = 0;

        long previousTime = System.nanoTime();
        int updates_per_second = 0;

        while (running) {
            boolean render = false;
            
            long currentTime = System.nanoTime();
            long passedTime = currentTime - previousTime;
            previousTime = currentTime;

            unprocessedTime += passedTime / 1000000000.0;
            frameCounterTime += passedTime / 1000000000.0;

            if (frameCounterTime >= 1.0) {
                // FPS String
                fpsString = (1000.0 / frames) + " ms per frame (" + frames
                        + " fps, " + updates_per_second + " ups)";
                framework.setFpsString(fpsString);
                if(!isDebug)
                    System.out.println(fpsString);
                updates_per_second = 0;
                frames = 0;
                frameCounterTime = 0.0;
            }

            
            
            while (unprocessedTime > frameTime) {
                framework.startUpdate();
                update(frameTime);
                framework.endUpdate(this);
                render = true;
                unprocessedTime -= frameTime;
                updates_per_second++;
            }

            if (render) {
                if(frames == 0)
                    framework.setFirstFrame(true);
                else
                    framework.setFirstFrame(false);
                frames++;
                framework.startRender();
                render();
                framework.endRender();
            } else {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException exc) {
                    exc.printStackTrace();
                }
            }

            if (eManager.isQuit()) {
                running = false;
            }
        }

        dispose();
    }

    /**
     * Updates the engines
     * @param delta
     */
    private void update(double delta) {
        threadPool.preupdate(eManager);
        threadPool.update(eManager, delta);
        threadPool.postOutputData(eManager);
        threadPool.startup(eManager);
    }

    /**
     * Updates the rendering engines
     */
    private void render() {
        threadPool.examineDrawing(eManager);
        threadPool.render(eManager);
        threadPool.postRenderingOutputData(eManager);
        threadPool.clearDrawingList(eManager);
    }

    /**
     * Dispose any required resources
     */
    public void dispose() {
        threadPool.dispose(eManager);
        framework.dispose();
        System.out.println("Cleaned Up!");
        System.exit(0);
    }
}
