package net.swagserv.andrew2060.anticombatlog;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import com.herocraftonline.heroes.Heroes;
import com.herocraftonline.heroes.characters.Hero;

public class CombatCommandListener implements Listener {
	private Heroes heroes = (Heroes)Bukkit.getServer().getPluginManager().getPlugin("Heroes");
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
		Hero h = heroes.getCharacterManager().getHero(event.getPlayer());
		LivingEntity targetentity = h.getCombatEffect().getLastCombatant();

		if(h.isInCombat() == false) {
			return; 
		}
		else {
			if(targetentity instanceof Monster) {
				return;
			}
			else if (event.getMessage().contains("skill") || event.getMessage().contains("bind")|| event.getMessage().contains("Skill")|| event.getMessage().contains("Bind")){
				return;
			}
			else {
				event.setCancelled(true);
				Player player = event.getPlayer();
				player.sendMessage(ChatColor.AQUA + "[" + ChatColor.RED + "Notice" + ChatColor.AQUA + "]: You Cannot Use Commands While In Combat!");
			}
		}
	}
}
