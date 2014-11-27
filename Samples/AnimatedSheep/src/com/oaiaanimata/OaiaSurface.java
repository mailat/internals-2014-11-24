package com.oaiaanimata;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public class OaiaSurface extends SurfaceView implements SurfaceHolder.Callback {
		public boolean surfaceCreated;
		private OaiaRunThread thread; 
	    private Oaia oaie;
	    private int touchedX=0;
	    private boolean isStopped=true;
		
		public OaiaSurface(Context context) {
			super(context);
			prepareSheep();
			
			getHolder().addCallback(this);
		    surfaceCreated = false;
		    setFocusable(true);
	    }

		
		private void prepareSheep()
		{
			oaie = new Oaia();
			
			//Pregatim imaginile
			List<Bitmap> lstFrames = new ArrayList<Bitmap>();
			lstFrames.add(BitmapFactory.decodeResource(getResources(), R.drawable.sheep1));
			lstFrames.add(BitmapFactory.decodeResource(getResources(), R.drawable.sheep2));
			
			oaie.setLstFrames(lstFrames);
			oaie.setSpeed(5);
			oaie.setX(100);
			oaie.setY(100);
			oaie.setTouched(false);
			
		}
		
		 @Override
		    public boolean onTouchEvent(MotionEvent event) {
			 if (event.getAction() == MotionEvent.ACTION_DOWN) {
			     touchedX=(int)event.getX();
			     if (event.getX()> oaie.getX() && event.getX()< oaie.getX()+oaie.getLstFrames().get(0).getWidth()
        				 && event.getY()> oaie.getY() && event.getY()<oaie.getY()+oaie.getLstFrames().get(0).getHeight()
        			    )
			     {
			    	 if (oaie.isTouched())
			    	 {
			    	   oaie.setTouched(false);
			    	 }
			    	 else 
			    	 {
			    	  oaie.setTouched(true);	
					 }
			     }
			 }
			 return true;
		 }
		 
		
		//Aici avem grija sa calculam urmatoarea pozitie unde se va desena oaia
		public void updatePhysics()
		{
			if (touchedX>0)
			{
				
				if (touchedX>oaie.getX())
				{
					
					oaie.setX(oaie.getX()+oaie.getSpeed());
					if(oaie.getX() >= touchedX)
					{
						isStopped=true;
					}
					else
					{
					    isStopped=false;	
					}
				}

				if (touchedX<oaie.getX())
				{
						oaie.setX(oaie.getX()-oaie.getSpeed());
						if(oaie.getX() <= touchedX)
						{
							isStopped=true;
						}
						else
						{
						    isStopped=false;
						}
				}
				
			}
		}
		
		//Aici desenam oaia
		@Override
		public void onDraw(Canvas canvas) {
			canvas.drawColor(Color.argb(255, 69, 139, 00));
			
			if (isStopped)
			{
				canvas.drawBitmap(oaie.getLstFrames().get(0), oaie.getX(), oaie.getY(), null);
			}
			else {
				if (SystemClock.elapsedRealtime() %2 == 0)
				{
				    canvas.drawBitmap(oaie.getLstFrames().get(0), oaie.getX(), oaie.getY(), null);
				}
				else
				{
				    canvas.drawBitmap(oaie.getLstFrames().get(1), oaie.getX(), oaie.getY(), null);
				}
			}
				
			if (oaie.isTouched())
			{
				canvas.drawText("Oaie Bee", oaie.getX(), oaie.getY()-10, new Paint());
			}
			
		}
		
		   public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		    }

		    public void surfaceCreated(SurfaceHolder holder) {
		    	 if (surfaceCreated == false)
		         {
		             createThread();
		             surfaceCreated = true;
		         }
		    }

		    public void surfaceDestroyed(SurfaceHolder holder) {

		    	surfaceCreated = false;
		    }
		    
		    public void createThread ()
		    {
		        thread = new OaiaRunThread(this);
		        thread.setRunning(true);
		        thread.start();
		    }
		    public void terminateThread ()
		    {
		      boolean retry = true;
		      thread.setRunning(false);
		      while (retry) {
		          try {
		              thread.join();
		              retry = false;
		          } catch (InterruptedException e) {
		              // we will try it again and again...
		          }
		      }
		    }
}
