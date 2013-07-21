package net.kingdomsofarden.andrew2060.anticombatlog.listeners;

import java.util.List;

import net.kingdomsofarden.andrew2060.anticombatlog.AntiCombatLogPlugin;
import net.kingdomsofarden.andrew2060.anticombatlog.Util;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import com.herocraftonline.heroes.Heroes;
import com.herocraftonline.heroes.characters.Hero;

public class TeleportListener implements Listener {

	private boolean blockCommandTele;
	private boolean blockPluginTele;
	private Heroes heroes;
	private List<World> ignoredWorlds;

	public TeleportListener(AntiCombatLogPlugin plugin, boolean pluginTele,boolean commandTele) {
		this.blockPluginTele = pluginTele;
		this.blockCommandTele = commandTele;
		this.ignoredWorlds = plugin.getConfigManager().ignoredWorldsTPOutgoing;
		this.heroes = plugin.getHeroes();
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerTeleport(PlayerTeleportEvent event) {
		Player p = event.getPlayer();
		if(AntiCombatLogPlugin.permission.has(p, "combatlog.bypass.teleport")) {
			return;
		}
		if(ignoredWorlds.contains(event.getFrom().getWorld())) {
			return;
		}
		Hero h = heroes.getCharacterManager().getHero(p);
		if(!Util.isInCombatWithPlayer(h).isInCombat()) {
			return;
		}

		TeleportCause cause = event.getCause();
		if(blockCommandTele) {
			if(cause.equals(TeleportCause.COMMAND)) {
				event.setCancelled(true);
				p.sendMessage(ChatColor.AQUA + "[" + ChatColor.RED + "Notice" + ChatColor.AQUA + "]: Teleport was Cancelled Due to Being In Combat!");
				return;
			}
			
		}
		if(blockPluginTele) {
			if(cause.equals(TeleportCause.PLUGIN)) {
				event.setCancelled(true);
				p.sendMessage(ChatColor.AQUA + "[" + ChatColor.RED + "Notice" + ChatColor.AQUA + "]: Teleport was Cancelled Due to Being In Combat!");
				return;
			}
		}
	}
}
