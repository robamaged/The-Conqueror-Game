package game.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import engine.City;

public class CityView extends JFrame implements ActionListener{
	private JPanel leftpanel;
	private JPanel chooseUnit;
	private JPanel info;
	private JPanel chooseBuilding;
	private JTextArea PlayerInfo;
	private JTextArea cityInfo;
	private JTextArea armyInfo;
	private JTextArea stationedArmies;
	private JButton building;
	private JButton build;
	private JButton Upgrade;
	private JButton initiateArmy;
	private JButton recruitUnit;
	private JButton addUnits;
	private JButton EndTurn;
	private JButton targetACity;
	private JComboBox buildingtype;
	private JComboBox UnitType;

	public CityView()
	{
		build = new JButton("BUILD");
		recruitUnit = new JButton("Recruit Unit");
		Upgrade = new JButton("Upgrade a Building");
		initiateArmy = new JButton("Initiate Army");
		EndTurn = new JButton("End Turn");
		targetACity = new JButton("Target A City");
		addUnits = new JButton("Add units to the initiated army");
		info = new JPanel();
		chooseBuilding = new JPanel();
		chooseUnit = new JPanel();
		leftpanel = new JPanel();
		armyInfo = new JTextArea();
		stationedArmies = new JTextArea();
		String[] Buildingtypes = { "", "Farm", "Market", "ArcheryRange", "Barracks", "Stable" };
		String[] Unittypes = { "", "Archer", "Cavalry", "Infantry" };
		buildingtype = new JComboBox(Buildingtypes);
		UnitType = new JComboBox(Unittypes);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		this.setBounds(0, 0, (int) d.getWidth(), (int) d.getHeight());
		info.setLayout(new GridLayout(20, 20));
		leftpanel.setLayout(new GridLayout(4,1));
		leftpanel.setPreferredSize(new Dimension(400, (this.HEIGHT)));
		info.setPreferredSize(new Dimension(300, this.HEIGHT));
		chooseBuilding.setLayout(new BorderLayout());
		chooseBuilding.setVisible(true);
		chooseUnit.setLayout(new BorderLayout());
		chooseUnit.setVisible(true);
		this.add(info, BorderLayout.CENTER);
		chooseBuilding.add(buildingtype);
		chooseUnit.add(UnitType);
		PlayerInfo = new JTextArea();
		PlayerInfo.setPreferredSize(new Dimension(200, this.HEIGHT));
		this.add(PlayerInfo, BorderLayout.EAST);
		PlayerInfo.setVisible(true);
		PlayerInfo.setEditable(false);
		cityInfo = new JTextArea();
		cityInfo.setPreferredSize(new Dimension(400, (this.HEIGHT/3)));
		armyInfo.setPreferredSize(new Dimension(400, (this.HEIGHT/3)));
		stationedArmies.setPreferredSize(new Dimension(400,(this.HEIGHT/3)));
		leftpanel.add(cityInfo);
		leftpanel.add(armyInfo);
		leftpanel.add(stationedArmies);
		this.add(leftpanel, BorderLayout.WEST);
		cityInfo.setVisible(true);
		cityInfo.setEditable(false);
		armyInfo.setVisible(true);
		armyInfo.setEditable(false);
		this.setTitle("City View");
		this.setVisible(true);	
		this.revalidate();
		this.repaint();		
	}
		


	public JButton getRecruitUnit() {
		return recruitUnit;
	}


	public JButton getBuild() {
		return build;
	}


	public void actionPerformed(ActionEvent e) {
		
		
	}

	public JTextArea getPlayerInfo() {
		return PlayerInfo;
	}
	
	public JPanel getInfo() {
		return info;
	}
	public JTextArea getCityInfo() {
		return cityInfo;
	}
	public JComboBox getBuildingtype() {
		return buildingtype;
	}



	public JComboBox getUnitType() {
		return UnitType;
	}
	

	public JComponent getChooseBuilding() {
		 return chooseBuilding;
		
	}
	public JComponent getChooseUnit() {
		 return chooseUnit;
			
	}



	public JButton getUpgrade() {
		return Upgrade;
	}



	public JButton getInitiateArmy() {
		return initiateArmy;
	}



	public JButton getEndTurn() {
		return EndTurn;
	}



	public JPanel getLeftpanel() {
		return leftpanel;
	}



	public JTextArea getArmyInfo() {
		return armyInfo;
	}



	public JTextArea getStationedArmies() {
		return stationedArmies;
	}



	public JButton getTargetACity() {
		return targetACity;
	}



	public JButton getAddUnits() {
		return addUnits;
	}

}
