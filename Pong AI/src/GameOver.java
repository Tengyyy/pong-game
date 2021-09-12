import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.JPanel;

public class GameOver extends JPanel{
	
	static int GAME_WIDTH;
	static int GAME_HEIGHT;
	
	static String winnerName;
	
	static int winner;
	

	GameOver(String winnerName, int winner, int GAME_WIDTH, int GAME_HEIGHT){
		
		GameOver.GAME_WIDTH = GAME_WIDTH;
		GameOver.GAME_HEIGHT = GAME_HEIGHT;
		GameOver.winner = winner;
		
		GameOver.winnerName = winnerName;
		
	}
	
	public void draw(Graphics g) {
		
		g.setColor(Color.WHITE);
		
		g.setFont(new Font("Consolas", Font.PLAIN, 100));
		
		FontMetrics metrics = getFontMetrics(g.getFont());
		
		g.drawString("Game Over", (GAME_WIDTH - metrics.stringWidth("Game Over")) / 2, GAME_HEIGHT / 2 - 100);
		
		
		g.setFont(new Font("Consolas", Font.PLAIN, 70));
		
		FontMetrics metrics2 = getFontMetrics(g.getFont());
		
		if(winner == 1) {
			
			if(!GamePanel.singlePlayer) {
				g.setColor(Color.BLUE);
				g.drawString(winnerName + " wins!", (GAME_WIDTH - metrics2.stringWidth(winnerName + " wins!")) / 2, GAME_HEIGHT / 2);
			}
			else {
				g.setColor(Color.BLUE);
				g.drawString("You win!", (GAME_WIDTH - metrics2.stringWidth("You win!")) / 2, GAME_HEIGHT / 2);

			}

			
		}
		else {
		
			if(!GamePanel.singlePlayer) {
				g.setColor(Color.RED);
				g.drawString(winnerName + " wins!", (GAME_WIDTH - metrics2.stringWidth(winnerName + " wins!")) / 2, GAME_HEIGHT / 2);
			}
			else {
				g.setColor(Color.RED);
				g.drawString("The AI wins!", (GAME_WIDTH - metrics2.stringWidth("The AI wins!")) / 2, GAME_HEIGHT / 2);

			
		

			
			}
		
		}
	
	}
}