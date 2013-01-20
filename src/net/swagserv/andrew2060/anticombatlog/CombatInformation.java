package net.swagserv.andrew2060.anticombatlog;

import org.bukkit.entity.Player;

public class CombatInformation {
	boolean inCombat;
	Player lastCombatant;
	public CombatInformation(Player p, Boolean inCombat) {
		this.inCombat = inCombat;
		this.lastCombatant = p;
	}

}
