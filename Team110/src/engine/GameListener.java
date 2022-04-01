package engine;

import java.io.IOException;

import exceptions.FriendlyFireException;
import units.Army;

public interface GameListener {
	public void onTargetCity(Army army, String targetName);
	public void onEndTurn();
	public void onAutoResolve(Army attacker, Army defender) throws FriendlyFireException;
	public void onIsGameOver();
	
	

}
