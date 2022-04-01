package game.controller;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import buildings.ArcheryRange;
import buildings.Barracks;
import buildings.Building;
import buildings.EconomicBuilding;
import buildings.Farm;
import buildings.Market;
import buildings.MilitaryBuilding;
import buildings.Stable;
import engine.City;
import engine.Game;
import exceptions.BuildingInCoolDownException;
import exceptions.FriendlyCityException;
import exceptions.FriendlyFireException;
import exceptions.MaxCapacityException;
import exceptions.MaxLevelException;
import exceptions.MaxRecruitedException;
import exceptions.NotEnoughGoldException;
import exceptions.TargetNotReachedException;
import game.view.BattleView;
import game.view.BuildingTypeView;
import game.view.BuildingView;
import game.view.CityView;
import game.view.GameView;
import game.view.UnitTypeView;
import game.view.WorldMapView;
import units.Archer;
import units.Army;
import units.Cavalry;
import units.Infantry;
import units.Status;
import units.Unit;

public class Controller implements ActionListener {
	private Game model;
	private WorldMapView map;
	private CityView cityView;
	private GameView view;
	private BattleView battle;
	private JButton start;
	private JTextField name;
	private JComboBox city;
	private JPanel game;
	private String CityName;
	private String PlayerName;
	private JLabel Entername;
	private JLabel ChooseCity;
	private City c = null;
	private City target = null;
	private Army a = null;
	private Army enemy=null;
	private Unit opposing = null;
	private Unit mine = null;
	private JComboBox BuildingsToUpgrade;
	private JComboBox UnitForInitiation;
	private JComboBox Availablecities;
	private JComboBox listOfUnits4Attack;
	private JComboBox attackunit;
	private JTextArea myunit;
	private JComboBox Aunits;

	public Controller() throws IOException {

		game = new JPanel();
		game.setLayout(null);
		Entername = new JLabel("Enter your name:");
		ChooseCity = new JLabel("Choose a city:");
		Entername.setBounds(5, 20, 200, 20);
		ChooseCity.setBounds(5, 100, 200, 20);
		start = new JButton();
		start.setPreferredSize(new Dimension(50, 50));
		start.addActionListener(this);
		name = new JTextField();
		name.setBounds(5, 60, 100, 20);
		name.addActionListener(this);
		start.setText("Start");
		start.setBounds(600, 200, 100, 50);
		String[] cities = { "", "Cairo", "Sparta", "Rome" };
		city = new JComboBox<>(cities);
		city.setBounds(5, 140, 100, 20);
		game.add(Entername);
		game.add(name);
		game.add(ChooseCity);
		game.add(city);
		game.add(start);
		view = new GameView();
		game.setPreferredSize(new Dimension(view.getWidth(), view.getHeight()));
		view.add(game);
		view.revalidate();
		view.repaint();
		game.setVisible(true);
		map = new WorldMapView();
		map.setVisible(false);
		cityView = new CityView();
		cityView.setVisible(false);
		battle = new BattleView();
		battle.setVisible(false);
		attackunit=new JComboBox();
		attackunit.addItem("");
		myunit=new JTextArea();
		Aunits=new JComboBox();
		Aunits.addItem("");
		myunit.setText("choose a unit to attack with ");
		myunit.setEditable(false);
		battle.getInfo().add(battle.getAttack());
		battle.getInfo().add(attackunit);
		battle.getInfo().add(myunit);
		battle.getInfo().add(Aunits);
		battle.getInfo().add(battle.getAutoResolve());
		battle.getInfo().add(battle.getLaySiege());
		battle.getInfo().add(battle.getEndTurn());
		
		cityView.getInfo().add(cityView.getBuild());
		cityView.getInfo().add(cityView.getChooseBuilding());
		cityView.getInfo().add(cityView.getUpgrade());
		BuildingsToUpgrade = new JComboBox<>();
		BuildingsToUpgrade.addItem("");
		cityView.getInfo().add(BuildingsToUpgrade);
		cityView.getInfo().add(cityView.getRecruitUnit());
		cityView.getInfo().add(cityView.getChooseUnit());
		cityView.getInfo().add(cityView.getInitiateArmy());
		UnitForInitiation = new JComboBox();
		UnitForInitiation.addItem("");
		listOfUnits4Attack = new JComboBox();
		listOfUnits4Attack.addItem("");
		cityView.getInfo().add(UnitForInitiation);
		cityView.getInfo().add(cityView.getAddUnits());
		cityView.getInfo().add(listOfUnits4Attack);
		cityView.getInfo().add(cityView.getTargetACity());
		Availablecities = new JComboBox();
		Availablecities.addItem("");
		cityView.getInfo().add(Availablecities);
		cityView.getInfo().add(cityView.getEndTurn());
		
		cityView.getBuild().addActionListener(this);
		cityView.getRecruitUnit().addActionListener(this);
		cityView.getUpgrade().addActionListener(this);
		cityView.getInitiateArmy().addActionListener(this);
		cityView.getEndTurn().addActionListener(this);
		cityView.getTargetACity().addActionListener(this);	
		cityView.getAddUnits().addActionListener(this);
		
		map.getCairo().addActionListener(this);
		map.getSparta().addActionListener(this);
		map.getRome().addActionListener(this);
		
		battle.getAttack().addActionListener(this);
		battle.getAutoResolve().addActionListener(this);
		battle.getLaySiege().addActionListener(this);
		battle.getEndTurn().addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		CityName = (String) city.getSelectedItem();
		PlayerName = name.getText();
		if (e.getSource() == start && (CityName.equals("") || PlayerName.equals("")))
			JOptionPane.showMessageDialog(null, "Please enter your name and choose a city first!");
		else if (e.getSource() == start && !CityName.equals("") && !PlayerName.equals("")) {
			try {

				model = new Game(name.getText(), CityName);
				String citiesToTarget = "";
				for (int i = 0; i < model.getAvailableCities().size(); i++) {
					if (!model.getPlayer().getControlledCities().contains(model.getAvailableCities().get(i)))
						Availablecities.addItem(model.getAvailableCities().get(i).getName());
				}

				view.setVisible(false);
				displayMap();

			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		// CAIRO
		else if (e.getSource() == map.getCairo()) {
			for (int i = 0; i < model.getPlayer().getControlledCities().size(); i++) {
				if (model.getPlayer().getControlledCities().get(i).getName().equalsIgnoreCase("cairo")) {
					c = model.getPlayer().getControlledCities().get(i);
					displayCityView(c);
				} else
					JOptionPane.showMessageDialog(null, "You cannot view this city because you don't control it yet!");
			}
		}

		// SPARTA
		else if (e.getSource() == map.getSparta()) {
			for (int i = 0; i < model.getPlayer().getControlledCities().size(); i++) {
				if (model.getPlayer().getControlledCities().get(i).getName().equalsIgnoreCase("Sparta")) {
					c = model.getPlayer().getControlledCities().get(i);
					displayCityView(c);
				} else
					JOptionPane.showMessageDialog(null, "You cannot view this city because you don't control it yet!");
			}
		}

		// ROME
		else if (e.getSource() == map.getRome()) {
			for (int i = 0; i < model.getPlayer().getControlledCities().size(); i++) {
				if (model.getPlayer().getControlledCities().get(i).getName().equalsIgnoreCase("Rome")) {
					c = model.getPlayer().getControlledCities().get(i);
					displayCityView(c);

				} else
					JOptionPane.showMessageDialog(null, "You cannot view this city because you don't control it yet!");
			}
		}
		// BUILD
		else if (e.getSource() == cityView.getBuild()) {

			String buildingchosen = (String) cityView.getBuildingtype().getSelectedItem();

			if (!buildingchosen.equals("")) {
				try {
					model.getPlayer().onBuild(buildingchosen, c);
					gameOver();
					BuildingsToUpgrade.addItem(buildingchosen);
					cityView.getBuildingtype().setSelectedIndex(0);
					displayCityView(c);

				} catch (NotEnoughGoldException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}
			} else
				JOptionPane.showMessageDialog(null, "Please choose a building from the list first!");
		}
		// RECRUIT UNIT
		else if (e.getSource() == cityView.getRecruitUnit()) {
			int UnitCountB4 = c.getDefendingArmy().getUnits().size();
			String unitchosen = (String) cityView.getUnitType().getSelectedItem();
			cityView.getUnitType().setSelectedIndex(0);
			if (!unitchosen.equals("")) {
				try {
					model.getPlayer().onRecruitUnit(unitchosen, c.getName());
					int UnitCountAfter = c.getDefendingArmy().getUnits().size();
					if (UnitCountAfter > UnitCountB4)
						UnitForInitiation.addItem(unitchosen);
					displayMap();
					displayCityView(c);
					gameOver();
				} catch (BuildingInCoolDownException | MaxRecruitedException | NotEnoughGoldException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}
			} else
				JOptionPane.showMessageDialog(null,
						"Please choose the unit that you want to recruit from the list first!");
		}
		// UPGRADE BUILDING
		else if (e.getSource() == cityView.getUpgrade()) {

			String selectedBuilding = (String) BuildingsToUpgrade.getSelectedItem();
			for (Building b : c.getEconomicalBuildings()) {
				if (selectedBuilding.equals("Farm") && b instanceof Farm) {
					try {
						model.getPlayer().onUpgradeBuilding(b);
						gameOver();
						displayCityView(c);
						break;
					} catch (NotEnoughGoldException | BuildingInCoolDownException | MaxLevelException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage());
					}
				}
				if (selectedBuilding.equals("Market") && b instanceof Market) {
					try {
						model.getPlayer().onUpgradeBuilding(b);
						gameOver();
						break;
					} catch (NotEnoughGoldException | BuildingInCoolDownException | MaxLevelException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage());
					}
				}
				if (selectedBuilding.equals("ArcheryRange") && b instanceof ArcheryRange) {
					try {
						model.getPlayer().onUpgradeBuilding(b);
						gameOver();
						break;
					} catch (NotEnoughGoldException | BuildingInCoolDownException | MaxLevelException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage());
					}
				}
				if (selectedBuilding.equals("Barracks") && b instanceof Barracks) {
					try {
						model.getPlayer().onUpgradeBuilding(b);
						gameOver();
						break;
					} catch (NotEnoughGoldException | BuildingInCoolDownException | MaxLevelException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage());
					}
				}
				if (selectedBuilding.equals("Stable") && b instanceof Stable) {
					try {
						model.getPlayer().onUpgradeBuilding(b);
						gameOver();
						break;
					} catch (NotEnoughGoldException | BuildingInCoolDownException | MaxLevelException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage());
					}
				}
			}
		}
		// INITIATE ARMY
		else if (e.getSource() == cityView.getInitiateArmy()) {
			String selectedUnit = (String) UnitForInitiation.getSelectedItem();
			
			for (Unit u : c.getDefendingArmy().getUnits()) {
				if (u instanceof Archer && selectedUnit.equals("Archer")) {
					
					model.getPlayer().initiateArmy(c, u);
					displayMap();
					displayCityView(c);
				} else if (u instanceof Infantry && selectedUnit.equals("Infantry")) {

					model.getPlayer().initiateArmy(c, u);
					displayMap();
					displayCityView(c);
				} else {
					model.getPlayer().initiateArmy(c, u);
					displayMap();
					displayCityView(c);
				}
				u.getParentArmy().setTarget((String) Availablecities.getSelectedItem());
				a = u.getParentArmy();
			}
			addUnits();
		} 
		//RELOCATE UNIT
		else if (e.getSource() == cityView.getAddUnits()) {
			if (listOfUnits4Attack.getSelectedItem().equals(""))
				JOptionPane.showMessageDialog(null, "choose a unit to add first!");
			else {
//				for (Army rob : model.getPlayer().getControlledArmies()) {
					for (Unit u : c.getDefendingArmy().getUnits()) {
						if (listOfUnits4Attack.getSelectedItem().equals("Archer") && u instanceof Archer) {
							try {
								a.relocateUnit(u);
								break;
							} catch (MaxCapacityException e1) {
								JOptionPane.showMessageDialog(null, e1.getMessage());
							}
						} else if (listOfUnits4Attack.getSelectedItem().equals("Cavalry") && u instanceof Cavalry) {
							try {
								a.relocateUnit(u);
								break;
							} catch (MaxCapacityException e1) {
								JOptionPane.showMessageDialog(null, e1.getMessage());
							}
						} else if (listOfUnits4Attack.getSelectedItem().equals("Infantry") && u instanceof Infantry) {
							try {
								a.relocateUnit(u);
								break;
							} catch (MaxCapacityException e1) {
								JOptionPane.showMessageDialog(null, e1.getMessage());
							}
						}
						
					}
//				}
					displayMap();
					displayCityView(c);
			}

		}

		// END TURN IN CITY VIEW
		else if (e.getSource() == cityView.getEndTurn()) {
			model.onEndTurn();
			gameOver();
			displayMap();
			displayCityView(c);
			//System.out.println(a.getCurrentLocation());
			if(a!=null)
			{
				if(a.getCurrentLocation().equalsIgnoreCase(a.getTarget()))
				{
					displayBattleView();
				}
			}
			
		} 
		//END TURN IN BATTLE VIEW
		else if(e.getSource()==battle.getEndTurn())
		{
			model.onEndTurn();
			gameOver();
			displayMap();
			displayBattleView();
			if(target!=null)
			{
				if(target.getTurnsUnderSiege()==3)
				{
					JOptionPane.showMessageDialog(null, "Max turns under siege reached! Please choose attack or autoresolve");
				}
			}
		}
		//SETTING A TARGET CITY
		else if (e.getSource() == cityView.getTargetACity()) {
			model.targetCity(a, (String) Availablecities.getSelectedItem());
			//a.setCurrentStatus(Status.MARCHING);
		}
		//ATTACK MANUALLY
		else if(e.getSource()==battle.getAttack()) 
		{  
			for(Unit meineArmy: a.getUnits())
			{
				for(Unit u:enemy.getUnits())
				{
					if(u instanceof Archer && attackunit.getSelectedItem().equals("Archer") && meineArmy instanceof Archer && Aunits.getSelectedItem().equals("Archer"))
					{
						try {
							meineArmy.attack(u);
						} catch (FriendlyFireException e1) {
							JOptionPane.showMessageDialog(null, e1.getMessage());
						}
					}
					else if(u instanceof Archer && attackunit.getSelectedItem().equals("Archer") && meineArmy instanceof Cavalry && Aunits.getSelectedItem().equals("Cavalry"))
					{
						try {
							meineArmy.attack(u);
						} catch (FriendlyFireException e1) {
							JOptionPane.showMessageDialog(null, e1.getMessage());
						}
					}
					else if(u instanceof Archer && attackunit.getSelectedItem().equals("Archer") && meineArmy instanceof Infantry && Aunits.getSelectedItem().equals("Infantry"))
					{
						try {
							meineArmy.attack(u);
						} catch (FriendlyFireException e1) {
							JOptionPane.showMessageDialog(null, e1.getMessage());
						}
					}
					else if(u instanceof Infantry && attackunit.getSelectedItem().equals("Infantry") && meineArmy instanceof Archer && Aunits.getSelectedItem().equals("Archer"))
					{
						try {
							meineArmy.attack(u);
						} catch (FriendlyFireException e1) {
							JOptionPane.showMessageDialog(null, e1.getMessage());
						}
					}
					else if(u instanceof Infantry && attackunit.getSelectedItem().equals("Infantry") && meineArmy instanceof Infantry && Aunits.getSelectedItem().equals("Infantry"))
					{
						try {
							meineArmy.attack(u);
						} catch (FriendlyFireException e1) {
							JOptionPane.showMessageDialog(null, e1.getMessage());
						}
					}
					else if(u instanceof Infantry && attackunit.getSelectedItem().equals("Infantry") && meineArmy instanceof Cavalry && Aunits.getSelectedItem().equals("Cavalry"))
					{
						try {
							meineArmy.attack(u);
						} catch (FriendlyFireException e1) {
							JOptionPane.showMessageDialog(null, e1.getMessage());
						}
					}
					else if(u instanceof Cavalry && attackunit.getSelectedItem().equals("Cavalry") && meineArmy instanceof Archer && Aunits.getSelectedItem().equals("Archer"))
					{
						try {
							meineArmy.attack(u);
						} catch (FriendlyFireException e1) {
							JOptionPane.showMessageDialog(null, e1.getMessage());
						}
					}
					else if(u instanceof Cavalry && attackunit.getSelectedItem().equals("Cavalry") && meineArmy instanceof Cavalry && Aunits.getSelectedItem().equals("Cavalry"))
					{
						try {
							meineArmy.attack(u);
						} catch (FriendlyFireException e1) {
							JOptionPane.showMessageDialog(null, e1.getMessage());
						}
					}
					else if(u instanceof Cavalry && attackunit.getSelectedItem().equals("Cavalry") && meineArmy instanceof Infantry && Aunits.getSelectedItem().equals("Infantry"))
					{
						try {
							meineArmy.attack(u);
						} catch (FriendlyFireException e1) {
							JOptionPane.showMessageDialog(null, e1.getMessage());
						}
					}
				}
			}
			if(enemy.getUnits().isEmpty())
			{
				model.occupy(a, a.getCurrentLocation());
				gameOver();
				JOptionPane.showMessageDialog(null, "Congratulations! You've won this battle, now the city is yours");
			}
			else if(a.getUnits().isEmpty())
				JOptionPane.showMessageDialog(null, "You lost this battle :(");
		
		}

		//AUTO RESOLVE
		else if(e.getSource()==battle.getAutoResolve())
		{
			try {
				model.autoResolve(a, enemy);
			} catch (FriendlyFireException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage());
			}
			if(enemy.getUnits().isEmpty())
			{
				model.occupy(a, a.getCurrentLocation());
				gameOver();
				JOptionPane.showMessageDialog(null, "Congratulations! You've won this battle, now the city is yours");
			}
			else if(a.getUnits().isEmpty())
				JOptionPane.showMessageDialog(null, "You lost this battle :(");
		}
		
		//LAY SIEGE
		else if(e.getSource()==battle.getLaySiege())
		{
			System.out.println(a.getCurrentLocation());
			System.out.println(target.getName());
			try {
					model.getPlayer().laySiege(a, target);
			} catch (TargetNotReachedException | FriendlyCityException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage());
			}
		}
	}

	public void addUnits() {
//		for(Army army : model.getPlayer().getControlledArmies())
//		{
			for (Unit u : c.getDefendingArmy().getUnits()) {
				if (u instanceof Archer && u.getParentArmy()!=a)
					listOfUnits4Attack.addItem("Archer");
				else if (u instanceof Infantry && u.getParentArmy()!=a)
					listOfUnits4Attack.addItem("Infantry");
				else if(u instanceof Cavalry && u.getParentArmy()!=a)
					listOfUnits4Attack.addItem("Cavalry");
			}
//		}
		
	}
	//GAME OVER
	public void gameOver() {
		if (model.isGameOver() && model.getAvailableCities().size() == model.getPlayer().getControlledCities().size())
			JOptionPane.showMessageDialog(null, "CONGRATULATIONS YOU WON!!");
		else if (model.isGameOver()
				&& model.getAvailableCities().size() != model.getPlayer().getControlledCities().size())
			JOptionPane.showMessageDialog(null, "GAME OVER, YOU LOST :(");
	}
	//MAP VIEW
	public void displayMap() {
		map.setVisible(true);
		map.getPlayerInfo().setText("Player Name: " + name.getText() + "\nCurrent Turn: " + model.getCurrentTurnCount()
				+ "\nGold: " + model.getPlayer().getTreasury() + "\nFood: " + model.getPlayer().getFood());
		String type;
		String armiesInMap="";
		for (int i = 0; i < model.getPlayer().getControlledArmies().size(); i++) {
			Army m = model.getPlayer().getControlledArmies().get(i);
			Unit u = null;
			for (int j = 0; j < m.getUnits().size(); j++) {
				u = m.getUnits().get(j);
				if (u instanceof Archer)
					type = "Archer";
				else if (u instanceof Cavalry)
					type = "Cavalry";
				else
					type = "Infantry";
				armiesInMap+="\n "+m.getCurrentStatus()+" army from "+m.getCurrentLocation()+"\nUnit Type: " + type + "\nCurrent solider Count: "
					+ u.getCurrentSoldierCount()+ "\nLevel: " + u.getLevel() + "\nMax solider Count: " + u.getMaxSoldierCount();
				}
		}
		map.getMapInfo().setText(armiesInMap);
		map.revalidate();
		map.repaint();
	}
	
	//CITTY VIEW
	public void displayCityView(City c) {

		Army m = c.getDefendingArmy();
		String type;
		Unit u = null;
		String stationedarmies = "Stationed armies:";
		String defendingArmyUnits = "Units in the defending army:";
		for (int j = 0; j < m.getUnits().size(); j++) {
			u = m.getUnits().get(j);
			if (u instanceof Archer)
				type = "Archer";
			else if (u instanceof Cavalry)
				type = "Cavalry";
			else
				type = "Infantry";
			defendingArmyUnits+="\nUnit Type: " + type + "\nCurrent solider Count: "
							+ u.getCurrentSoldierCount() + "\nLevel: " + u.getLevel() + "\nMax solider Count: "
							+ u.getMaxSoldierCount();
		}
		cityView.getArmyInfo().setText(defendingArmyUnits);
		for (int i = 0; i < model.getPlayer().getControlledArmies().size(); i++) {
			Army stationed = model.getPlayer().getControlledArmies().get(i);
			if (stationed.getCurrentLocation().equalsIgnoreCase(c.getName())) {
				for (int j = 0; j < stationed.getUnits().size(); j++) {
					u = stationed.getUnits().get(j);
					if (u instanceof Archer)
						type = "Archer";
					else if (u instanceof Cavalry)
						type = "Cavalry";
					else
						type = "Infantry";
					stationedarmies += "\nUnit Type: " + type + " from " + u.getParentArmy().getCurrentLocation()
							+ "\nCurrent solider Count: " + u.getCurrentSoldierCount() + "\nLevel: " + u.getLevel()
							+ "\nMax solider Count: " + u.getMaxSoldierCount();
				}
			}
		}
		cityView.getStationedArmies().setText(stationedarmies);;
		cityView.getPlayerInfo()
				.setText("Player Name: " + name.getText() + "\nCurrent Turn: " + model.getCurrentTurnCount()
						+ "\nGold: " + model.getPlayer().getTreasury() + "\nFood: " + model.getPlayer().getFood());
		String buildingss = "";
		for (int j = 0; j < c.getEconomicalBuildings().size(); j++) {
			EconomicBuilding b = c.getEconomicalBuildings().get(j);
			if (b instanceof Farm)
				buildingss += "\nFARM, Level: " + b.getLevel() + "\n The upgrade cost of a farm is: "
						+ b.getUpgradeCost();
			else
				buildingss += "\nMARKET, Level: " + b.getLevel() + "\n The upgrade cost of a market is: "
						+ b.getUpgradeCost();
		}
		for (int j = 0; j < c.getMilitaryBuildings().size(); j++) {
			MilitaryBuilding b = c.getMilitaryBuildings().get(j);
			if (b instanceof ArcheryRange)
				buildingss += "\nARCHERY RANGE, Level: " + b.getLevel() + "\n The upgrade cost of an archery range is: "
						+ b.getUpgradeCost() + "\n The recruitment cost of a unit from the archery range is: "
						+ b.getRecruitmentCost();
			else if (b instanceof Barracks)
				buildingss += "\nBARRACKS, Level: " + b.getLevel() + "\n The upgrade cost of the barracks is: "
						+ b.getUpgradeCost() + "\n The recruitment cost of a unit from the barracks is: "
						+ b.getRecruitmentCost();
			else
				buildingss += "\nSTABLE, Level: " + b.getLevel() + "\n The upgrade cost of a stable is: "
						+ b.getUpgradeCost() + "\n The recruitment cost of a unit from the stable is: "
						+ b.getRecruitmentCost();
		}
		cityView.getCityInfo().setText(buildingss);
		cityView.setVisible(true);
		cityView.revalidate();
		cityView.repaint();
		return;

	}
	//BATTLE VIEW
	public void displayBattleView() {
		battle.setVisible(true);
		battle.getPlayerInfo()
				.setText("Player Name: " + name.getText() + "\nCurrent Turn: " + model.getCurrentTurnCount()
						+ "\nGold: " + model.getPlayer().getTreasury() + "\nFood: " + model.getPlayer().getFood());
		String armiesInformation="Enemy Army: ";
		String myArmyinbattle = "My Army:";
		Unit u = null;
		String type;
		for(City t: model.getAvailableCities())
		{
			enemy = t.getDefendingArmy();
//			if(a.getCurrentLocation().equalsIgnoreCase(t.getName()))
//			{
				for (int j = 0; j < enemy.getUnits().size(); j++) {
					u = enemy.getUnits().get(j);
					if (u instanceof Archer)
						type = "Archer";
					else if (u instanceof Cavalry)
						type = "Cavalry";
					else
						type = "Infantry";
					armiesInformation += "\nUnit Type: " + type + " from " + u.getParentArmy().getCurrentLocation()+ " Level: " + u.getLevel()
							+ "\nCurrent solider Count: " + u.getCurrentSoldierCount() 
							+ "Max solider Count: " + u.getMaxSoldierCount();
					attackunit.addItem(type);
					target = t;
				}
				for (int j = 0; j < a.getUnits().size(); j++) {
					u = a.getUnits().get(j);
					if (u instanceof Archer)
						type = "Archer";
					else if (u instanceof Cavalry)
						type = "Cavalry";
					else
						type = "Infantry";
					myArmyinbattle += "\nUnit Type: " + type + " Level: " + u.getLevel()
							+ "\nCurrent solider Count: " + u.getCurrentSoldierCount() 
							+ "Max solider Count: " + u.getMaxSoldierCount();
					Aunits.addItem(type);
				}
//			}
		}
		battle.getArmiesInfo().setText(armiesInformation);
		battle.getBattleInfo().setText(myArmyinbattle);
		battle.revalidate();
		battle.repaint();

	}

	public String[] listOfOwnedBuildings() {
		String[] list = new String[c.getEconomicalBuildings().size() + c.getMilitaryBuildings().size()];
		for (int i = 0; i < c.getEconomicalBuildings().size(); i++) {
			if (c.getEconomicalBuildings().get(i) instanceof Farm)
				list[i] = "Farm";
			else
				list[i] = "Market";
		}
		for (int i = 0; i < c.getMilitaryBuildings().size(); i++) {
			if (c.getMilitaryBuildings().get(i) instanceof ArcheryRange)
				list[i] = "ArcheryRange";
			else if (c.getMilitaryBuildings().get(i) instanceof Barracks)
				list[i] = "Barracks";
			else
				list[i] = "Stable";
		}
		return list;
	}

	public Game getModel() {
		return model;
	}

	public void setModel(Game model) {
		this.model = model;
	}

	public String getPlayerName() {
		return PlayerName;
	}

	public static void main(String[] args) throws IOException {
		new Controller();
	}

}
