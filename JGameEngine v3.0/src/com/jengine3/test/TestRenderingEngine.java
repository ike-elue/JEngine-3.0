package com.jengine3.test;

import com.jengine3.engine.Engine;
import com.jengine3.framework.Framework;

public class TestRenderingEngine extends Engine{

	public TestRenderingEngine(Framework framework) {
		super("test rendering engine", 10, framework);	
	}

	@Override
	public void init() {
            encodeClassification(2f, 1, "Data");
	}
        
	@Override
	public void update(double delta) {
            outData[4] = -1;
            outData[5] = 1;
            outData[6] = 0;
            postData(outData);
	}

	@Override
	public void dispose() {
		
	}

}
