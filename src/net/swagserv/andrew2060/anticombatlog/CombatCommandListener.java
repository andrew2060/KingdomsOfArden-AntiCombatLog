package net.swagserv.andrew2060.anticombatlog;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import com.herocraftonline.heroes.Heroes;
import com.herocraftonline.heroes.characters.Hero;

public class CombatCommandListener implements Listener {
	private Heroes heroes = (Heroes)Bukkit.getServer().getPluginManager().getPlugin("Heroes");
	@EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled = true)
	public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
		Hero h = heroes.getCharacterManager().getHero(event.getPlayer());
		if(!Util.isInCombatWithPlayer(h).inCombat) {
			return;
		}
		if (event.getMessage().toUpperCase().contains("SKILL")
				|| event.getMessage().toUpperCase().contains("BIND")
				|| event.getMessage().toUpperCase().contains("HP")
				|| event.getMessage().toUpperCase().contains("CD")
				|| event.getMessage().toUpperCase().contains("ENEMY")
				|| event.getMessage().toUpperCase().contains("HERO")){
			return; 
		}
		event.setCancelled(true);
		Player player = event.getPlayer();
		player.sendMessage(ChatColor.AQUA + "[" + ChatColor.RED + "Notice" + ChatColor.AQUA + "]: You Cannot Use Commands While In Combat!");
	}
	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onFactionCommand(AsyncPlayerChatEvent event) { 
		if(event.getMessage().toUpperCase().contains("F HOME") || event.getMessage().toUpperCase().contains("F JAIL") || event.getMessage().toUpperCase().contains("F WARP")) {
			Hero h = heroes.getCharacterManager().getHero(event.getPlayer());
			if(!Util.isInCombatWithPlayer(h).inCombat) {
				return;
			}
			event.setCancelled(true);
		}
	}
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerTeleport(PlayerTeleportEvent event) {
		Player p = event.getPlayer();
		Hero h = heroes.getCharacterManager().getHero(p);
		if(!Util.isInCombatWithPlayer(h).inCombat) {
			return;
		}
		if(!(event.getCause().equals(TeleportCause.COMMAND) || event.getCause().equals(TeleportCause.PLUGIN))) {
			return;
		}
		event.setCancelled(true);
		p.sendMessage(ChatColor.GRAY + "Teleport cancelled due to being in combat!");
	}
	
	
}
