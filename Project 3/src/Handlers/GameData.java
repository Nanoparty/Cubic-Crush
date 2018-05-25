/**
 * The class contains all of the scores
 * for each level. Scores are saved to file
 * whenever the LevelSelectState is loaded.
 * This class also keeps track of the current
 * score while playing a level.
 */

package Handlers;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class GameData {
	
	// temporary saves
	private static int cumulativeScore;
	
	// things I need to save
	private static int[] levelScores;
	private static int[] levelMinutes;
	private static int[] levelSeconds;
	private static int[] levelTime;
	
	public static void resetScore() {
		cumulativeScore = 0;
	}
	
	public static void setTime(int min,int sec,int i){
		if(levelMinutes[i] == 0 && levelSeconds[i] == 0){
			levelSeconds[i] = sec;
			levelMinutes[i] = min;
		}else if(min <= levelMinutes[i]){
			if(sec < levelSeconds[i]){
				levelSeconds[i] = sec;
				levelMinutes[i] = min;
				System.out.println("overwrite");
			}
		}
				
			
		
	}
	
	public static void addScore(int i) {
		cumulativeScore += i;
		if(cumulativeScore < 0) cumulativeScore = 0;
	}
	
	public static void setFinalScore(int i) {
		if(levelScores[i] < cumulativeScore) levelScores[i] = cumulativeScore;
	}
	
	public static int getMin(int i){
		return levelMinutes[i];
	}
	public static int getSec(int i){
		return levelSeconds[i];
	}
	
	public static int getScore(int i) {
		return levelScores[i];
	}
	
	public static int getCurrentScore() {
		return cumulativeScore;
	}
	
	public static void save() {
		try {
			PrintWriter out = new PrintWriter("save.txt");
			for(int i = 0; i < levelMinutes.length; i++) {
				out.println(levelMinutes[i]);
			}
			for(int i = 0; i < levelSeconds.length; i++){
				out.println(levelSeconds[i]);
			}
			out.close();
			System.out.println("saved");
		}
		catch(Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	public static void load() {
		try {
			File f = new File("save.txt");
			if(!f.exists()) {
				defaultInit();
				return;
			}
			Scanner in = new Scanner(f);
			levelMinutes = new int[10];
			levelSeconds = new int[10];
			for(int i = 0; i < levelMinutes.length; i++) {
				if(in.hasNext()){
					levelMinutes[i] = in.nextInt();
				}else{
					levelMinutes[i] = 0;
				}
			}
			for(int i = 0; i < levelSeconds.length;i++){
				if(in.hasNext()){
					levelSeconds[i] = in.nextInt();
				}else{
					levelSeconds[i] = 0;
				}
			}
			
			in.close();
			
		}
		catch(Exception e) {
			e.printStackTrace();
			defaultInit();
		}
	}
	
	
	
	private static void defaultInit() {
		levelMinutes = new int[10];
		levelSeconds = new int[10];
		save();
	}
	
}