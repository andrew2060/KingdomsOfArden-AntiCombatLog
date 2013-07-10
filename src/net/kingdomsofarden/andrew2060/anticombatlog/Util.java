package net.kingdomsofarden.andrew2060.anticombatlog;

import java.util.Iterator;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import com.herocraftonline.heroes.characters.Hero;

public class Util {
	public static CombatInformation isInCombatWithPlayer(Hero h) {
		if(!h.isInCombat()) {
			return (new CombatInformation(null, false));
		}
		Iterator<LivingEntity> combatants = h.getCombatants().keySet().iterator();
		while(combatants.hasNext()) {
			LivingEntity lE = combatants.next();
			if(lE instanceof Player) {
				return (new CombatInformation((Player)lE, true));
			}
			continue;
		}
		return (new CombatInformation(null, false));		
	}
}
