package com.jengine3.framework;

import com.jengine3.core.GameContainer;
import static org.lwjgl.glfw.Callbacks.errorCallbackPrint;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetCursorPosCallback;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetMouseButtonCallback;
import static org.lwjgl.glfw.GLFW.glfwSetScrollCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL11.GL_VERSION;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glGetString;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.nio.ByteBuffer;

import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWScrollCallback;
import org.lwjgl.glfw.GLFWvidmode;
import org.lwjgl.opengl.GLContext;

import kuusisto.tinysound.TinySound;
import org.joml.Vector3f;

public class Framework {

    private GLFWErrorCallback errorCallback;
    private GLFWKeyCallback keyCallback;
    private GLFWCursorPosCallback cursorPosCallback;
    private GLFWMouseButtonCallback mouseButtonCallback;
    private GLFWScrollCallback scrollCallback;
    
    // Window
    private final String title;
    private final int width, height;
    private long window;
    
    private Draw draw;
    
    private String fpsString;
    private boolean firstFrame;
    
    public Framework(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
        fpsString = "";
        firstFrame = false;
    }

    public void init() {
        // Setup an error callback. The default implementation
        // will print the error message in System.err
        glfwSetErrorCallback(errorCallback = errorCallbackPrint(System.err));

        if (glfwInit() != GL_TRUE) {
                // Throw an error.
                throw new IllegalStateException("Unable to initialize GLFW");
        }

        // Configure our window
        glfwDefaultWindowHints(); // optional, the current window hints are
                                                                // already the default
        glfwWindowHint(GLFW_VISIBLE, GL_FALSE); // the window will stay hidden
                                                                                        // aft
        glfwWindowHint(GLFW_RESIZABLE, GL_FALSE); // Allows our window to not be
                                                                                                // resizable (too
                                                                                                // complicated)

        window = glfwCreateWindow(width, height, title, NULL, NULL);

        if (window == NULL) {
            // Throw an Error
            throw new RuntimeException("Failed to create the GLFW window");
        }

        glfwSetKeyCallback(window, keyCallback = new KeyHandler());
        glfwSetCursorPosCallback(window,
                        cursorPosCallback = new CursorPosHandler());
        glfwSetMouseButtonCallback(window,
                        mouseButtonCallback = new MouseButtonHandler());
        glfwSetScrollCallback(window,
                        scrollCallback = new ScrollWheelHandler());

        // Center window
        ByteBuffer vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        int x = GLFWvidmode.width(vidmode);
        int y = GLFWvidmode.height(vidmode);
        int winposx = (int) ((x - width) / 2.0);
        int winposy = (int) ((y - height) / 2.0);
        glfwSetWindowPos(window, winposx, winposy);

        glfwMakeContextCurrent(window);

        GLContext.createFromCurrent();
        glfwSwapInterval(1);

        glfwShowWindow(window);

        initGL();
        TinySound.init();
    }

    private void initGL() {
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();

        glOrtho(0, width, 0, height, -1, 1);

        glMatrixMode(GL_MODELVIEW);

        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glEnable(GL_DEPTH_TEST);

        draw = new Draw(width, height);
        
        System.out.println("OpenGL: " + glGetString(GL_VERSION));
    }

    public void startUpdate() {
        glfwPollEvents();
    }
    
    public void endUpdate(GameContainer gc) {
        KeyHandler.update();
        MouseButtonHandler.update();
        if (glfwWindowShouldClose(window) == GL_TRUE) {
            gc.running = false;
	}
    }
    
    public void startRender() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);	
        //draw.fillRect(pos, width, height, Pixel.getColor(Color.yellow));
    }
    
    public void endRender() {
        glfwSwapBuffers(window);
    }
    
    public void dispose() {
        draw.dispose();
        TinySound.shutdown();
        
        // GLFW Cleanup
        glfwDestroyWindow(window);
        keyCallback.release();
        cursorPosCallback.release();
        mouseButtonCallback.release();
        scrollCallback.release();
        
        // Final Cleanup
        glfwTerminate();
        errorCallback.release();
        
        System.out.println("Framework Cleaned!");
    }
    
    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return height;
    }
    
    public Draw getDraw() {
        return draw;
    }
    
    public String getFpsString() {
        return fpsString;
    }
    
    public boolean isFirstFrame() {
        return firstFrame;
    }
    
    public void setFpsString(String fpsString) {
        this.fpsString = fpsString;
    }
    
    public void setFirstFrame(boolean b) {
        firstFrame = b;
    }
}
