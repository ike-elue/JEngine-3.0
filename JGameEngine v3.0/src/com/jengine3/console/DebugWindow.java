package com.jengine3.console;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class DebugWindow implements ActionListener, WindowListener {

    private final JFrame frame;
    private final Console c;
    private boolean disposed;
    private final boolean isDebug;
    private final JLabel fps, data;
    
    public DebugWindow(Console c, boolean isDebug) {
        this.c = c;
        this.isDebug = isDebug;    
        
        frame = new JFrame("Debug Window");
        fps = new JLabel();
        data = new JLabel();

        if(isDebug) {
            disposed = false;

            
            
            frame.setSize(400, 320);
            frame.setLocation(50, 50);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            //frame.setResizable(false);
            frame.addWindowListener(this);
            frame.setLayout(new BorderLayout());

            frame.add(fps, BorderLayout.NORTH);
            frame.add(data, BorderLayout.CENTER);

            frame.setVisible(true);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
    
    public boolean dispose(boolean checkingFrame) {
        if(frame != null) {
            if(checkingFrame) {
                return disposed;
            }
            disposed = true;
            return disposed;
        }
        
        return disposed;
    }

    @Override
    public void windowOpened(WindowEvent e) {}

    @Override
    public void windowClosing(WindowEvent e) {
        c.setQuit(true);
        dispose(false);
    }

    @Override
    public void windowClosed(WindowEvent e) {}

    @Override
    public void windowIconified(WindowEvent e) {}

    @Override
    public void windowDeiconified(WindowEvent e) {}

    @Override
    public void windowActivated(WindowEvent e) {}

    @Override
    public void windowDeactivated(WindowEvent e) {}
    
    public void setFPS(String s) {
        if(isDebug)
            fps.setText("FPS: " + s);
    }
    
    public void setData(String s) {
        if(isDebug)
            data.setText(s);
    }
}
