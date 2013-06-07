package net.swagserv.andrew2060.anticombatlog.listeners;

import java.util.Iterator;

import mc.alk.arena.competition.match.Match;
import mc.alk.arena.events.matches.MatchCompletedEvent;
import mc.alk.arena.objects.ArenaPlayer;

import org.bukkit.event.Listener;

import com.herocraftonline.heroes.Heroes;
import com.herocraftonline.heroes.characters.Hero;

public class ArenaListener implements Listener {

	private Heroes heroesPlugin;

	public ArenaListener(Heroes plugin) {
		this.heroesPlugin = plugin;
	}
	//Cancels out combat states on Arena completion
	public void onMatchCompletion(MatchCompletedEvent event) {
		Match m = event.getMatch();
		Iterator<ArenaPlayer> matchPlayers = m.getPlayers().iterator();
		while(matchPlayers.hasNext()) {
			ArenaPlayer aP = matchPlayers.next();
			Hero h = heroesPlugin.getCharacterManager().getHero(aP.getPlayer());
		}
	}

}
