import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.*;
import java.util.List;

import javax.swing.*;

public class GameFrame extends JFrame{

	GamePanel pane;	
	
	GameFrame(){
		
		pane = new GamePanel();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(pane);
		this.setTitle("Pong");
		this.setResizable(false);
		this.setBackground(Color.BLACK);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.setIconImage(new ImageIcon(getClass().getResource("logo.png")).getImage());

	}
	
}
