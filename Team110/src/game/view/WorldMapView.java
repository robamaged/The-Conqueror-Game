package game.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import game.controller.Controller;

public class WorldMapView extends JFrame implements ActionListener {
	private JTextArea PlayerInfo;
	private JTextArea mapInfo;
	private JPanel info;
	private JButton Sparta;
	private JButton Cairo;
	private JButton Rome;

	public WorldMapView() {
		
		info = new JPanel();
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		this.setBounds(0, 0, (int) d.getWidth(), (int) d.getHeight());
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		info.setLayout(new GridLayout(20, 20));
		info.setPreferredSize(new Dimension(500, this.HEIGHT));
		this.add(info, BorderLayout.CENTER);
		PlayerInfo = new JTextArea();
		PlayerInfo.setPreferredSize(new Dimension(200, this.HEIGHT));
		this.add(PlayerInfo, BorderLayout.EAST);
		PlayerInfo.setVisible(true);
		PlayerInfo.setEditable(false);
		mapInfo = new JTextArea();
		mapInfo.setPreferredSize(new Dimension(200, this.HEIGHT));
		this.add(mapInfo, BorderLayout.WEST);
		mapInfo.setVisible(true);
		mapInfo.setEditable(false);
		Cairo = new JButton("Cairo");
		Cairo.setPreferredSize(new Dimension(200, 200));
		Sparta = new JButton("Sparta");
		Sparta.setPreferredSize(new Dimension(200, 200));
		Sparta.addActionListener(this);
		Rome = new JButton("Rome");
		Rome.setPreferredSize(new Dimension(200, 200));
		info.add(Sparta);
		info.add(Cairo);
		info.add(Rome);
		this.add(info);
		this.setTitle("World Map");
		this.setVisible(true);
		this.revalidate();
		this.repaint();

	}

	public JTextArea getMapInfo() {
		return mapInfo;
	}

	public void actionPerformed(ActionEvent e) {

	}

	
	public JTextArea getPlayerInfo() {
		return PlayerInfo;
	}
	public JPanel getInfo() {
		return info;
	}
	public JButton getSparta() {
		return Sparta;
	}

	public JButton getCairo() {
		return Cairo;
	}

	public JButton getRome() {
		return Rome;
	}

}
