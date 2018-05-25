package Main;

import javax.swing.JFrame;

public class Game {
	
	public static void main(String[] args){
		
		JFrame window = new JFrame("Cubix");
		window.setContentPane(new GamePanel());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setLocation(450,220);
		window.pack();
		window.setVisible(true);
		
	}

}
