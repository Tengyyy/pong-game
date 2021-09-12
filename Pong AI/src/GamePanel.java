import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.sound.sampled.*;


public class GamePanel extends JPanel implements Runnable{

	public static final int GAME_WIDTH = 1000;
	public static final int GAME_HEIGHT = (int) (GAME_WIDTH * (0.5555));
	static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH, GAME_HEIGHT);
	public static final int BALL_DIAMETER = 20;
	static final int PADDLE_WIDTH = 25;
	public static final int PADDLE_HEIGHT = 100;
	Thread gameThread;
	Image image;
	Graphics graphics;
	Random random;
	Paddle paddle1;
	Paddle paddle2;
	Ball ball;
	Score score;
	
	Sound sound;
	
	GameOver gameOver;
	
	JButton homeButton = new JButton("Main Menu");
	JButton restartButton = new JButton("Restart Game");
	
	public static int difficulty = 2; // 1 == easy, 2 == medium, 3 == hard
	
	public static boolean running = false;
	
	boolean isPaused = false;
	
	public static boolean gameEnd = false;
	
	public static boolean singlePlayer = true;
	
	
	int endScore = 10;
	
	JButton easy;
	JButton medium;
	JButton hard;
	
	JLabel gameModeLabel;
	
	JButton singlePlayerButton;
	JButton multiPlayerButton;
	
	JLabel player1NameLabel;
	JLabel player2NameLabel;
	
	JLabel gameTitle;
	
	JTextField player1Name;
	JTextField player2Name;
	
	String p1Name = "Player 1";
	String p2Name = "Player 2";
	
	JLabel roundCountLabel;
	
	SpinnerModel sm = new SpinnerNumberModel(10, 1, 99, 1); // default value, lower bound, upper bound, increment by
	JSpinner roundCountSpinner;
	
	JButton startButton;
	
	public static int aiSpeedRandomizer;
	public static int ballSpeedRandomizer;
	
	
	GamePanel(){
		
		sound = new Sound();
		
		
		roundCountLabel = new JLabel("Game length:");
		roundCountLabel.setBounds(190, 370, 300, 70);
		//roundCountLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
		roundCountLabel.setHorizontalAlignment(SwingConstants.LEFT);
		roundCountLabel.setForeground(Color.WHITE);
		roundCountLabel.setFont(new Font("Consolas", Font.PLAIN, 30));
		
		roundCountSpinner = new JSpinner(sm);
		roundCountSpinner.setBounds(190,440,300,80);
		//roundCountSpinner.setForeground(Color.WHITE);
		roundCountSpinner.setBackground(Color.BLACK);
		roundCountSpinner.setOpaque(true);
		roundCountSpinner.setForeground(Color.WHITE);
		roundCountSpinner.setFont(new Font("Consolas", Font.PLAIN, 60));
		roundCountSpinner.setValue(10);
		((JSpinner.DefaultEditor) roundCountSpinner.getEditor()).getTextField().setHorizontalAlignment(SwingConstants.CENTER); // change spinners attributes by accessing the JTextField inside it.
		
		((JSpinner.DefaultEditor) roundCountSpinner.getEditor()).getTextField().setBackground(Color.BLACK);
		((JSpinner.DefaultEditor) roundCountSpinner.getEditor()).getTextField().setForeground(Color.WHITE);

		((JSpinner.DefaultEditor) roundCountSpinner.getEditor()).getTextField().setEditable(false);
		
		((JSpinner.DefaultEditor) roundCountSpinner.getEditor()).getTextField().setAlignmentY(SwingConstants.BOTTOM);

		
		roundCountSpinner.addChangeListener((e) ->  {
			endScore = (int) roundCountSpinner.getValue();
		});
		
		gameTitle = new JLabel("PONG");
		gameTitle.setForeground(Color.WHITE);
		gameTitle.setFont(new Font("Consolas", Font.PLAIN, 150));
		gameTitle.setBounds(325, 50, 350, 150);
		gameTitle.setHorizontalAlignment(SwingConstants.CENTER);
		gameTitle.setVerticalAlignment(SwingConstants.CENTER);
		
		gameModeLabel = new JLabel("Select a gamemode");
		gameModeLabel.setBounds(265, 250, 470, 50);
		gameModeLabel.setFont(new Font("Consolas", Font.PLAIN, 50));
		gameModeLabel.setForeground(Color.WHITE);
		gameModeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		gameModeLabel.setVerticalAlignment(SwingConstants.CENTER);
		
		
		singlePlayerButton = new JButton("Single-Player");
		singlePlayerButton.setBounds(190, 320, 300, 120);
		singlePlayerButton.setForeground(Color.WHITE);
		singlePlayerButton.setBackground(Color.BLACK);
		singlePlayerButton.setOpaque(true);
		singlePlayerButton.setFocusable(false);
		singlePlayerButton.setVerticalAlignment(SwingConstants.CENTER);
		singlePlayerButton.setFont(new Font("Consolas", Font.BOLD, 40));
		singlePlayerButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 5));
		singlePlayerButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		singlePlayerButton.addActionListener((e) -> {
			
			singlePlayer = true;
			
			gameModeLabel.setVisible(false);				
			singlePlayerButton.setVisible(false);				
			multiPlayerButton.setVisible(false);
			
			easy.setVisible(true);
			medium.setVisible(true);
			hard.setVisible(true);
			
			roundCountLabel.setVisible(true);
			roundCountSpinner.setVisible(true);
			
			startButton.setVisible(true);
			
			revalidate(); // makes the JSpinner work for some odd reason
			
		});
		
		
		multiPlayerButton = new JButton("Multi-Player");
		multiPlayerButton.setBounds(510, 320, 300, 120);
		multiPlayerButton.setForeground(Color.WHITE);
		multiPlayerButton.setBackground(Color.BLACK);
		multiPlayerButton.setOpaque(true);
		multiPlayerButton.setFocusable(false);
		multiPlayerButton.setVerticalAlignment(SwingConstants.CENTER);
		multiPlayerButton.setFont(new Font("Consolas", Font.BOLD, 40));
		multiPlayerButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 5));
		multiPlayerButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		multiPlayerButton.addActionListener((e) -> {
			
			singlePlayer = false;
			
			gameModeLabel.setVisible(false);				
			singlePlayerButton.setVisible(false);				
			multiPlayerButton.setVisible(false);				
			
			player1NameLabel.setVisible(true);
			player1Name.setVisible(true);
			player2NameLabel.setVisible(true);
			player2Name.setVisible(true);
			
			roundCountLabel.setVisible(true);

			roundCountSpinner.setVisible(true);
			
			startButton.setVisible(true);
			
			revalidate(); // makes the JSpinner work for some odd reason
			
		
		});
		
		
		//////////////////////////////////////////
		// DIFFICULTY BUTTONS
		//////////////////////////////////////////
		
		easy = new JButton("EASY");
		easy.setBackground(Color.BLACK);
		easy.setOpaque(true);
		easy.setFocusable(false);
		easy.setForeground(Color.WHITE);
		easy.setVerticalAlignment(SwingConstants.CENTER);
		easy.setFont(new Font("Consolas", Font.BOLD, 70));
		easy.setBorder(BorderFactory.createLineBorder(Color.WHITE, 6));
		easy.setCursor(new Cursor(Cursor.HAND_CURSOR));
		easy.setBounds(95,200,270,130);
		easy.addActionListener((e) -> {
			if(e.getSource() == easy) {
				
				difficulty = 1;
				
				easy.setForeground(Color.GREEN);
				easy.setBorder(BorderFactory.createLineBorder(Color.GREEN, 6));
				
				medium.setBorder(BorderFactory.createLineBorder(Color.WHITE, 6));
				medium.setForeground(Color.WHITE);
				
				hard.setBorder(BorderFactory.createLineBorder(Color.WHITE, 6));
				hard.setForeground(Color.WHITE);

			}
		});
		
		
		medium = new JButton("MEDIUM");
		medium.setBackground(Color.BLACK);
		medium.setOpaque(true);
		medium.setFocusable(false);
		medium.setForeground(Color.YELLOW);
		medium.setVerticalAlignment(SwingConstants.CENTER);
		medium.setFont(new Font("Consolas", Font.BOLD, 70));
		medium.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 6));
		medium.setCursor(new Cursor(Cursor.HAND_CURSOR));
		medium.setBounds(365,200,270,130);
		medium.addActionListener((e) -> {
			if(e.getSource() == medium) {
				difficulty = 2;
				
				medium.setForeground(Color.YELLOW);
				medium.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 6));
				
				easy.setBorder(BorderFactory.createLineBorder(Color.WHITE, 6));
				easy.setForeground(Color.WHITE);
				
				hard.setBorder(BorderFactory.createLineBorder(Color.WHITE, 6));
				hard.setForeground(Color.WHITE);

			}
		});
		
		
		hard = new JButton("HARD");
		hard.setBackground(Color.BLACK);
		hard.setOpaque(true);
		hard.setFocusable(false);
		hard.setForeground(Color.WHITE);
		hard.setVerticalAlignment(SwingConstants.CENTER);
		hard.setFont(new Font("Consolas", Font.BOLD, 70));
		hard.setBorder(BorderFactory.createLineBorder(Color.WHITE, 6));
		hard.setCursor(new Cursor(Cursor.HAND_CURSOR));
		hard.setBounds(635,200,270,130);
		hard.addActionListener((e) -> {
			if(e.getSource() == hard) {
				
				difficulty = 3;
				
				hard.setForeground(Color.RED);
				hard.setBorder(BorderFactory.createLineBorder(Color.RED, 6));
				
				medium.setBorder(BorderFactory.createLineBorder(Color.WHITE, 6));
				medium.setForeground(Color.WHITE);
				
				easy.setBorder(BorderFactory.createLineBorder(Color.WHITE, 6));
				easy.setForeground(Color.WHITE);

			}
		}); 
		
		
		
		////////////////////////
		// Player name fields
		///////////////////////
		
		
		player1NameLabel = new JLabel("Player 1:");
		player1NameLabel.setBounds(190, 180, 300, 80);
		player1NameLabel.setForeground(Color.BLUE);
		player1NameLabel.setFont(new Font("Consolas", Font.PLAIN, 50));
		
		
		player1Name = new JTextField();
		player1Name.setBounds(190, 260, 300, 80);
		player1Name.setFont(new Font("Consolas", Font.PLAIN, 50));
		player1Name.setForeground(Color.WHITE);
		player1Name.setBackground(Color.BLACK);
		player1Name.setCaretColor(Color.WHITE);
		
		player1Name.addKeyListener(new KeyAdapter() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				if(player1Name.getText().length() > 9) { // sets the maximum length of the name to be 10 characters
					e.consume(); // event will not be executed
				}
			}
			
		});
		
		
		
		player2NameLabel = new JLabel("Player 2:");
		player2NameLabel.setBounds(510, 180, 300, 80);
		player2NameLabel.setForeground(Color.RED);
		player2NameLabel.setFont(new Font("Consolas", Font.PLAIN, 50));
		
		
		player2Name = new JTextField();
		player2Name.setBounds(510, 260, 300, 80);
		player2Name.setFont(new Font("Consolas", Font.PLAIN, 50));
		player2Name.setForeground(Color.WHITE);
		player2Name.setBackground(Color.BLACK);
		player2Name.setCaretColor(Color.WHITE);
		player2Name.addKeyListener(new KeyAdapter() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				if(player2Name.getText().length() > 9) {
					e.consume(); // event will not be executed
				}
			}
			
		});
		

		// round count selector
		
		
		
		
		// START BUTTON
		
		
		startButton = new JButton("PLAY");
		startButton.setBackground(Color.BLACK);
		startButton.setOpaque(true);
		startButton.setFocusable(false);
		startButton.setForeground(new Color(247, 0, 255));
		startButton.setVerticalAlignment(SwingConstants.CENTER);
		startButton.setFont(new Font("Consolas", Font.BOLD, 80));
		startButton.setBorder(BorderFactory.createLineBorder(new Color(247, 0, 255), 8));
		startButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		startButton.setBounds(510,400,300,120);
		startButton.addActionListener((e) -> {
			if(e.getSource() == startButton) {
				
				if(!(player1Name.getText().isEmpty())) {
					p1Name = player1Name.getText();
				}
				
				if(!(player2Name.getText().isEmpty())) {
					p2Name = player2Name.getText();
				}
				
				gameTitle.setVisible(false);
				easy.setVisible(false);
				medium.setVisible(false);				
				hard.setVisible(false);				
				player1NameLabel.setVisible(false);				
				player1Name.setVisible(false);				
				player2NameLabel.setVisible(false);				
				player2Name.setVisible(false);				
				
				roundCountLabel.setVisible(false);				
				roundCountSpinner.setVisible(false);				
				
				startButton.setVisible(false);				
				
				this.requestFocus();
				
				startGame();
				
				repaint();
				
			}				
		});
		
		
		homeButton.setBounds(GAME_WIDTH / 2 - 360, GAME_HEIGHT / 2 + 100, 300, 120);
		homeButton.setBackground(Color.BLACK);
		homeButton.setOpaque(true);
		homeButton.setFocusable(false);
		homeButton.setForeground(Color.WHITE);
		homeButton.setFont(new Font("Consolas", Font.BOLD, 40));
		homeButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 5));
		homeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		homeButton.addActionListener((e) ->{
			if(e.getSource() == homeButton) {
				
				running = false;
				gameEnd = false;
				
				restartButton.setVisible(false);
				homeButton.setVisible(false);
				
				gameModeLabel.setVisible(true);
				singlePlayerButton.setVisible(true);
				multiPlayerButton.setVisible(true);
				gameTitle.setVisible(true);
				
				revalidate();
				repaint();
			}
		});
		
		restartButton.setBounds(GAME_WIDTH / 2 + 60, GAME_HEIGHT / 2 + 100, 300, 120);
		restartButton.setBackground(Color.BLACK);
		restartButton.setOpaque(true);
		restartButton.setFocusable(false);
		restartButton.setForeground(Color.WHITE);
		restartButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 5));
		restartButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		restartButton.setFont(new Font("Consolas", Font.BOLD, 40));
		restartButton.addActionListener((e) -> {
			if(e.getSource() == restartButton) {
				
				gameEnd = false;
				
				restartButton.setVisible(false);
				homeButton.setVisible(false);
				
				newPaddles();
				newBall();
				score.player1 = 0;
				score.player2 = 0;
				
			}
			
		});
		
		//
		//
		
		easy.setVisible(false);
		medium.setVisible(false);
		hard.setVisible(false);
		player1NameLabel.setVisible(false);
		player1Name.setVisible(false);
		player2NameLabel.setVisible(false);
		player2Name.setVisible(false);
		roundCountLabel.setVisible(false);
		roundCountSpinner.setVisible(false);
		startButton.setVisible(false);
		homeButton.setVisible(false);
		restartButton.setVisible(false);
		
		this.setLayout(null);
		this.setFocusable(true); // panel has to be in focus for keypresses to work
		this.addKeyListener(new AL()); // creates new instance of the AL (ActionListener) class and uses it as the keylistener for the gamepanel
		this.setPreferredSize(SCREEN_SIZE);
		this.add(gameTitle);
		this.add(gameModeLabel);
		this.add(singlePlayerButton);
		this.add(multiPlayerButton);
		
		this.add(easy);
		this.add(medium);
		this.add(hard);
		this.add(player1NameLabel);
		this.add(player1Name);
		this.add(player2NameLabel);
		this.add(player2Name);
		
		this.add(roundCountLabel);
		this.add(roundCountSpinner);
		this.add(startButton);
		
		this.add(homeButton);
		this.add(restartButton);

		gameThread = new Thread(this);
		gameThread.start();
		
	}
	
	
	
	public void startGame() {
		
		newPaddles();
		newBall();
		
		running = true;
		
		score = new Score(GAME_WIDTH, GAME_HEIGHT);
		
		sound.playSound(3);
		
	}
	
	public void newBall() {
		random = new Random();
		 ball = new Ball((GAME_WIDTH / 2) - (BALL_DIAMETER / 2), random.nextInt(GAME_HEIGHT - BALL_DIAMETER), BALL_DIAMETER, BALL_DIAMETER);
	}
	
	public void newPaddles() {
		
		paddle1 = new Paddle(0,((GAME_HEIGHT / 2) - (PADDLE_HEIGHT/2)), PADDLE_WIDTH, PADDLE_HEIGHT, 1, false); // centers the paddle on the y-axis
		
		if(singlePlayer) {
			paddle2 = new Paddle((GAME_WIDTH - PADDLE_WIDTH),((GAME_HEIGHT / 2) - (PADDLE_HEIGHT/2)), PADDLE_WIDTH, PADDLE_HEIGHT, 2, true); // sets the second paddle to be the AI paddle if single player mode is selected
		}
		else {
		paddle2 = new Paddle((GAME_WIDTH - PADDLE_WIDTH),((GAME_HEIGHT / 2) - (PADDLE_HEIGHT/2)), PADDLE_WIDTH, PADDLE_HEIGHT, 2, false);
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		
		
		image = createImage(getWidth(), getHeight()); // loob v2ljaspool ekraani asuva pildi (double-bufferingu jaoks)
		graphics = image.getGraphics(); // v6imaldab loodud ekraaniv2lisele pildile joonistada
	
		draw(graphics);
		
		g.drawImage(image, 0, 0, this); // this passes the GamePanel as the image observer, which will be notified when more of the image is able to be drawn
	}
	
	public void draw(Graphics g) {
		

		
		if(running && !gameEnd) {
			score.draw(g);
			paddle1.draw(g);
			paddle2.draw(g);
			ball.draw(g);	
		}	
		
		else if (running & gameEnd){
			gameOver.draw(g);
			score.draw(g);
		}
		
		Toolkit.getDefaultToolkit().sync();
	}
	
	public void move() {
		paddle1.move(); // will execute the Paddle.move() method every game cycle which will make them move smoother
		paddle2.move();
		ball.move();
	}
	public void checkCollision() {
		
		// bounces ball off top and bottom window edges:
		
		if(ball.y <= 0) {
			ball.setYDirection(-Ball.yVelocity);
			sound.playSound(3);
			
		}
		if(ball.y >= GAME_HEIGHT - BALL_DIAMETER) {
			ball.setYDirection(-Ball.yVelocity);
			sound.playSound(3);	
		}
		
		// bounces ball off of the paddles:
		
		if(ball.intersects(paddle1)) {
			
			Ball.xVelocity = Math.abs(Ball.xVelocity);
			
			ballSpeedRandomizer = random.nextInt(2); // will randomly determine if the ball speed should increase or not
			
			aiSpeedRandomizer = random.nextInt(10);
			
			if(Ball.xVelocity <= difficulty * 3) {
				
				
				if(Math.abs(Ball.xVelocity) < difficulty + 1) {
					Ball.xVelocity++;
				}
				else if(ballSpeedRandomizer == 0) {
						Ball.xVelocity++;
				}
				
			}
				
			if(Math.abs(Ball.yVelocity ) <= difficulty * 3) {
				if(Ball.yVelocity > 0) {
					
					if(Ball.yVelocity < difficulty + 1) {
						Ball.yVelocity++;
					}
					
					else if(ballSpeedRandomizer == 0) {
						Ball.yVelocity++;
					}
				}
				else if(Ball.yVelocity < 0){
					
					if(Math.abs(Ball.yVelocity) < difficulty + 1) {
						Ball.yVelocity--;
					}
					
					else if(ballSpeedRandomizer == 0) {
						Ball.yVelocity--;
					}
				}
			}
			
			ball.setXDirection(Ball.xVelocity);
			ball.setYDirection(Ball.yVelocity);
			
			
			sound.playSound(1);
			
		}
		
		if(ball.intersects(paddle2)) {
			Ball.xVelocity = -Ball.xVelocity;
			
			ballSpeedRandomizer = random.nextInt(2);
			
			aiSpeedRandomizer = random.nextInt(10);
			
			if(Ball.xVelocity >= -difficulty * 3) {
				
				if(Math.abs(Ball.xVelocity) < difficulty + 1) {
					Ball.xVelocity--;
				}
				
				else if(ballSpeedRandomizer == 0) {
					Ball.xVelocity--;
				}
			}
			
			if(Math.abs(Ball.yVelocity) <= difficulty * 3) {
				if(Ball.yVelocity > 0) {
					
					if(Math.abs(Ball.yVelocity) < difficulty + 1) {
						Ball.yVelocity++;
					}
					
					else if(ballSpeedRandomizer == 0) {
						Ball.yVelocity++;
					}
				}
				else {
					
					if(Math.abs(Ball.yVelocity) < difficulty + 1) {
						Ball.yVelocity--;
					}
					
					else if(ballSpeedRandomizer == 0) {
						Ball.yVelocity--;
					}
				}
			}
			
			ball.setXDirection(Ball.xVelocity);
			ball.setYDirection(Ball.yVelocity);
			
			sound.playSound(1);
		}
		
		// this stops paddles at window edges:
		if(paddle1.y <= 0) {
			paddle1.y = 0;
		}
		if(paddle1.y >=  (GAME_HEIGHT - PADDLE_HEIGHT)) {
			paddle1.y = GAME_HEIGHT - PADDLE_HEIGHT;
		}
		
		if(paddle2.y <= 0) {
			paddle2.y = 0;
		}
		if(paddle2.y >=  (GAME_HEIGHT - PADDLE_HEIGHT)) {
			paddle2.y = GAME_HEIGHT - PADDLE_HEIGHT;
		}
		
		// give player a point if he scores and create new paddles & ball
		
		if(ball.x <= 0) {
			score.player2++;
			
			sound.playSound(2);
			
			checkScore();
			
			newPaddles();
			newBall();
			System.out.println("Player2: " + score.player2);
		}
		
		if(ball.x >= GAME_WIDTH - BALL_DIAMETER) {
			score.player1++;
			
			sound.playSound(2);
			
			checkScore();
			
			newPaddles();
			newBall();
			System.out.println("Player1: " + score.player1);
		}
	}
	
	public void checkScore() {
		
		
		if(score.player1 == endScore) {
			
			gameEnd = true;
			
			gameOver = new GameOver(p1Name, 1, GAME_WIDTH, GAME_HEIGHT);
		
			
			homeButton.setVisible(true);
			restartButton.setVisible(true);
	
			repaint();
		}
		
		
		else if (score.player2 == endScore) {
			
			gameEnd = true;
			
			gameOver = new GameOver(p2Name, 2, GAME_WIDTH, GAME_HEIGHT);
	
			homeButton.setVisible(true);
			restartButton.setVisible(true);
			
			this.add(homeButton);
			this.add(restartButton);
			
			repaint();
			
		}
	}
	
	public void run() {
		
		// game loop minecraftist, jooksutab mängu 60 fpsiga vms, väga ei saa aru
				long lastTime = System.nanoTime();
				double amountOfTicks = 120.0;
				double ns = 1000000000 / amountOfTicks; // time in nanoseconds between each tick, 1 billion nanoseconds = 1 second
				double delta = 0;
				
				while(true) {
					
					long now = System.nanoTime();
					delta+= (now - lastTime)/ ns;
					lastTime = now;
					
					if(delta >= 1) {
						if( running && !gameEnd) {
							move();
							checkCollision();							
						}
						repaint();
						delta--;
					}
				}				
		
	}
	public class AL extends KeyAdapter {
		
		@Override
		public void keyPressed(KeyEvent e) {
			
			if(running && !gameEnd) {
				paddle1.keyPressed(e);
				paddle2.keyPressed(e);
			}	
		}
		
		@Override
		public void keyReleased(KeyEvent e) {
			if(running && !gameEnd) {
				paddle1.keyReleased(e);
				paddle2.keyReleased(e);
			}
		}
	}
}
