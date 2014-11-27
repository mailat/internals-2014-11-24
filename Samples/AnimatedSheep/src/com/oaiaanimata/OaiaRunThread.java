package com.oaiaanimata;

import android.graphics.Canvas;

public class OaiaRunThread extends Thread {
    private OaiaSurface panel;
    private boolean run = false;

    public OaiaRunThread(OaiaSurface pan) {
        panel = pan;
    }
    
    public void setRunning(boolean Run) {
        run = Run;
    }

    public boolean isRunning() {
        return run;
    }


    @Override
    public void run() {
        Canvas c;
        while (run) {
            c = null;
            try {
                c = panel.getHolder().lockCanvas(null);
                  synchronized (panel.getHolder()) {
                	
                	panel.updatePhysics();
                	panel.onDraw(c);
                	
                }
            } finally {
                if (c != null) {
                    panel.getHolder().unlockCanvasAndPost(c);
                }
            }
        }
    }    
}