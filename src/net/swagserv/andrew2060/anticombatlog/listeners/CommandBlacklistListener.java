package net.swagserv.andrew2060.anticombatlog.listeners;

import java.util.List;

import net.swagserv.andrew2060.anticombatlog.AntiCombatLogPlugin;
import net.swagserv.andrew2060.anticombatlog.Util;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import com.herocraftonline.heroes.Heroes;
import com.herocraftonline.heroes.characters.Hero;

public class CommandBlacklistListener implements Listener {

	private Heroes heroes;
	private List<String> blockedCommands;

	public CommandBlacklistListener(AntiCombatLogPlugin plugin) {
		this.heroes = plugin.getHeroes();
		this.blockedCommands = plugin.getConfigManager().listCommands;
	}
	
	@EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled = true)
	public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
		Hero h = heroes.getCharacterManager().getHero(event.getPlayer());
		if(!Util.isInCombatWithPlayer(h).isInCombat()) {
			return;
		}
		Player player = event.getPlayer();
		if(player.hasPermission("combatlog.bypass.command")) {
			return;
		}
		String command = (event.getMessage() + " ");
		int firstspace = command.indexOf(" ");
		String commandLower = command.substring(0,firstspace).toLowerCase();
		if(blockedCommands.contains(commandLower)) {
			event.setCancelled(true);
			player.sendMessage(ChatColor.AQUA + "[" + ChatColor.RED + "Notice" + ChatColor.AQUA + "]: You Cannot Use this Command While In Combat!");
			return;
		} else {
			return;
		}
	}

}
