package Entities;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import Audio.JukeBox;
import GameStateManager.GameStateManager;
import Main.GamePanel;

public class Ball {

	private int x;
	private int y;
	
	private int dx;
	private int dy;
	
	private int lives;
	
	private long start;
	private long elapsed;
	private boolean collidable;
	
	private boolean right;
	
	private GameStateManager gsm;
	
	private boolean lost;
	private boolean visible;
	private boolean bounce;
	private boolean crush;
	
	private BufferedImage image;
	private Rectangle r1;
	
	private ArrayList<BallTrail>trail;
	
	public Ball(GameStateManager gsm){
		
		try{
			
		this.gsm = gsm;
			
		BufferedImage image2 = ImageIO.read(getClass().getResourceAsStream("/v2/ball.png"));
		image = image2;
		
		trail = new ArrayList<BallTrail>();
		
		lost = false;
		bounce = true;
		crush = true;
		visible = true;
		collidable = true;
		lives = 3;
		x = 10;
		y = 10;
		dx = 0;
		dy = 0;
		
		
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public void update(){
		
		trail.add(new BallTrail(x,y));
		for(int i = 0;i < trail.size();i++){
			if(trail.get(i).getAlpha() < 0){
				trail.remove(i);
			}
		}
		
		if(collidable){
			elapsed = (System.nanoTime() - start)/1000000;
			if(elapsed > 1000){
				collidable = true;
				
				start = 0;
				elapsed = 0;
			}
		}
		
		
		if(x > GamePanel.WIDTH - 15){
			JukeBox.play("bouncer");
			dx = -dx;
			bounce = true;
		}
		if(x < 10){
			JukeBox.play("bouncer");
			dx = -dx;
			bounce = true;
		}
		if(y < 10){
			JukeBox.play("bouncer");
			dy = -dy;
			bounce = true;
		}
		if(y > GamePanel.HEIGHT - 20){
			JukeBox.play("hole");
			visible = false;
			lives--;
		}
		
		if(dx == 2){
			right = true;
		}
		if(dx == -2){
			right = false;
		}
		
		x+=dx;
		y+=dy;
		
		
	}
	public void draw(Graphics2D g){
		
		g.drawImage(image,x,y,null);
		for(int i = 0;i < trail.size();i++){
			trail.get(i).draw(g);
		}
		
	}
	
	public void setX(int i){x = i;}
	public void setY(int i){y = i;}
	
	public int getY(){return y;}
	public int getX(){return x;}
	
	public void setDx(int i){dx = i;}
	public void setDy(int i){dy = i;}
	public void multDy(int i){dy = dy * i;}
	public void multDx(int i){dx = dx * i;}
	
	public int getLives(){return lives;}
	
	public void setVisible(boolean b){visible = b;}
	public boolean getVisible(){return visible;}
	
	public Rectangle getBounds(){
		return new Rectangle(x,y,6,6);
	}
	
	public boolean getRight(){return right;}
	public void setRight(boolean b){right = b;}
	
	public void setBounce(boolean b){bounce = b;}
	public boolean getBounce(){return bounce;}
	
	public BufferedImage getImage(){return image;}
	
	public void setPosition(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public void collision(){
		
		collidable = false;
		System.out.println("false");
		start = System.nanoTime();
	}
	
	public boolean getCollidable(){return collidable;}
	public void setCollidable(boolean b){collidable = b;}
	
	
}
















