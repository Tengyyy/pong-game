import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class Ball extends Rectangle{

	Random random;
	public static int xVelocity;
	public static int yVelocity;
	int initialSpeed = 1;
	
	public static int ballX;
	public static int ballY;
	
	Ball(int x, int y, int width, int height){
		
		super(x, y, width, height); // calls super class (Rectangle) and creates a rectangle object
				
		Ball.ballX = x;
		Ball.ballY = y;
		
		random = new Random();
		
		int randomXDirection = random.nextInt(2); // if random is 0 the ball goes left, if random is 1 the ball goes right
		
		if(randomXDirection == 0) { // if the ball is supposed to go left the randomXDirection will be changed to equal -1
			randomXDirection--;
		}
			setXDirection(randomXDirection * initialSpeed);
		
			int randomYDirection = random.nextInt(2);
			
			if(randomYDirection == 0) { // if random is 0 ball will go up
				randomYDirection--;
			}
				setYDirection(randomYDirection * initialSpeed);	
			
	}
	
	public void setXDirection(int randomXDirection) {
		xVelocity = randomXDirection;
	}
	
	public void setYDirection(int randomYDirection) {
		yVelocity = randomYDirection;
	}
	
	public void move() {
		
		x+= xVelocity;
		
		ballX = x;
		
		y+= yVelocity;
		
		ballY = y;
		
	}
	
	public void draw(Graphics g) {
		
		g.setColor(Color.WHITE);
		
		g.fillOval(x, y, width, height);
		
	}
	
}
