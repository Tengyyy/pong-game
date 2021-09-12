import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class Paddle extends Rectangle{

	int id; // id 1 == player 1, id 2 == player2 || AI 
	
	int yVelocity;
	int speed = 5;
	
	int aiSpeed = 1;
	
	boolean AI;
	
	Paddle(int x, int y, int PADDLE_WIDTH, int PADDLE_HEIGHT, int id, boolean AI){
		
		super(x, y, PADDLE_WIDTH, PADDLE_HEIGHT); // creates a rectangle object with arguments x y width and height which are later passed in to the draw method
		
		this.id = id;
		
		this.AI = AI;
	}
	
	public void keyPressed(KeyEvent e) {
		if(!AI) {
			switch(id) {
			case 1:
				if(e.getKeyCode() == KeyEvent.VK_W) { // sets the paddles direction to go up on the y axis when pressing W
					setYDirection(-speed);
					move();
				}
				if(e.getKeyCode() == KeyEvent.VK_S) {
					setYDirection(speed);
					move();
				}
				break;
			case 2:
				if(e.getKeyCode() == KeyEvent.VK_UP) { // sets the paddles direction to go up on the y axis when pressing up arrow
					setYDirection(-speed);
					move();
				}
				if(e.getKeyCode() == KeyEvent.VK_DOWN) {
					setYDirection(speed);
					move();
				}
				break;
			}
		}
	}
	
	public void keyReleased(KeyEvent e) {
		
		if(!AI) {
			switch(id) {
			case 1:
				if(e.getKeyCode() == KeyEvent.VK_W) { // sets the paddles direction to go up on the y axis when pressing W
					setYDirection(0);
					move();
				}
				if(e.getKeyCode() == KeyEvent.VK_S) {
					setYDirection(0);
					move();
				}
				break;
			case 2:
				if(e.getKeyCode() == KeyEvent.VK_UP) { // sets the paddles direction to go up on the y axis when pressing W
					setYDirection(0);
					move();
				}
				if(e.getKeyCode() == KeyEvent.VK_DOWN) {
					setYDirection(0);
					move();
				}
				break;
			}
		}
	}
	
	public void setYDirection(int yDirection) {
		yVelocity = yDirection;
	}
	
	public void move() {
		if(!AI) {
			y = y + yVelocity;
		}
		else {
		
			if(Ball.xVelocity < 0) { // if ball is moving away from the AI paddle
				
				if(GamePanel.difficulty == 1) {
					if(y + GamePanel.PADDLE_HEIGHT/2 > GamePanel.GAME_HEIGHT/2) {
						y-= 1;
					}
					else if(y + GamePanel.PADDLE_HEIGHT/2 < GamePanel.GAME_HEIGHT/2) {
						y+= 1;
					}
				}
				else {
					if(y + GamePanel.PADDLE_HEIGHT/2 > GamePanel.GAME_HEIGHT/2) {
						y-= 2;
					}
					else if(y + GamePanel.PADDLE_HEIGHT/2 < GamePanel.GAME_HEIGHT/2) {
						y+= 2;
					}
				}
				
				
			}
			
			else {
				
				if(GamePanel.difficulty == 3) {
					if(Ball.ballY + (GamePanel.BALL_DIAMETER/2) > y + GamePanel.PADDLE_HEIGHT/2) {
						
						if(GamePanel.aiSpeedRandomizer > 7 && Math.abs(Ball.yVelocity) > 7) {
							y+=(Math.abs(Ball.yVelocity) - 2);
						}
						
						else if(GamePanel.aiSpeedRandomizer > 7 && Math.abs(Ball.yVelocity) > 5) { 
							
							y+=(Math.abs(Ball.yVelocity) - 1);
						}
						else {
							y+=Math.abs(Ball.yVelocity);
						}
					}
					else if(Ball.ballY + (GamePanel.BALL_DIAMETER/2) < y + GamePanel.PADDLE_HEIGHT/2) {
						
						if(GamePanel.aiSpeedRandomizer > 7 && Math.abs(Ball.yVelocity) > 7) {
							y-=(Math.abs(Ball.yVelocity) - 2);
						}
						
						else if(GamePanel.aiSpeedRandomizer > 7 && Math.abs(Ball.yVelocity) > 5) {
								y-=(Math.abs(Ball.yVelocity) - 1);
						}
						else {
							y-=Math.abs(Ball.yVelocity);
						}
					}
				}
				
				else if(GamePanel.difficulty == 2) {
					if(Ball.ballY + (GamePanel.BALL_DIAMETER/2) > y + GamePanel.PADDLE_HEIGHT/2) {
					
					if(GamePanel.aiSpeedRandomizer > 5 && Math.abs(Ball.yVelocity) > 5) {
						y+=(Math.abs(Ball.yVelocity) - 2);
					}
					
					else if(GamePanel.aiSpeedRandomizer > 5 && Math.abs(Ball.yVelocity) > 3) { 
						
						y+=(Math.abs(Ball.yVelocity) - 1);
					}
					else {
						y+=Math.abs(Ball.yVelocity);
					}
				}
				else if(Ball.ballY + (GamePanel.BALL_DIAMETER/2) < y + GamePanel.PADDLE_HEIGHT/2) {
					
					if(GamePanel.aiSpeedRandomizer > 5 && Math.abs(Ball.yVelocity) > 5) {
						y-=(Math.abs(Ball.yVelocity) - 2);
					}
					
					else if(GamePanel.aiSpeedRandomizer > 5 && Math.abs(Ball.yVelocity) > 3) {
							y-=(Math.abs(Ball.yVelocity) - 1);
					}
					else {
						y-=Math.abs(Ball.yVelocity);
					}
				}
			}
				
				else {
					if(Ball.ballY + (GamePanel.BALL_DIAMETER/2) > y + GamePanel.PADDLE_HEIGHT/2) {
						
						if(GamePanel.aiSpeedRandomizer > 4 && Math.abs(Ball.yVelocity) > 3) {
							y+=(Math.abs(Ball.yVelocity) - 2);
						}
						
						else if(GamePanel.aiSpeedRandomizer > 4 && Math.abs(Ball.yVelocity) > 2) { 
							
							y+=(Math.abs(Ball.yVelocity) - 1);
						}
						else {
							y+=Math.abs(Ball.yVelocity);
						}
					}
					else if(Ball.ballY + (GamePanel.BALL_DIAMETER/2) < y + GamePanel.PADDLE_HEIGHT/2) {
						
						if(GamePanel.aiSpeedRandomizer > 4 && Math.abs(Ball.yVelocity) > 3) {
							y-=(Math.abs(Ball.yVelocity) - 2);
						}
						
						else if(GamePanel.aiSpeedRandomizer > 4 && Math.abs(Ball.yVelocity) > 2) {
								y-=(Math.abs(Ball.yVelocity) - 1);
						}
						else {
							y-=Math.abs(Ball.yVelocity);
						}
					}
				}
			}
		}
	}
	
	public void draw(Graphics g) {
		if(id==1) {
			g.setColor(Color.BLUE);
			g.fillRect(x, y, width, height);
		}
		else {
			g.setColor(Color.RED);
			g.fillRect(x, y, width, height);
			
		}
	}
}
