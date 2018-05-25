package GameStateManager;

import java.util.ArrayList;

import Audio.AudioPlayer;
import Audio.JukeBox;

public class GameStateManager {
	
	private boolean Music = true;
	private boolean SFX = true;
	private boolean FS = true;
	
	private AudioPlayer select_sound;
	private AudioPlayer bounce_sound;
	
	private GameState[] gameStates;
	private int currentState;
	
	public static final int NUMGAMESTATES = 17;
	public static final int MENUSTATE = 0;
	public static final int LEVELSELECTSTATE = 1;
	public static final int LEVEL1STATE = 2;
	public static final int OPTIONSSTATE = 3;
	public static final int CREDITSSTATE = 4;
	public static final int LEVEL2STATE = 5;
	public static final int LEVEL3STATE = 6;
	public static final int LEVEL4STATE = 7;
	public static final int LEVEL5STATE = 8;
	public static final int LEVEL6STATE = 9;
	public static final int LEVEL7STATE = 10;
	public static final int LEVEL8STATE = 11;
	public static final int LEVEL9STATE = 12;
	public static final int LEVEL10STATE = 13;
	public static final int BONUSSTATE = 14;
	public static final int CREDITSTATE = 15;
	public static final int CONTROLSTATE = 16;
	
	
	public GameStateManager() {
		
		JukeBox.init();
		JukeBox.load("/SFX/menuclick.wav", "menuclick");
		JukeBox.load("/SFX/hit.wav", "hit");
		JukeBox.load("/SFX/bouncer.wav", "bouncer");
		JukeBox.load("/SFX/finish.wav", "finish");
		JukeBox.load("/SFX/hole.wav", "hole");
		JukeBox.load("/SFX/menumusic.mp3", "menumusic");
		JukeBox.load("/SFX/bgmusic1.wav", "bgmusic1");
		
		gameStates = new GameState[NUMGAMESTATES];
		
		currentState = MENUSTATE;
		loadState(currentState);
		
		select_sound = new AudioPlayer("/SFX/switch.wav");
		bounce_sound = new AudioPlayer("/SFX/Bounce.wav");
		
	}
	
	private void loadState(int state) {
		if(state == MENUSTATE)
			gameStates[state] = new MenuState(this);
		if(state == LEVEL1STATE)
			gameStates[state] = new Level1State(this);
		if(state == LEVELSELECTSTATE)
			gameStates[state] = new LevelSelectState(this);
		if(state == OPTIONSSTATE)
			gameStates[state] = new OptionsState(this);
		if(state == CREDITSSTATE)
			gameStates[state] = new CreditState(this);
		if(state == LEVEL2STATE)
			gameStates[state] = new Level2State(this);
		if(state == LEVEL3STATE)
			gameStates[state] = new Level3State(this);
		if(state == LEVEL4STATE)
			gameStates[state] = new Level4State(this);
		if(state == LEVEL5STATE)
			gameStates[state] = new Level5State(this);
		if(state == LEVEL6STATE)
			gameStates[state] = new Level6State(this);
		if(state == LEVEL7STATE)
			gameStates[state] = new Level7State(this);
		if(state == LEVEL8STATE)
			gameStates[state] = new Level8State(this);
		if(state == LEVEL9STATE)
			gameStates[state] = new Level9State(this);
		if(state == LEVEL10STATE)
			gameStates[state] = new Level10State(this);
		if(state == BONUSSTATE)
			gameStates[state] = new BonusState(this);
		if(state == CREDITSTATE)
			gameStates[state] = new CreditState(this);
		if(state == CONTROLSTATE)
			gameStates[state] = new ControlsState(this);
	}
	
	private void unloadState(int state) {
		gameStates[state] = null;
	}
	
	public void setState(int state) {
		unloadState(currentState);
		currentState = state;
		loadState(currentState);
		//gameStates[currentState].init();
	}
	
	public void update() {
		try {
			gameStates[currentState].update();
		} catch(Exception e) {}
	}
	
	public void draw(java.awt.Graphics2D g) {
		try {
			gameStates[currentState].draw(g);
		} catch(Exception e) {}
	}
	
	public void keyPressed(int k) {
		gameStates[currentState].keyPressed(k);
	}
	
	public void keyReleased(int k) {
		gameStates[currentState].keyReleased(k);
	}
	
	
	public void setMusic(boolean b){
		Music = b;
	}
	
	public boolean getMusic(){
		return Music;
	}
	
	public void setSFX(boolean b){
		SFX = b;
	}
	
	public boolean getSFX(){
		return SFX;
	}
	
	public void setFS(boolean b){
		FS = b;
	}
	
	public boolean getFS(){
		return FS;
	}
	public void playSelect(){
		select_sound.play();
	}
	public void playBounce(){
		bounce_sound.play();
	}
}









