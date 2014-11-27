package com.oaiaanimata;

import java.util.List;
import android.graphics.Bitmap;

public class Oaia {

	private int x;
    private int y;
    private int speed;
    private List<Bitmap> lstFrames;
    private boolean isTouched;
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public List<Bitmap> getLstFrames() {
		return lstFrames;
	}
	public void setLstFrames(List<Bitmap> lstFrames) {
		this.lstFrames = lstFrames;
	}
	public boolean isTouched() {
		return isTouched;
	}
	public void setTouched(boolean isTouched) {
		this.isTouched = isTouched;
	}
    
}
