package net.swagserv.andrew2060.anticombatlog.listeners;

import net.swagserv.andrew2060.anticombatlog.Util;

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
		if(!Util.isInCombatWithPlayer(h).isInCombat()) {
			return;
		}
		Player player = event.getPlayer();
		if(player.hasPermission("anticombatlog.commandbypass")) {
			return;
		}
		String commandUpper = event.getMessage().toUpperCase();
		if (commandUpper.contains("SKILL")
				|| commandUpper.contains("BIND")
				|| commandUpper.contains("HP")
				|| commandUpper.contains("CD")
				|| commandUpper.contains("ENEMY")
				|| commandUpper.contains("HERO")
				|| commandUpper.contains("SKILLS")){
			return; 
		}
		event.setCancelled(true);
		player.sendMessage(ChatColor.AQUA + "[" + ChatColor.RED + "Notice" + ChatColor.AQUA + "]: You Cannot Use Commands While In Combat!");
	}
	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onFactionCommand(AsyncPlayerChatEvent event) { 
		String commandUpper = event.getMessage().toUpperCase();
		if(commandUpper.contains("F HOME") || commandUpper.contains("F JAIL") || commandUpper.contains("F WARP")) {
			Hero h = heroes.getCharacterManager().getHero(event.getPlayer());
			if(!Util.isInCombatWithPlayer(h).isInCombat()) {
				return;
			}
			event.setCancelled(true);
		}
	}
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerTeleport(PlayerTeleportEvent event) {
		Player p = event.getPlayer();
		if(p.hasPermission("anticombatlog.tpbypass")) {
			return;
		}
		Hero h = heroes.getCharacterManager().getHero(p);
		if(!Util.isInCombatWithPlayer(h).isInCombat()) {
			return;
		}
		if(!(event.getCause().equals(TeleportCause.COMMAND) || event.getCause().equals(TeleportCause.PLUGIN))) {
			return;
		}
		event.setCancelled(true);
		p.sendMessage(ChatColor.GRAY + "Teleport cancelled due to being in combat!");
	}
	
	
}
