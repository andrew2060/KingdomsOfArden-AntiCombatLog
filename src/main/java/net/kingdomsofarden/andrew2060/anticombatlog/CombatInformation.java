package net.kingdomsofarden.andrew2060.anticombatlog;

import org.bukkit.entity.Player;

public class CombatInformation {
	private boolean inCombat;
	private Player lastCombatant;
	public CombatInformation(Player p, Boolean inCombat) {
		this.setInCombat(inCombat);
		this.setLastCombatant(p);
	}
	/**
	 * @return whether a player is in Combat
	 */
	public boolean isInCombat() {
		return inCombat;
	}
	/**
	 * @param inCombat set whether a player is in Combat
	 */
	public void setInCombat(boolean inCombat) {
		this.inCombat = inCombat;
	}
	public Player getLastCombatant() {
		return lastCombatant;
	}
	private void setLastCombatant(Player lastCombatant) {
		this.lastCombatant = lastCombatant;
	}

}
