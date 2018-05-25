package Entities;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.imageio.ImageIO;

public class Brick_Bonus {
	
	private int x;
	private int y;
	
	private boolean visible;
	private boolean collidable;
	
	private BufferedImage Yellow;
	private BufferedImage Yellow2;
	private BufferedImage Green;
	private BufferedImage Green2;
	private BufferedImage Red;
	private BufferedImage Red2;
	private BufferedImage Blue;
	private BufferedImage Blue2;
	private BufferedImage Purple;
	private BufferedImage Purple2;
	private BufferedImage currentImage;
	
	private Random ran;
	private int one;
	private int two;
	
	private BufferedImage image;
	private BufferedImage image3;
	
	private long start;
	
	private Rectangle r1;
	private Rectangle r2;
	private Rectangle r3;
	private Rectangle r4;
	
	private int hits;
	
	public Brick_Bonus(int x,int y){
		
		try{
		
			
			
		this.x = x;
		this.y = y;
		visible = true;
		hits = 2;
		collidable = true;
		
		
		
		Blue = ImageIO.read(getClass().getResourceAsStream("/v2/blue.png"));
		Blue2 = ImageIO.read(getClass().getResourceAsStream("/v2/blue2.png"));
		
		Green = ImageIO.read(getClass().getResourceAsStream("/v2/green.png"));
		Green2 = ImageIO.read(getClass().getResourceAsStream("/v2/green2.png"));
		
		Yellow = ImageIO.read(getClass().getResourceAsStream("/v2/yellow.png"));
		Yellow2 = ImageIO.read(getClass().getResourceAsStream("/v2/yellow2.png"));
		
		Red = ImageIO.read(getClass().getResourceAsStream("/v2/red.png"));
		Red2 = ImageIO.read(getClass().getResourceAsStream("/v2/red2.png"));
		
		Purple = ImageIO.read(getClass().getResourceAsStream("/v2/purple.png"));
		Purple2 = ImageIO.read(getClass().getResourceAsStream("/v2/purple2.png"));
		
		currentImage = ImageIO.read(getClass().getResourceAsStream("/v2/powerup.png"));
		
		
		r1 = new Rectangle(x,y,20,1);
		r2 = new Rectangle(x,y+8,20,1);
		r3 = new Rectangle(x,y+1,1,6);
		r4 = new Rectangle(x+20,y+1,1,6);
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public void update(){
		if(collidable == false){
			start = System.nanoTime();
			long elapsed = (System.nanoTime()-start)/1000000;
			if(elapsed > 500){
				collidable = true;
				start = System.nanoTime();
			}
		}
		
	}
	
	public void draw(Graphics2D g){
		g.drawImage(currentImage,x,y,null);
	}
	
	public void hit(){
	
			collidable = false;
			hits--;
			if(hits == 0){
				visible = false;
			}
			if(hits == 1){
				
				image = image3;
			}
		}
	
		
	
	public void hit2(){
		
	}
	
public void changeColor(){
	
	
		
		if(hits == 1){
			one = ran.nextInt(5) + 1;
			if(one  == 1){
				currentImage = Blue;
			}
			if(one == 2){
				currentImage = Red;
			}
			if(one == 3){
				currentImage = Green;
			}
			if(one == 4){
				currentImage = Yellow;
			}
			if(one == 5){
				currentImage = Purple;
			}
		}
		
		if(hits == 2){
			one = ran.nextInt(5) + 1;
			if(one  == 1){
				currentImage = Blue;
			}
			if(one == 2){
				currentImage = Red;
			}
			if(one == 3){
				currentImage = Green;
			}
			if(one == 4){
				currentImage = Yellow;
			}
			if(one == 5){
				currentImage = Purple;
			}
		}
	}
	
	public Rectangle getTop(){return r1;}
	public Rectangle getBottom(){return r2;}
	public Rectangle getLeft(){return r3;}
	public Rectangle getRight(){return r4;}
	
	public boolean getVisible(){return visible;}
	
	public int getX(){return x;}
	public int getY(){return y;}
	public boolean getCollidable(){return collidable;}
	
	public Rectangle getBounds(){
		return new Rectangle(x,y,20,8);
	}

}
