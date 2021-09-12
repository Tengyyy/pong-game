import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class Score extends Rectangle{

	static int GAME_WIDTH;
	static int GAME_HEIGHT;
	
	int player1 = 0; // holds player1 score
	int player2 = 0; // holds player2 score
	
	Score(int GAME_WIDTH, int GAME_HEIGHT){
		
		Score.GAME_WIDTH = GAME_WIDTH; // Score. not this. cuz static variables
		Score.GAME_HEIGHT = GAME_HEIGHT;
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.WHITE);
		g.setFont(new Font("Consolas", Font.PLAIN, 60));
		
		if(!GamePanel.gameEnd) {
		g.drawLine(GAME_WIDTH / 2, 0, GAME_WIDTH / 2, GAME_HEIGHT); // draw line on the center of the game board
		
		}
		
		g.drawString(String.valueOf(player1/10) + String.valueOf(player1%10), (GAME_WIDTH / 2) - 85, 50); // gives a formatted two digit score display
		g.drawString(String.valueOf(player2/10) + String.valueOf(player2%10), (GAME_WIDTH / 2) + 20, 50);
	
	}
}
