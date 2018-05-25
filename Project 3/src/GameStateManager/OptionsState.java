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

public class OptionsState extends GameState {
	
	private Background bg;
	private Background fg;
	
	private Background Cg;
	private Background Sg;
	
	private BufferedImage image;
	
	private int currentChoice = 0;
	private String[] options = {
			"Controls",
			"About",
			"Back"
		};
	
	private Font title;
	private Font text;
	private Font text2;
	
	private int dy;
	
	public OptionsState(GameStateManager gsm){
		
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
		
		if(currentChoice == 0){dy = 91;}
		if(currentChoice == 1){dy = 111;}
		if(currentChoice == 2){dy = 131;}
	}
	
	public void draw(Graphics2D g){
		bg.draw(g);
		Sg.draw(g);
		Cg.draw(g);
		g.drawImage(image,71,37,null);
		
		g.setColor(Color.WHITE);
		g.fillRect(80,dy,160,10);
		
		g.setColor(Color.BLACK);
		
		g.setFont(title);
		int length2 = (int) g.getFontMetrics().getStringBounds("Help", g).getWidth();
		g.drawString("Help",(GamePanel.WIDTH - length2)/2,62);
		
		g.setFont(text);
		for(int i = 0; i < options.length; i++) {
			
			if(i == currentChoice) {
				g.setColor(Color.BLACK);
			}
			else {
				g.setColor(Color.WHITE);
			}
			
			int length = (int) g.getFontMetrics().getStringBounds(options[i], g).getWidth();
			g.drawString(options[i], (GamePanel.WIDTH - length)/2, 100 + i * 20);
		}
		
		
		
		
		g.setColor(Color.BLACK);
		g.setFont(text2);
		g.drawString("v 1.0",285,198);
		g.drawString("2013 Nathan F.",10,198);
		
		fg.draw(g);
	}
	
	public void keyPressed(int k){
	
		if(k == KeyEvent.VK_ENTER){
			JukeBox.play("menuclick");
		}
		
		if(k == KeyEvent.VK_UP) {
			currentChoice--;
			if(currentChoice == -1) {
				currentChoice = options.length - 1;
			}
		}
		if(k == KeyEvent.VK_DOWN) {
			currentChoice++;
			if(currentChoice == options.length) {
				currentChoice = 0;
			}
		}
	}
	
	public void keyReleased(int k){
		if(k == KeyEvent.VK_ENTER){
			
			if(currentChoice == 0){
				gsm.setState(GameStateManager.CONTROLSTATE);
			}
			if(currentChoice == 1){
			gsm.setState(GameStateManager.CREDITSSTATE);
			}
			if(currentChoice == 2){
				gsm.setState(GameStateManager.MENUSTATE);
			}
	}

	}
}





















