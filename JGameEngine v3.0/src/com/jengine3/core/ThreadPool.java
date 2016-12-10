package com.jengine3.core;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.jengine3.engine.EngineManager;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ThreadPool {

    private final ExecutorService executor;
    private final List<Future> futures;
    
    /**
     * Constructor
     */
    public ThreadPool() {
        executor = Executors.newFixedThreadPool(2);
        futures = new ArrayList<>();
    }

    /**
     * Runs the run() method in each engine
     * @param eManager reference to EngineManager (holds the list)
     */
    private void execute(EngineManager eManager) {
        eManager.getEngines().stream().forEach((e) -> {
            Future f = executor.submit(e);
            futures.add(f);
        });
        
        futures.stream().forEach((f) -> {
            try {
                f.get();
            } catch (InterruptedException | ExecutionException ex) {
                Logger.getLogger(ThreadPool.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        futures.clear();
    }

    /**
     * Runs the run() method in each rendering engine
     * @param eManager reference to EngineManager (holds the list)
     */
    private void executeR(EngineManager eManager) {
        eManager.getEnginesR().stream().forEach((e) -> {
            Future f = executor.submit(e);
            futures.add(f);
        });
        
        futures.stream().forEach((f) -> {
            try {
                f.get();
            } catch (InterruptedException | ExecutionException ex) {
                Logger.getLogger(ThreadPool.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        futures.clear();
        
    }
    
    /**
     * Clears all stored data in engines and makes the new data the old data 
     * @param eManager 
     */
    public void startup(EngineManager eManager) {
        eManager.setPointer(-1);
        execute(eManager);
        eManager.getDm().swicthData();
    }

    /**
     * Executor tells engines to grab any specified data
     * @param eManager 
     */
    public void preupdate(EngineManager eManager) {
        eManager.setPointer(-3);
        execute(eManager);
    }

    public void postOutputData(EngineManager eManager) {
        eManager.setPointer(-2);
        execute(eManager);
    }
    
    /**
     * Method where you are only supposed to act on gathered data and/or own functions based on delta
     * @param eManager
     * @param delta 
     */
    public void update(EngineManager eManager, double delta) {
        eManager.setPointer(delta);
        execute(eManager);
    }

    /**
     * Executor tells rendering engines to grab any specified data
     * @param eManager 
     */
    public void examineDrawing(EngineManager eManager) {
        eManager.setPointer(-3);
        executeR(eManager);
    }
    
    /**
     * Method where the rendering engines are supposed to update
     * @param eManager 
     */
    public void render(EngineManager eManager) {
        eManager.setPointer(0);
        executeR(eManager);
    }
    
    public void postRenderingOutputData(EngineManager eManager) {
        eManager.setPointer(-2);
        executeR(eManager);
    }
    
    /**
     * Method where the rendering engines clear their data
     * @param eManager 
     */
    public void clearDrawingList(EngineManager eManager) {
        eManager.setPointer(-1);
        executeR(eManager);
    }

    
    public void dispose(EngineManager eManager) {
        eManager.getEngines().stream().forEach((engine) -> {
            engine.cleanUp();
        });

        eManager.getEnginesR().stream().forEach((engine) -> {
            engine.cleanUp();
        });
        
        try {
            System.out.println("Attempting to shutdown executor...");
            executor.shutdown();
            executor.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.err.println("Tasks interrupted");
        } finally {
            if (!executor.isTerminated()) {
                System.err.println("Cancel non-finished tasks");
            }
            executor.shutdownNow();
            System.out.println("Executor shutdown finished!");
        }
    }
}
