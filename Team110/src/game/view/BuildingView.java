package game.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class BuildingView extends JFrame{
	private JTextArea PlayerInfo;
	private JTextArea BuildingInfo;
	private JPanel info;
	private JButton upgrade;
	public BuildingView()
	{
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		this.setBounds(0, 0, (int) d.getWidth(), (int) d.getHeight());
		info = new JPanel();
		info.setLayout(new GridLayout(20, 20));
		info.setPreferredSize(new Dimension(500, (this.HEIGHT-200)));
		this.add(info, BorderLayout.CENTER);
		PlayerInfo = new JTextArea();
		PlayerInfo.setPreferredSize(new Dimension(200, this.HEIGHT));
		this.add(PlayerInfo, BorderLayout.EAST);
		PlayerInfo.setVisible(true);
		PlayerInfo.setEditable(false);
		BuildingInfo = new JTextArea();
		BuildingInfo.setPreferredSize(new Dimension(200, 200));
		this.add(BuildingInfo, BorderLayout.NORTH);
		BuildingInfo.setVisible(true);
		BuildingInfo.setEditable(false);
		upgrade = new JButton("Upgrade Building");
		upgrade.setPreferredSize(new Dimension(100, 100));
		info.add(upgrade);
		this.setVisible(true);	
		this.revalidate();
		this.repaint();		
		
	}
	public JTextArea getPlayerInfo() {
		return PlayerInfo;
	}
	public JTextArea getBuildingInfo() {
		return BuildingInfo;
	}
	public JPanel getInfo() {
		return info;
	}
	public JButton getUpgrade() {
		return upgrade;
	}
}
