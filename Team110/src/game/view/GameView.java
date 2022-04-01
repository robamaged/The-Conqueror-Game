package game.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class GameView extends JFrame implements ActionListener {
	private JTextArea PlayerInfo;
	private JPanel game;

	private JButton button;
	public GameView() {
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		this.setBounds(0, 0, (int) d.getWidth(), (int) d.getHeight());
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new GridLayout(1,0));
		this.setTitle("The Conquerer");

		this.revalidate();
		this.repaint();
		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {

	}

}
