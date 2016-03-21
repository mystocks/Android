package com.example.testcats;

public class Dots {

	private int x;
	private int y;
	private int status;
	public static final int STATUS_OFF = 0;
	public static final int STATUS_ON = 1;
	public static final int STATUS_IN = 2;
	
	public Dots(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
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
	
	public void setXY(int x, int y){
		this.x = x;
		this.y = y;
	}
	public void setDotStatus(int status)
	{
		this.status = status;
	}
	public int getDotStatus()
	{
		return this.status;
	}
}
