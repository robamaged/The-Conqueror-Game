package units;

import exceptions.FriendlyFireException;

public interface UnitListener {
	public void onAttack(Unit meineArmy , Unit u) throws FriendlyFireException ;

}
