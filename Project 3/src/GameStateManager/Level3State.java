package GameStateManager;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import Audio.JukeBox;
import Entities.Ball;
import Entities.Brick_Blue;
import Entities.Brick_Green;
import Entities.Brick_Purple;
import Entities.Brick_Red;
import Entities.Brick_Yellow;
import Entities.Player;
import Handlers.GameData;
import Main.GamePanel;
import TileMap.Background;

public class Level3State extends GameState {
	
	private Background bg;
	private Background fg;
	
	private Player p;
	private Ball b;
	private int dy;
	
	private int score;
	
	private boolean coolDown;
	private long ballStart;
	private long ballElapsed;
	
	private boolean right;
	private boolean left;
	
	private int min;
	private int sec;
	private long start;
	private long interval;
	private long interval2;
	
	private boolean running;
	private boolean paused;
	private boolean win;
	private boolean lose;
	
	private BufferedImage image;
	private int currentChoice;
	private Font title;
	private Font text;
	private Font text2;
	
	private boolean serve;
	private int lives;
	private Random ran;
	private int die;
	
	private String[] options = {
			"Resume",
			"Restart",
			"Main Menu"
	};
	private String[] options2 = {
			"Continue",
			"Restart",
			"Main Menu"
	};
	private String[] options3 = {
			"Restart",
			"Main Menu"
	};
	
	private int[][] bluePos = {
			{20,16},{40,16},{60,16},{80,16},{100,16},{120,16},{140,16},{160,16},{180,16},{200,16},{220,16},{240,16},{260,16},{280,16}
	};
	private int[][] redPos = {
			{20,64},{40,64},{60,64},{80,64},{100,64},{120,64},{140,64},{160,64},{180,64},{200,64},{220,64},{240,64},{260,64},{280,64}
	};
	private int[][] greenPos = {
			{20,32},{40,32},{60,32},{80,32},{100,32},{120,32},{140,32},{160,32},{180,32},{200,32},{220,32},{240,32},{260,32},{280,32}
	};
	private int[][] yellowPos = {
			{20,80},{40,80},{60,80},{80,80},{100,80},{120,80},{140,80},{160,80},{180,80},{200,80},{220,80},{240,80},{260,80},{280,80}
	};
	private int[][] purplePos = {
			{20,48},{40,48},{60,48},{80,48},{100,48},{120,48},{140,48},{160,48},{180,48},{200,48},{220,48},{240,48},{260,48},{280,48}
	};
	
	private ArrayList<Brick_Blue>Blue;
	private ArrayList<Brick_Red>Red;
	private ArrayList<Brick_Yellow>Yellow;
	private ArrayList<Brick_Purple>Purple;
	private ArrayList<Brick_Green>Green;
	
	public Level3State(GameStateManager gsm){
		
		this.gsm = gsm;
		
		init();
				
		serve = true;
		lives = 3;
		ran = new Random();
		currentChoice = 0;
		
		running = true;
		paused = false;
		win = false;
		lose = false;
		start = System.nanoTime();
		interval = 1;
		interval2 = 60;
		score = 0;
		coolDown = false;
	}
	
	public void init() {
		try{
			
			if(!JukeBox.isPlaying("bgmusic1")){
				JukeBox.loop("bgmusic1",0,JukeBox.getFrames("bgmusic1") - 1);
			}
			JukeBox.stop("menumusic");
			Font scFont = Font.createFont(
					Font.TRUETYPE_FONT,
					getClass().getResourceAsStream("/Fonts/blocked.ttf"));
			
			bg = new Background("/v2/background.png",1);
			fg = new Background("/v2/strips.png",1);
			p = new Player("/v2/paddle.png",137,170);
			b = new Ball(gsm);
			
			text2 = scFont.deriveFont(Font.PLAIN,8f);
			title = scFont.deriveFont(Font.PLAIN,14f);
			text = scFont.deriveFont(Font.PLAIN,10f);
			Blue = new ArrayList<Brick_Blue>();
			Red = new ArrayList<Brick_Red>();
			Green = new ArrayList<Brick_Green>();
			Yellow = new ArrayList<Brick_Yellow>();
			Purple = new ArrayList<Brick_Purple>();
			
			addBricks();
			System.out.println("init");
			image = ImageIO.read(getClass().getResourceAsStream("/v2/window.png"));
			
		}catch(Exception e){e.printStackTrace();}
	}
	
	public void update(){
		
		if(running){
			
			if(coolDown){
				ballElapsed = (System.nanoTime() - ballStart)/1000000;
				if(ballElapsed > 25){
					coolDown = false;
					b.setCollidable(true);
				}
			}
			
		//clock
		long elapsed;
		elapsed = (System.nanoTime()-start)/1000000000;
		
		
		if(elapsed > interval){
			interval += 1;
			sec +=1;
			if(elapsed > interval2){
				interval2 +=60;
				min+=1;
				sec = 0;
			}
		}
		
		
		//update entities
		bg.update();
		fg.update();
		p.update();
		b.update();
		
		//removeBricks();
		checkEnd();
		
		
		//collision
		playerCollision();
		if(b.getCollidable()){
			brickCollision();
			coolDown = true;
			ballStart = System.nanoTime();
		}
		
		
		//serving the ball
		if(serve){
			b.setX(p.getX()+19);
			b.setY(p.getY() - 12);
			
		
		}
		if(b.getVisible() == false){
			if(lives > 0){
				b.setVisible(true);
				b.setX(p.getX()+19);
				b.setY(p.getY() - 12);
				serve = true;
			}
		}
		
		for(int i = 0;i < Red.size(); i ++){
			Red.get(i).update();
		}
		for(int i = 0;i < Green.size(); i ++){
			Green.get(i).update();
		}
		for(int i = 0;i < Blue.size(); i ++){
			Blue.get(i).update();
		}
		for(int i = 0;i < Yellow.size(); i ++){
			Yellow.get(i).update();
		}
		for(int i = 0;i < Purple.size(); i ++){
			Purple.get(i).update();
		}
		
		
			
		}
		if(paused){
			if(currentChoice == 0){dy = 91;}
			if(currentChoice == 1){dy = 111;}
			if(currentChoice == 2){dy = 131;}
		}
		
		if(win){
			if(currentChoice == 0){dy = 91;}
			if(currentChoice == 1){dy = 111;}
			if(currentChoice == 2){dy = 131;}
		}
		if(lose){
			if(currentChoice == 0){dy = 91;}
			if(currentChoice == 1){dy = 111;}
			if(currentChoice == 2){dy = 131;}
		}
		
		
		
		
		
	}
	
	public void draw(Graphics2D g){
		
		
		
		bg.draw(g);
		
		p.draw(g);
		b.draw(g);
		
		for(int i = 0;i < Blue.size();i++){
			Blue.get(i).draw(g);
		}
		for(int i = 0;i < Red.size();i++){
			Red.get(i).draw(g);
		}
		for(int i = 0;i < Green.size();i++){
			Green.get(i).draw(g);
		}
		for(int i = 0;i < Yellow.size();i++){
			Yellow.get(i).draw(g);
		}
		for(int i = 0;i < Purple.size();i++){
			Purple.get(i).draw(g);
		}
		g.setColor(Color.BLACK);
		g.setFont(text2);
		if(sec < 10){
			g.drawString("" + min + " : 0" + sec, 285, 198);
		}else{
			g.drawString("" + min + " : " + sec, 285, 198);
		}
		g.drawString("Lives : " + b.getLives(), 15,198);
		
		
		if(paused){
			
			g.drawImage(image,97,37,null);
			
			g.setColor(Color.WHITE);
			g.fillRect(120,dy,80,10);
			
			g.setFont(title);
			g.setColor(Color.BLACK);
			int length2 = (int) g.getFontMetrics().getStringBounds("Pause", g).getWidth();
			g.drawString("Pause",(GamePanel.WIDTH - length2)/2,60);
			
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
		
		
		
		
		
			
			
			
		}
		
		if(win){
			g.drawImage(image,97,37,null);
			
			g.setColor(Color.WHITE);
			g.fillRect(120,dy,80,10);
			
			g.setFont(title);
			g.setColor(Color.BLACK);
			int length2 = (int) g.getFontMetrics().getStringBounds("Win", g).getWidth();
			g.drawString("Win",(GamePanel.WIDTH - length2)/2,60);
			
			g.setFont(text);
			for(int i = 0; i < options2.length; i++) {
				
				if(i == currentChoice) {
					g.setColor(Color.BLACK);
				}
				else {
					g.setColor(Color.WHITE);
				}
				int length = (int) g.getFontMetrics().getStringBounds(options2[i], g).getWidth();
				g.drawString(options2[i], (GamePanel.WIDTH - length)/2, 100 + i * 20);
			}
			g.setColor(Color.WHITE);
			if(sec > 9)
				g.drawString("Time - " + min + " : " + sec, 125, 85);
			if(sec < 10)
				g.drawString("Time - " + min + " : 0" + sec,125,85);
		}
		if(lose){
			g.drawImage(image,97,37,null);
			
			g.setColor(Color.WHITE);
			g.fillRect(120,dy,80,10);
			
			g.setFont(title);
			g.setColor(Color.BLACK);
			int length2 = (int) g.getFontMetrics().getStringBounds("Fail", g).getWidth();
			g.drawString("Fail",(GamePanel.WIDTH - length2)/2,60);
			
			g.setFont(text);
			for(int i = 0; i < options3.length; i++) {
				
				if(i == currentChoice) {
					g.setColor(Color.BLACK);
				}
				else {
					g.setColor(Color.WHITE);
				}
				int length = (int) g.getFontMetrics().getStringBounds(options3[i], g).getWidth();
				g.drawString(options3[i], (GamePanel.WIDTH - length)/2, 100 + i * 20);
			}
		}
		fg.draw(g);
		
		
	
	}
	
	private void playerCollision(){
		Rectangle r1 = new Rectangle(p.getX(),p.getY(),45,1);
		
		
			Rectangle r2 = b.getBounds();
			if(r1.intersects(r2)){
				JukeBox.play("hit");
				if(right){
					b.setDy(-1);
					b.setDx(1);
				}
				else if(left){
					b.setDy(-1);
					b.setDx(-1);
				}
				else if(!right && !left){
					b.setDy(-1);
				}
			}
		
	}
	
	
	public void keyPressed(int k){
		
		if(running){
		
		if(k == KeyEvent.VK_ESCAPE){
			paused = true;
			running = false;
		}
		if(k == KeyEvent.VK_LEFT){
			p.setDx(-3);
			left = true;
		}
		if(k == KeyEvent.VK_RIGHT){
			p.setRx(3);
			right = true;
		}
		if(k == KeyEvent.VK_SPACE && serve){
			serve = false;
			die = ran.nextInt(2)+1;
			if(die == 1){
				b.setDx(-1);
				b.setDy(-1);
			}
			if(die == 2){
				b.setDx(1);
				b.setDy(-1);
			}
		}
		}
		
		
		
	}
	
	public void keyReleased(int k){
		if(k == KeyEvent.VK_LEFT){
			p.setDx(0);
			left = false;
		}
		if(k == KeyEvent.VK_RIGHT){
			p.setRx(0);
			right = false;
		}
		
		if(paused){
			if(k == KeyEvent.VK_ENTER){
				if(currentChoice == 0){
					running = true;
					paused = false;
				}
				if(currentChoice == 1){
					gsm.setState(GameStateManager.LEVEL3STATE);
				}
				if(currentChoice == 2){
					gsm.setState(GameStateManager.MENUSTATE);
				}
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
		if(win){
			if(k == KeyEvent.VK_ENTER){
				GameData.setTime(min, sec, 2);
				GameData.save();
				if(currentChoice == 0){
					gsm.setState(GameStateManager.LEVEL4STATE);
				}
				if(currentChoice == 1){
					gsm.setState(GameStateManager.LEVEL3STATE);
				}
				if(currentChoice == 2){
					gsm.setState(GameStateManager.MENUSTATE);
				}
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
		if(lose){
			if(k == KeyEvent.VK_ENTER){
				
				if(currentChoice == 0){
					gsm.setState(GameStateManager.LEVEL3STATE);
				}
				if(currentChoice == 1){
					gsm.setState(GameStateManager.MENUSTATE);
				}
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
	}
	
	
	private void addBricks(){
		
		for(int i = 0;i < bluePos.length;i++){
			Blue.add(new Brick_Blue(bluePos[i][0], bluePos[i][1],"/v2/blue.png","/v2/blue2.png"));
		}
		for(int i = 0;i < redPos.length;i++){
			Red.add(new Brick_Red(redPos[i][0], redPos[i][1],"/v2/red.png","/v2/red2.png"));
		}
		for(int i = 0;i < greenPos.length;i++){
			Green.add(new Brick_Green(greenPos[i][0], greenPos[i][1],"/v2/green.png","/v2/green2.png"));
		}
		for(int i = 0;i < yellowPos.length;i++){
			Yellow.add(new Brick_Yellow(yellowPos[i][0], yellowPos[i][1],"/v2/yellow.png","/v2/yellow2.png"));
		}
		for(int i = 0;i < purplePos.length;i++){
			Purple.add(new Brick_Purple(purplePos[i][0], purplePos[i][1],"/v2/purple.png","/v2/purple2.png"));
		}
	}
	
private void brickCollision(){
		
		Rectangle r1 = b.getBounds();
		
		for(int i = 0;i < Blue.size();i++){
			Rectangle r2 = Blue.get(i).getTop();
			Rectangle r3 = Blue.get(i).getBottom();
			Rectangle r4 = Blue.get(i).getLeft();
			Rectangle r5 = Blue.get(i).getRight();
			
			
			if(r1.intersects(r2)){
				JukeBox.play("bouncer");
				if(b.getCollidable()){
				Blue.get(i).hit();
				b.collision();
				score = score+20;
				b.multDy(-1);
				if(Blue.get(i).getVisible() == false){
					Blue.remove(i);
					i++;
				}
				}
			}
			else if(r1.intersects(r3)){
				JukeBox.play("bouncer");
				if(b.getCollidable()){
				Blue.get(i).hit();
				b.collision();
				score = score+20;
				b.multDy(-1);
				if(Blue.get(i).getVisible() == false){
					Blue.remove(i);
					i++;
				}
				}
			}
			else if(r1.intersects(r4)){
				JukeBox.play("bouncer");
				if(b.getCollidable()){
				Blue.get(i).hit();
				b.collision();
				score = score+20;
				b.multDx(-1);
				if(Blue.get(i).getVisible() == false){
					Blue.remove(i);
					i++;
				}
				}
			}
			else if(r1.intersects(r5)){
				JukeBox.play("bouncer");
				if(b.getCollidable()){
				Blue.get(i).hit();
				b.collision();
				score = score+20;
				b.multDx(-1);
				if(Blue.get(i).getVisible() == false){
					Blue.remove(i);
					i++;
				}
				}
			}
			
		}
		for(int i = 0;i < Red.size();i++){
			Rectangle r2 = Red.get(i).getTop();
			Rectangle r3 = Red.get(i).getBottom();
			Rectangle r4 = Red.get(i).getLeft();
			Rectangle r5 = Red.get(i).getRight();
			
			if(r1.intersects(r2)){
				JukeBox.play("bouncer");
				if(b.getCollidable()){
				Red.get(i).hit();
				b.collision();
				score = score+20;
				b.multDy(-1);
				if(Red.get(i).getVisible() == false){
					Red.remove(i);
					i++;
				}
				}
			}
			else if(r1.intersects(r3)){
				JukeBox.play("bouncer");
				if(b.getCollidable()){
				Red.get(i).hit();
				b.collision();
				score = score+20;
				b.multDy(-1);
				if(Red.get(i).getVisible() == false){
					Red.remove(i);
					i++;
				}
				}
			}
			else if(r1.intersects(r4)){
				JukeBox.play("bouncer");
				if(b.getCollidable()){
				Red.get(i).hit();
				b.collision();
				score = score+20;
				b.multDx(-1);
				if(Red.get(i).getVisible() == false){
					Red.remove(i);
					i++;
				}
				}
			}
			else if(r1.intersects(r5)){
				JukeBox.play("bouncer");
				if(b.getCollidable()){
				Red.get(i).hit();
				b.collision();
				score = score+20;
				b.multDx(-1);
				if(Red.get(i).getVisible() == false){
					Red.remove(i);
					i++;
				}
				}
			}
			
		}
		for(int i = 0;i < Green.size();i++){
			Rectangle r2 = Green.get(i).getTop();
			Rectangle r3 = Green.get(i).getBottom();
			Rectangle r4 = Green.get(i).getLeft();
			Rectangle r5 = Green.get(i).getRight();
			
			if(r1.intersects(r2)){
				JukeBox.play("bouncer");
				if(b.getCollidable()){
				Green.get(i).hit();
				b.collision();
				score = score+20;
				b.multDy(-1);
				if(Green.get(i).getVisible() == false){
					Green.remove(i);
					i++;
				}
				}
			}
			else if(r1.intersects(r3)){
				JukeBox.play("bouncer");
				if(b.getCollidable()){
				Green.get(i).hit();
				b.collision();
				score = score+20;
				b.multDy(-1);
				if(Green.get(i).getVisible() == false){
					Green.remove(i);
					i++;
				}
				}
			}
			else if(r1.intersects(r4)){
				JukeBox.play("bouncer");
				if(b.getCollidable()){
				Green.get(i).hit();
				b.collision();
				score = score+20;
				b.multDx(-1);
				if(Green.get(i).getVisible() == false){
					Green.remove(i);
					i++;
				}
				}
			}
			else if(r1.intersects(r5)){
				JukeBox.play("bouncer");
				if(b.getCollidable()){
				Green.get(i).hit();
				b.collision();
				score = score+20;
				b.multDx(-1);
				if(Green.get(i).getVisible() == false){
					Green.remove(i);
					i++;
				}
				}
			}
			
		}
		for(int i = 0;i < Yellow.size();i++){
			Rectangle r2 = Yellow.get(i).getTop();
			Rectangle r3 = Yellow.get(i).getBottom();
			Rectangle r4 = Yellow.get(i).getLeft();
			Rectangle r5 = Yellow.get(i).getRight();
			
			if(r1.intersects(r2)){
				JukeBox.play("bouncer");
				if(b.getCollidable()){
				Yellow.get(i).hit();
				b.collision();
				score = score+20;
				b.multDy(-1);
				if(Yellow.get(i).getVisible() == false){
					Yellow.remove(i);
					i++;
				}
				}
			}
			else if(r1.intersects(r3)){
				JukeBox.play("bouncer");
				if(b.getCollidable()){
				Yellow.get(i).hit();
				b.collision();
				score = score+20;
				b.multDy(-1);
				if(Yellow.get(i).getVisible() == false){
					Yellow.remove(i);
					i++;
				}
				}
			}
			else if(r1.intersects(r4)){
				JukeBox.play("bouncer");
				if(b.getCollidable()){
				Yellow.get(i).hit();
				b.collision();
				score = score+20;
				b.multDx(-1);
				if(Yellow.get(i).getVisible() == false){
					Yellow.remove(i);
					i++;
				}
				}
			}
			else if(r1.intersects(r5)){
				JukeBox.play("bouncer");
				if(b.getCollidable()){
				Yellow.get(i).hit();
				b.collision();
				score = score+20;
				b.multDx(-1);
				if(Yellow.get(i).getVisible() == false){
					Yellow.remove(i);
					i++;
				}
				}
			}
			
		}
		for(int i = 0;i < Purple.size();i++){
			Rectangle r2 = Purple.get(i).getTop();
			Rectangle r3 = Purple.get(i).getBottom();
			Rectangle r4 = Purple.get(i).getLeft();
			Rectangle r5 = Purple.get(i).getRight();
		
			if(r1.intersects(r2)){
				JukeBox.play("bouncer");
				if(b.getCollidable()){
				Purple.get(i).hit();
				b.collision();
				score = score+20;
				b.multDy(-1);
				if(Purple.get(i).getVisible() == false){
					Purple.remove(i);
					i++;
				}
				}
			}
			else if(r1.intersects(r3)){
				JukeBox.play("bouncer");
				if(b.getCollidable()){
				Purple.get(i).hit();
				b.collision();
				score = score+20;
				b.multDy(-1);
				if(Purple.get(i).getVisible() == false){
					Purple.remove(i);
					i++;
				}
				}
			}
			else if(r1.intersects(r4)){
				JukeBox.play("bouncer");
				if(b.getCollidable()){
				Purple.get(i).hit();
				b.collision();
				score = score+20;
				b.multDx(-1);
				if(Purple.get(i).getVisible() == false){
					Purple.remove(i);
					i++;
				}
				}
			}
			else if(r1.intersects(r5)){
				JukeBox.play("bouncer");
				if(b.getCollidable()){
				Purple.get(i).hit();
				b.collision();
				score = score+20;
				b.multDx(-1);
				if(Purple.get(i).getVisible() == false){
					Purple.remove(i);
					i++;
				}
				}
			}
		}
		
		
	}
	
	/*private void removeBricks(){
		
		for(int i = 0;i < Blue.size();i++){
			if(Blue.get(i).getVisible() == false){
				Blue.remove(i);
			}
		}
		for(int i = 0;i < Red.size();i++){
			if(Red.get(i).getVisible() == false){
				Red.remove(i);
			}
		}
		for(int i = 0;i < Green.size();i++){
			if(Green.get(i).getVisible() == false){
				Green.remove(i);
			}
		}
		for(int i = 0;i < Yellow.size();i++){
			if(Yellow.get(i).getVisible() == false){
				Yellow.remove(i);
			}
		}
		for(int i = 0;i < Purple.size();i++){
			if(Purple.get(i).getVisible() == false){
				Purple.remove(i);
			}
		}
		
	}
	*/
	
	private void checkEnd(){
		if(Blue.size() == 0 && Green.size() == 0 && Yellow.size() == 0 && Purple.size() == 0 && Red.size() == 0){
			win = true;
			running = false;
			if(min == 0){
				int points = 60 - sec;
				score += points*4;
			}
		}
		if(b.getLives() == 0){
			lose = true;
			running = false;
		}
	}
	
	
	

}





















