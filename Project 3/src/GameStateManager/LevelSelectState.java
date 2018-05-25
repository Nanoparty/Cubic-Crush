package GameStateManager;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.imageio.ImageIO;

import Audio.JukeBox;
import Handlers.GameData;
import Main.GamePanel;
import TileMap.Background;

public class LevelSelectState extends GameState {
	
	private Background bg;
	private Background fg;
	private Background Sg;
	private Background Cg;
	
	private BufferedImage image;
	
	private boolean top;
	private boolean middle;
	private boolean bottom;
	
	private int currentChoice = 0;
	private String[] options = {
		"Level 1",
			"Level 2",
			"Level 3",
			"Level 4",
			"Level 5",
			"Level 6",
			"Level 7",
			"Level 8",
			"Level 9",
			"Level 10"
		};
	
	
	private Font title;
	private Font text;
	private Font text2;
	
	private int dy;
	private int boxy;
	
	public LevelSelectState(GameStateManager gsm){
		
		this.gsm = gsm;
		
		init();
		
				
	}
	
	public void init() {
		
		try{
			
			Font scFont = Font.createFont(
					Font.TRUETYPE_FONT,
					getClass().getResourceAsStream("/Fonts/blocked.ttf"));
			
			GameData.load();
			top = true;
			boxy = 91;
			middle = false;
			bottom = false;
			bg = new Background("/v2/background.png",1);
			fg = new Background("/v2/strips.png",1);
			Sg = new Background("/v2/scroll.png",1);
			Cg = new Background("/v2/backgroundCover.png",1);
			fg.setVector(0, 0);
			Sg.setVector(0,.5);
			title = scFont.deriveFont(Font.PLAIN,20f);
			text = scFont.deriveFont(Font.PLAIN,10f);
			text2 = scFont.deriveFont(Font.PLAIN,8f);;
			image = ImageIO.read(getClass().getResourceAsStream("/v2/WindowLarge.png"));
		}catch(Exception e){e.printStackTrace();}
		
	}
	
	public void update(){
		bg.update();
		fg.update();
		//Sg.update();
		Cg.update();
		
		/*
		if(currentChoice == 0){dy = 0;}
		if(currentChoice == 1){dy = -20;}
		if(currentChoice == 2){dy = -40;}
		if(currentChoice == 3){dy = -60;}
		if(currentChoice == 4){dy = -80;}
		if(currentChoice == 5){dy = -100;}
		if(currentChoice == 6){dy = -120;}
		if(currentChoice == 7){dy = -140;}
		if(currentChoice == 8){dy = -160;}
		if(currentChoice == 9){dy = -180;}
		*/
		
		if(top){
			boxy = 91;
		}
		if(middle){
			boxy = 111;
		}
		if(bottom){
			boxy = 131;
		}
	}
	
	public void draw(Graphics2D g){
		bg.draw(g);
		Sg.draw(g);
		Cg.draw(g);
		g.drawImage(image,71,37,null);
		
		g.setColor(Color.WHITE);
		g.fillRect(80,boxy,160,10);
			
		g.setColor(Color.BLACK);
		
		g.setFont(title);
		int length2 = (int) g.getFontMetrics().getStringBounds("Levels", g).getWidth();
		g.drawString("Levels",(GamePanel.WIDTH - length2)/2,62);
		
		g.setFont(text);
		for(int i = 0; i < options.length; i++) {
			
			if(i == currentChoice) {
				g.setColor(Color.BLACK);
			}
			else {
				g.setColor(Color.WHITE);
			}
			int length = (int) g.getFontMetrics().getStringBounds(options[i], g).getWidth();
			if(top){
			if(currentChoice == i || currentChoice+1 == i || currentChoice+2 == i){
			g.drawString(options[i], (GamePanel.WIDTH - length)/3, (100 + i * 20) + dy);
			}
			}
			if(middle){
				if(currentChoice == i || currentChoice+1 == i || currentChoice-1 == i){
					g.drawString(options[i], (GamePanel.WIDTH - length)/3, (100 + i * 20) + dy);
					}
			}
			if(bottom){
				if(currentChoice == i || currentChoice-1 == i || currentChoice-2 == i){
					g.drawString(options[i], (GamePanel.WIDTH - length)/3, (100 + i * 20) + dy);
					}
			}
		}
		
		for(int i = 0; i < 10; i++) {
			
			if(i == currentChoice) {
				g.setColor(Color.BLACK);
			}
			else {
				g.setColor(Color.WHITE);
			}
			int length = (int) g.getFontMetrics().getStringBounds(GameData.getMin(i) + " : " + GameData.getSec(i), g).getWidth();
			if(top){
				if(currentChoice == i || currentChoice+1 == i || currentChoice+2 == i){
					if(GameData.getSec(i) > 9){
						g.drawString(GameData.getMin(i) + " : " + GameData.getSec(i),200, (100 + i * 20) + dy);
					}else{
						g.drawString(GameData.getMin(i) + " : 0" + GameData.getSec(i),200, (100 + i * 20) + dy);
					}
					}
			}
			if(middle){
				if(currentChoice == i || currentChoice+1 == i || currentChoice-1 == i){
					if(GameData.getSec(i) > 9){
						g.drawString(GameData.getMin(i) + " : " + GameData.getSec(i),200, (100 + i * 20) + dy);
					}else{
						g.drawString(GameData.getMin(i) + " : 0" + GameData.getSec(i),200, (100 + i * 20) + dy);
					}
					}
			}
			if(bottom){
				if(currentChoice == i || currentChoice-1 == i || currentChoice-2 == i){
					if(GameData.getSec(i) > 9){
						g.drawString(GameData.getMin(i) + " : " + GameData.getSec(i),200, (100 + i * 20) + dy);
					}else{
						g.drawString(GameData.getMin(i) + " : 0" + GameData.getSec(i),200, (100 + i * 20) + dy);
					}
					}
			}
		}
		
		g.setColor(Color.WHITE);
		g.drawString("Levels",90,85);
		g.drawString("Best Time",175,85);

		
		g.setColor(Color.BLACK);
		g.setFont(text2);
		g.drawString("v 1.0",285,198);
		
		fg.draw(g);
	}
	
	public void keyPressed(int k){
		
		if(k == KeyEvent.VK_ESCAPE){
			gsm.setState(GameStateManager.MENUSTATE);
			JukeBox.play("menuclick");
		}
		
		if(k == KeyEvent.VK_ENTER){
			JukeBox.play("menuclick");
			
			if(currentChoice == 0){
				gsm.setState(GameStateManager.LEVEL1STATE);
			}
			if(currentChoice == 1){
				gsm.setState(GameStateManager.LEVEL2STATE);
			}
			if(currentChoice == 2){
				gsm.setState(GameStateManager.LEVEL3STATE);
			}
			if(currentChoice == 3){
				gsm.setState(GameStateManager.LEVEL4STATE);
			}
			if(currentChoice == 4){
				gsm.setState(GameStateManager.LEVEL5STATE);
			}
			if(currentChoice == 5){
				gsm.setState(GameStateManager.LEVEL6STATE);
			}
			if(currentChoice == 6){
				gsm.setState(GameStateManager.LEVEL7STATE);
			}
			if(currentChoice == 7){
				gsm.setState(GameStateManager.LEVEL8STATE);
			}
			if(currentChoice == 8){
				gsm.setState(GameStateManager.LEVEL9STATE);
			}
			if(currentChoice == 9){
				gsm.setState(GameStateManager.LEVEL10STATE);
			}
			
		}
		
	}
	
	public void keyReleased(int k){
		
		if(k== KeyEvent.VK_ENTER){
			
		}
		if(k == KeyEvent.VK_UP) {
			if(top){
				if(currentChoice != 0){
				dy = dy + 20;
				}
			}
			if(middle){
				middle = false;
				top = true;
			}
			if(bottom){
				bottom = false;
				middle = true;
			}
			if(currentChoice!= 0){
				currentChoice--;
			}
			
		}
		if(k == KeyEvent.VK_DOWN) {
			if(bottom){
				if(currentChoice != 9){
				dy = dy - 20;
				}
			}
			if(middle){
				middle = false;
				bottom = true;
			}
			if(top){
				top = false;
				middle = true;
			}
			if(currentChoice != 9){
			currentChoice++;
			}
			
		}
	}


}





















