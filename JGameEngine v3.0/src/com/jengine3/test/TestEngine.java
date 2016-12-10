package com.jengine3.test;

import java.util.Random;

import com.jengine3.engine.Engine;
import com.jengine3.framework.Framework;

public class TestEngine extends Engine {

    private Random rand;

    public TestEngine(Framework framework) {
        super("test engine", 7, framework);
    }

    @Override
    public void init() {
        rand = new Random();
    }

    @Override
    public void update(double delta) {
        outData[4] = (float) delta;
        outData[5] = 1;
        outData[6] = rand.nextFloat();
        postData(outData);
    }

    @Override
    public void dispose() {

    }

}
