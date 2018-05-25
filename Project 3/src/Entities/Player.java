package Entities;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import Main.GamePanel;

public class Player {
	
	private int x;
	private int y;
	
	private int rx;
	private int lx;
	
	private BufferedImage image;
		
	
	public Player(String s,int x, int y){
		
		try{
			
			BufferedImage image2 = ImageIO.read(getClass().getResourceAsStream(s));
			image = image2;
			
			this.x = x;
			this.y = y;
			
		} catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public void update(){
		x+=lx;
		x+=rx;
		
		if(x < 10)x = 10;
		if(x > GamePanel.WIDTH-55)x = GamePanel.WIDTH-55;
	}
	
	
	public void draw(Graphics2D g){
		
		g.drawImage(image,x,y,null);
		
	}
	
	public Rectangle leftHalf(){
		return new Rectangle(x,y,60,10);
	}
	public Rectangle rightHalf(){
		return new Rectangle(x+17,y,17,10);
	}
	
	public void setDx(int i){lx = i;}
	public void setRx(int i){rx = i;}
	
	public int getX(){return x;}
	public int getY(){return y;}
	

}












