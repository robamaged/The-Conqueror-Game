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

public class BattleView extends JFrame implements ActionListener{
	private JPanel info;
	private JPanel rightPanel;
	private JTextArea ArmiesInfo;
	private JTextArea PlayerInfo;
	private JTextArea BattleInfo;
	private JButton laySiege;
	private JButton Attack;
	private JButton AutoResolve;
	private JButton EndTurn;
	
	
	public BattleView()
	{
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		this.setBounds(0, 0, (int) d.getWidth(), (int) d.getHeight());
		Attack = new JButton("Attack Manually");
		AutoResolve = new JButton("Auto Resolve");
		laySiege = new JButton("Lay Siege on the city");
		EndTurn = new JButton("End Turn");
		info = new JPanel();
		info.setLayout(new GridLayout(20, 20));
		info.setPreferredSize(new Dimension(400, this.HEIGHT));
		rightPanel = new JPanel();
		rightPanel.setLayout(new GridLayout(2,1));
		rightPanel.setPreferredSize(new Dimension(300, this.HEIGHT));
		PlayerInfo = new JTextArea();
		ArmiesInfo = new JTextArea();
		BattleInfo = new JTextArea();
		PlayerInfo.setPreferredSize(new Dimension(300, 300));
		ArmiesInfo.setPreferredSize(new Dimension(400, this.HEIGHT));
		BattleInfo.setPreferredSize(new Dimension(300, (this.HEIGHT-300)));
		rightPanel.add(PlayerInfo);
		rightPanel.add(BattleInfo);
		this.add(info, BorderLayout.CENTER);
		this.add(rightPanel, BorderLayout.EAST);
		this.add(ArmiesInfo, BorderLayout.WEST);
		PlayerInfo.setVisible(true);
		PlayerInfo.setEditable(false);
		ArmiesInfo.setVisible(true);
		ArmiesInfo.setEditable(false);
		this.setTitle("Battle View");
		this.setVisible(true);
		this.revalidate();
		this.repaint();
	}
	public JTextArea getBattleInfo() {
		return BattleInfo;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	public JTextArea getArmiesInfo() {
		return ArmiesInfo;
	}
	public JPanel getInfo() {
		return info;
	}
	public JTextArea getPlayerInfo() {
		return PlayerInfo;
	}
//	public static void main(String[] args) {
//		new BattleView();
//	}
	public JButton getEndTurn() {
		return EndTurn;
	}
	public JButton getLaySiege() {
		return laySiege;
	}
	public JButton getAttack() {
		return Attack;
	}
	public JButton getAutoResolve() {
		return AutoResolve;
	}
}
