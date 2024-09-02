package gui;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

import logika.LOGIKA;

public class GUI extends JPanel implements KeyListener, ActionListener {
	private LOGIKA game;
	
	//konstruktor
	public GUI(LOGIKA game) {
		this.game = game;
		addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        
        game.timer = new Timer(game.delay, this);
        game.timer.start();
	}
	
	public void paint(Graphics g) {
		//pozadina
		g.setColor(Color.white);
		g.fillRect(1, 1, 692, 592);
		
		//bricks (cigle)
		game.getBricks().draw((Graphics2D) g);
		
		//ispis bodova
		g.setColor(Color.blue);
		g.setFont(new Font("arial", Font.ITALIC, 25));
		g.drawString("Broj bodova: "+game.getBodovi(), 20, 30);
		
		//igrac
		g.setColor(Color.blue);
		g.fillRect(game.getIgrac(), 550, 100, 8);
		
		//lopta
		g.setColor(Color.red);
		g.fillOval(game.getPozicijaLopteX(), game.getPozicijaLopteY(), 20, 20);
		
		//pobijedili ste!
		if (game.win()) {
			game.restart();
			
			g.setColor(Color.blue);
			g.setFont(new Font("arial", Font.ITALIC, 25));
            g.drawString("Pobijedili ste!", 200, 300);
            g.drawString("Broj bodova: " + game.getBodovi(), 200, 350);
		}
		
		//izgubili ste!
		if (game.lose()) {
			game.restart();
			
			g.setColor(Color.blue);
			g.setFont(new Font("arial", Font.ITALIC, 25));
            g.drawString("Izgubili ste!", 200, 300);
            g.drawString("Broj bodova: " + game.getBodovi(), 200, 350);
		}
		
		g.dispose();
	}
	
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			game.desno();
		}
		
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			game.lijevo();
		}
	}
	
	public void keyReleased(KeyEvent e) {}
	
	public void keyTyped(KeyEvent e) {}
	
	public void actionPerformed(ActionEvent e) {
		game.timer.start();
		
		if (game.getPlay()) {
			game.glavnaFunkcija();
			repaint();
		}
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		LOGIKA game = new LOGIKA();
		GUI gui = new GUI(game);
		
		frame.setBounds(10, 10, 700, 700);
		frame.setTitle("BRICK BREAKER - ALMA BABIC");
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(gui);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}
}
