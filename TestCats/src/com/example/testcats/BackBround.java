package com.example.testcats;

import java.util.HashMap;
import java.util.Vector;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Toast;

public class BackBround extends SurfaceView implements OnTouchListener{
	
	private static int DIAMETER;
	private static final int COL = 10;
	private static final int ROW = 10;
	private static final int BLOCKS = 10;
	private Dots allDots[][]; 
	private Dots cat;
	private boolean justInit = false;
	
	public BackBround(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		getHolder().addCallback(callback);
		allDots = new Dots[ROW][COL];
		for(int i = 0; i < ROW; i++)
			for(int j = 0; j < COL; j++)
			{
				allDots[i][j] = new Dots(j,i);
			}
		setOnTouchListener(this);//设置点击事件
		initGame();
	}
	private void reRaw(){
		Canvas canvas = getHolder().lockCanvas();
		canvas.drawColor(Color.LTGRAY);
		Paint paint = new Paint();
		paint.setFlags(paint.ANTI_ALIAS_FLAG); //设置paint抗锯齿
		RectF rectf = new RectF();
		int offset = 0;
		for(int i = 0; i < COL; i ++){
			for(int j = 0; j < ROW; j ++)
			{
				if(j % 2 == 1){
					offset = DIAMETER / 2;
				}
				else
				{
					offset = 0;
				}
				Dots one = getDots(i,j);
				switch(one.getDotStatus()){
				case Dots.STATUS_IN:
					paint.setColor(0xFFFF0000);
					break;
				case Dots.STATUS_OFF:
					paint.setColor(0xFFEEEEEE);
					break;
				case Dots.STATUS_ON:
					paint.setColor(0xFFFFAA00);
					break;
				default:
					break;
				}
				rectf.left = one.getX() * DIAMETER + offset;
				rectf.right = (one.getX() + 1) * DIAMETER + offset;
				rectf.top = one.getY() * DIAMETER;
				rectf.bottom = (one.getY() + 1) * DIAMETER;
				canvas.drawOval(rectf, paint);
			}
		}
		getHolder().unlockCanvasAndPost(canvas);
	}
	
	private Callback callback = new Callback(){

		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			// TODO Auto-generated method stub
			reRaw();
		}

		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width,
				int height) {
			// TODO Auto-generated method stub
			DIAMETER = width / (COL + 1);
			reRaw();
		}

		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {
			// TODO Auto-generated method stub
			
		}
	};
	private Dots getDots(int x, int y){
		return allDots[y][x];
	}
	
	private boolean isAtEdge(Dots d){
		//是否处于边界
		if(d.getX() * d.getY() == 0 || d.getX() + 1 == COL || d.getY() + 1 == ROW){
			return true;
		}
		return false;
	}
	
	private Dots getAdjacentDots(Dots one, int direction){
		
		switch(direction){
		case 1:
			return getDots(one.getX() - 1, one.getY());
		case 2:
			if(one.getY() % 2 == 0){
				return getDots(one.getX() -1, one.getY() -1);				
			}
			else{
				return getDots(one.getX(), one.getY() - 1);
			}
		case 3:
			if(one.getY() % 2 == 0){
				return getDots(one.getX(), one.getY() -1);				
			}
			else{
				return getDots(one.getX() + 1, one.getY() -1);
			}
		case 4:
			return getDots(one.getX() + 1, one.getY());
		case 5:
			if(one.getY() % 2 == 0){
				return getDots(one.getX(), one.getY() + 1);				
			}
			else{
				return getDots(one.getX() + 1, one.getY() + 1);
			}
		case 6:
			if(one.getY() % 2 == 0){
				return getDots(one.getX() - 1, one.getY() +1);				
			}
			else{
				return getDots(one.getX(), one.getY() + 1);
			}
			default:
				break;
		}
		return one;
	}
	
	private int getDistance(Dots one, int direction){
		
		int distance = 0;
		Dots next = one;
		while(true){
			next = getAdjacentDots(next, direction);
			if(next.getDotStatus() == Dots.STATUS_ON){
				return distance * -1;
			}
			if(isAtEdge(next)){
				return ++distance;
			}
			distance ++;
		}
	}
	
	private void moveTo(Dots one){
		one.setDotStatus(Dots.STATUS_IN);
		getDots(cat.getX(), cat.getY()).setDotStatus(Dots.STATUS_OFF);
		cat.setXY(one.getX(), one.getY());
	}
	
	private void move(){
		Dots one;
		if(isAtEdge(cat)){
			lose();
			return;
		}
		Vector<Dots> available = new Vector<Dots>();
		HashMap<Integer, Integer> positive = new HashMap<Integer, Integer>();
		for(int i = 1; i < 7; i++){
			one = getAdjacentDots(cat, i);
			if(one.getDotStatus() == Dots.STATUS_OFF){
				available.add(one);
			}
			int dis = getDistance(cat, i);
			positive.put(i, dis);
		}
		if(available.size() == 0){
			win();
		}
		else if(available.size() == 1){
			moveTo(available.get(0));
		}
		else if(justInit){
			int index = (int) ((Math.random() * 1000)%available.size());
			moveTo(available.get(index));
		}
		else{
			int max, min;
			int maxIndex = 1, minIndex = 1;
			max = 0;
			min = 1000;
			for(int i =1; i < 7; i ++){
				one = getAdjacentDots(cat, i);
				int s = positive.get(one);
				if(s > max){
					max = s;
					maxIndex = i;
				}
				if(s < min){
					min = s;
					minIndex = i;
				}
			}
			if(max > 0){
				one = getAdjacentDots(cat, maxIndex);
			}
			else{
				one = getAdjacentDots(cat, minIndex);
			}
			moveTo(one);
		}
	}
	
	private void lose(){
		Toast.makeText(getContext(), "Lose", Toast.LENGTH_SHORT).show();
	}
	
	private void win(){
		Toast.makeText(getContext(), "Win", Toast.LENGTH_SHORT).show();
	}
	private void initGame(){
		for(int i = 0; i < ROW; i++)
			for(int j = 0; j < COL; j++)
			{
				allDots[i][j].setDotStatus(Dots.STATUS_OFF);
			}
		getDots(4,5).setDotStatus(Dots.STATUS_IN);
		cat = new Dots(4,5);
		for(int z = 0; z < BLOCKS;)
		{
			int i = (int) ((Math.random() * 1000) % COL);
			int j = (int) ((Math.random() * 1000) % ROW);
			if(getDots(i, j).getDotStatus() == Dots.STATUS_OFF)
			{
				getDots(i, j).setDotStatus(Dots.STATUS_ON);
				z++;
				System.out.println(z);
			}
		}
		justInit = true;
	}
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		if(event.getAction() == MotionEvent.ACTION_UP){
			//Toast.makeText(getContext(), event.getX() + ":" + event.getY(), Toast.LENGTH_SHORT).show();
			int x, y;
			y = (int) (event.getY() / DIAMETER);
			if(y % 2 == 0){
				x = (int) (event.getX() / DIAMETER);
			}
			else{
				x = (int) ((event.getX() - DIAMETER / 2) / DIAMETER);
			}
			if(x >= COL || y >= ROW){
				initGame();
			}
			else if(getDots(x,y).getDotStatus() == Dots.STATUS_OFF){
				getDots(x, y).setDotStatus(Dots.STATUS_ON);
				move();
			}
			reRaw();
		}
		return true;
	}
}
