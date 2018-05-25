package Entities;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Brick_Orange {
	
	private int x;
	private int y;
	
	private boolean visible;
	
	private BufferedImage image;
	private BufferedImage image3;
	
	private long start;
	
	private Rectangle r1;
	private Rectangle r2;
	private Rectangle r3;
	private Rectangle r4;
	
	private int hits;
	
	public Brick_Orange(int x,int y,String s,String d){
		
		try{
		
			
			
		this.x = x;
		this.y = y;
		visible = true;
		hits = 2;
		
		BufferedImage image2 = ImageIO.read(getClass().getResourceAsStream(s));
		image3 = ImageIO.read(getClass().getResourceAsStream(d));
		image = image2;
		
		r1 = new Rectangle(x,y,20,1);
		r2 = new Rectangle(x,y+8,20,1);
		r3 = new Rectangle(x,y+1,1,6);
		r4 = new Rectangle(x+20,y+1,1,6);
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public void update(){
		
	}
	
	public void draw(Graphics2D g){
		g.drawImage(image,x,y,null);
	}
	
	public void hit(){
		if(hits == 2){
		hits--;
		image = image3;
		start = System.nanoTime();
		}
		if(hits == 1){
		long elapsed = (System.nanoTime()-start)/1000000;
		if(elapsed > 1000){
		visible = false;
		}
		}
		
	}
	public void hit2(){
		
	}
	
	public Rectangle getTop(){return r1;}
	public Rectangle getBottom(){return r2;}
	public Rectangle getLeft(){return r3;}
	public Rectangle getRight(){return r4;}
	
	public boolean getVisible(){return visible;}
	
	public int getX(){return x;}
	public int getY(){return y;}
	
	public Rectangle getBounds(){
		return new Rectangle(x,y,20,8);
	}

}
