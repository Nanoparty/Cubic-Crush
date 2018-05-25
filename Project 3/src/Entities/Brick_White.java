package Entities;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Brick_White {
	
	private int x;
	private int y;
	
	private boolean visible;
	
	private BufferedImage image;
	
	private Rectangle r1;
	private Rectangle r2;
	private Rectangle r3;
	private Rectangle r4;
	
	public Brick_White(int x,int y,String s){
		
		try{
		
		this.x = x;
		this.y = y;
		visible = true;
		
		BufferedImage image2 = ImageIO.read(getClass().getResourceAsStream(s));
		image = image2;
		
		r1 = new Rectangle(x,y,32,1);
		r2 = new Rectangle(x,y+16,32,1);
		r3 = new Rectangle(x,y,1,16);
		r4 = new Rectangle(x+32,y,1,16);
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public void update(){
		
	}
	
	public void draw(Graphics2D g){
		g.drawImage(image,x,y,null);
	}
	
	public Rectangle getTop(){return r1;}
	public Rectangle getBottom(){return r2;}
	public Rectangle getLeft(){return r3;}
	public Rectangle getRight(){return r4;}

}
