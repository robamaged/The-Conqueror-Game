package engine;

import buildings.Building;
import exceptions.BuildingInCoolDownException;
import exceptions.FriendlyCityException;
import exceptions.MaxLevelException;
import exceptions.MaxRecruitedException;
import exceptions.NotEnoughGoldException;
import exceptions.TargetNotReachedException;
import units.Army;
import units.Unit;

public interface PlayerListener {
	public void onBuild(String type, City c) throws NotEnoughGoldException;
	public void onRecruitUnit(String type,String cityName) throws BuildingInCoolDownException, MaxRecruitedException, NotEnoughGoldException;
	public void onUpgradeBuilding(Building b) throws NotEnoughGoldException, BuildingInCoolDownException, MaxLevelException;
	public void onInitiateArmy(City c, Unit u);
	public void onLaySiege(Army Army, City c) throws TargetNotReachedException, FriendlyCityException;

}
