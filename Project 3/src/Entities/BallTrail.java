package Entities;

import java.awt.Color;
import java.awt.Graphics2D;

public class BallTrail {
	
	private int alpha;
	
	private int x;
	private int y;
	
	public BallTrail(int x,int y){
		
		
		this.x = x;
		this.y = y;
		alpha = 128;
		
	}
	
	
	public void Update(){
		
		alpha--;
		
	}
	
	
	public void draw(Graphics2D g){
		g.setColor(new Color(255,255,255,alpha));
		//g.fillRect(6, 6, x, y);
		
	}
	
	public int getAlpha(){return alpha;}

}
