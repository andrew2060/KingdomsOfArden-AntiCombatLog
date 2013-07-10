package net.kingdomsofarden.andrew2060.anticombatlog.listeners;

import java.util.Iterator;

import mc.alk.arena.BattleArena;
import mc.alk.arena.competition.match.Match;
import mc.alk.arena.events.matches.MatchCompletedEvent;
import mc.alk.arena.objects.ArenaPlayer;
import net.kingdomsofarden.andrew2060.anticombatlog.events.CombatLogEvent;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import com.herocraftonline.heroes.Heroes;
import com.herocraftonline.heroes.characters.Hero;
import com.herocraftonline.heroes.characters.effects.CombatEffect.LeaveCombatReason;

public class ArenaListener implements Listener {

	private Heroes heroesPlugin;

	public ArenaListener(Heroes plugin) {
		this.heroesPlugin = plugin;
	}
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	//Cancels out combat states on Arena completion
	public void onMatchCompletion(MatchCompletedEvent event) {
		Match m = event.getMatch();
		Iterator<ArenaPlayer> matchPlayers = m.getPlayers().iterator();
		while(matchPlayers.hasNext()) {
			ArenaPlayer aP = matchPlayers.next();
			Hero h = heroesPlugin.getCharacterManager().getHero(aP.getPlayer());
			h.leaveCombat(LeaveCombatReason.CUSTOM);
		}
	}
	//Check if a player is in an arena when "combat logging"->if so cancel the combat log
	//This prevents arena d/cs from wiping inventory
	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onCombatLog(CombatLogEvent event) {
		Player p = event.getWhoLogged();
		ArenaPlayer aP = BattleArena.toArenaPlayer(p);
		if(BattleArena.getBAController().insideArena(aP)) {
			event.setCancelled(true);
			return;
		}
	}
}
