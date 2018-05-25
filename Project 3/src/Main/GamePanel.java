package Main;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import javax.swing.JPanel;

import Audio.JukeBox;

import GameStateManager.GameStateManager;


@SuppressWarnings("serial")
public class GamePanel extends JPanel 
	implements Runnable, KeyListener{
	
	// dimensions
	public static final int WIDTH = 320;
	public static final int HEIGHT = 200;
	public static final int SCALE = 3;
	
	// game thread
	private Thread thread;
	private boolean running;
	private int FPS = 120;
	private long targetTime = 1000 / FPS;
	private long totalTime;
	private double averageFPS;
	
	// image
	private BufferedImage image;
	private Graphics2D g;
	
	// game state manager
	private GameStateManager gsm;
	
	public GamePanel() {
		super();
		setPreferredSize(
			new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		setFocusable(true);
		requestFocus();
		
		

		
	}
	
	public void addNotify() {
		super.addNotify();
		if(thread == null) {
			thread = new Thread(this);
			addKeyListener(this);
			thread.start();
		}
	}
	
	private void init() {
		
		image = new BufferedImage(
					WIDTH, HEIGHT,
					BufferedImage.TYPE_INT_RGB
				);
		g = (Graphics2D) image.getGraphics();
		
		running = true;
		
		gsm = new GameStateManager();
		Cursor c = null;
		setCursor(c);
		
		//JukeBox.loop("menumusic");
		
	}
	
	public void run() {
		
		init();
		
		long start;
		long elapsed;
		long wait;
		int frameCount = 0;
		int maxFrameCount = 60;
		
		// game loop
		while(running) {
			
			start = System.nanoTime();
			
			update();
			draw();
			drawToScreen();
			
			elapsed = System.nanoTime() - start;
			
			wait = targetTime - elapsed / 1000000;
			if(wait < 0) wait = 5;
			
			try {
				Thread.sleep(wait);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			totalTime += System.nanoTime() - start;
			frameCount++;
			if(frameCount == maxFrameCount){
				averageFPS = 1000.0 / ((totalTime/frameCount) / 1000000);
				frameCount = 0;
				totalTime = 0;
			}
			
		}
		
	}
	
	private void update() {
		gsm.update();
	}
	private void draw() {
		gsm.draw(g);
	
	}
	private void drawToScreen() {
		Graphics g2 = getGraphics();
		g2.drawImage(image, 0, 0,
				WIDTH * SCALE, HEIGHT * SCALE,
				null);
		g2.setColor(Color.WHITE);
		//g2.drawString("FPS: " + averageFPS, 50, 50);
		
		
	}
	
	public void keyTyped(KeyEvent key) {}
	public void keyPressed(KeyEvent key) {
		gsm.keyPressed(key.getKeyCode());
	}
	public void keyReleased(KeyEvent key) {
		gsm.keyReleased(key.getKeyCode());
	}

}
















