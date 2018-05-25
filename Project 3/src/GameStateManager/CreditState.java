package GameStateManager;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import Audio.JukeBox;

import javax.imageio.ImageIO;

import Main.GamePanel;
import TileMap.Background;

public class CreditState extends GameState {
	
	private Background bg;
	private Background fg;
	
	private Background Cg;
	private Background Sg;
	
	private BufferedImage image;
	
	private int currentChoice = 0;
	private String[] options = {
			"Back"
		};
	
	private Font title;
	private Font text;
	private Font text2;
	private Font text3;
	
	private int dy;
	
	public CreditState(GameStateManager gsm){
		
		this.gsm = gsm;
		
		
		
		
		init();
		
	}
	
	public void init() {
		
		try{
			
			
			
			Font scFont = Font.createFont(
					Font.TRUETYPE_FONT,
					getClass().getResourceAsStream("/Fonts/blocked.ttf"));
			
			bg = new Background("/v2/background.png",1);
			fg = new Background("/v2/strips.png",1);
			Sg = new Background("/v2/scroll.png",1);
			Cg = new Background("/v2/backgroundCover.png",1);
			fg.setVector(0, 0);
			Sg.setVector(0,.5);
			title = scFont.deriveFont(Font.PLAIN,20f);
			text = scFont.deriveFont(Font.PLAIN,10f);
			text2 = scFont.deriveFont(Font.PLAIN,8f);
			text3 = scFont.deriveFont(Font.PLAIN,9f);
			image = ImageIO.read(getClass().getResourceAsStream("/v2/WindowLarge.png"));
			
		
			if(!JukeBox.isPlaying("menumusic")) {
				JukeBox.loop("menumusic", 0, JukeBox.getFrames("menumusic") - 1);
			}
			
			
			JukeBox.stop("bgmusic1");
			
		}catch(Exception e){e.printStackTrace();}
		
	}
	
	public void update(){
		
		bg.update();
		fg.update();
		//Sg.update();
		Cg.update();
		
		
	}
	
	public void draw(Graphics2D g){
		bg.draw(g);
		Sg.draw(g);
		Cg.draw(g);
		g.drawImage(image,71,37,null);
		
		g.setColor(Color.WHITE);
		g.fillRect(80,141,160,10);
		
		g.setColor(Color.BLACK);
		
		g.setFont(title);
		int length2 = (int) g.getFontMetrics().getStringBounds("About", g).getWidth();
		g.drawString("About",(GamePanel.WIDTH - length2)/2,62);
		
		g.setFont(text);
		for(int i = 0; i < options.length; i++) {
			
			
				g.setColor(Color.BLACK);
			
			int length = (int) g.getFontMetrics().getStringBounds(options[i], g).getWidth();
			g.drawString(options[i], (GamePanel.WIDTH - length)/2, 150 + i * 20);
		}
		
		g.setColor(Color.BLACK);
		g.setFont(text2);
		g.drawString("v 1.0",285,198);
		g.drawString("2013 Nathan F.",10,198);
		
		g.setColor(Color.WHITE);
		g.setFont(text);
		int length1 = (int) g.getFontMetrics().getStringBounds("Developed By:", g).getWidth();
		int length5 = (int) g.getFontMetrics().getStringBounds("Nathan A. Foote", g).getWidth();
		int length3 = (int) g.getFontMetrics().getStringBounds("Music and Sound By:", g).getWidth();
		int length4 = (int) g.getFontMetrics().getStringBounds("Mike S.", g).getWidth();
		g.setColor(new Color(30,255,0));
		g.drawString("Developed By:", (GamePanel.WIDTH-length1)/2, 80);
		g.setColor(Color.WHITE);
		g.drawString("Nathan A. Foote", (GamePanel.WIDTH-length5)/2,90);
		g.setColor(new Color(149,20,218));
		g.drawString("Music and Sound By:",(GamePanel.WIDTH-length3)/2,115);
		g.setColor(Color.WHITE);
		g.drawString("Mike S.",(GamePanel.WIDTH-length4)/2,125);
		
		fg.draw(g);
	}
	
	public void keyPressed(int k){
		if(k == KeyEvent.VK_ENTER){
			JukeBox.play("menuclick");
		}
	}
	
	public void keyReleased(int k){
		if(k == KeyEvent.VK_ENTER){
			
			gsm.setState(GameStateManager.OPTIONSSTATE);
		}
	}


}





















