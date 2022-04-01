package game.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class BuildingTypeView extends JFrame implements ActionListener{
	JPanel panel;
	public BuildingTypeView()
	{
		this.setBounds(0, 0, 100, 100);
		
		this.setLayout(new BorderLayout());
		panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setPreferredSize(new Dimension(this.getWidth(), this.HEIGHT));
		this.add(panel,BorderLayout.CENTER);
		//this.setVisible(true);
		this.revalidate();
	    this.repaint();
	}
	public JPanel getPanel() {
		return panel;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
