package net.swagserv.andrew2060.anticombatlog;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.herocraftonline.heroes.Heroes;
import com.herocraftonline.heroes.characters.Hero;
public class HeroesCombatLogListener implements Listener{
	private Heroes heroes = (Heroes)Bukkit.
			getServer().
			getPluginManager().
			getPlugin("Heroes");

	@EventHandler(priority=EventPriority.NORMAL)
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player p = event.getPlayer();
		Hero h = heroes.getCharacterManager().getHero(event.getPlayer());
		boolean combatcheck = h.isInCombat();
		if (combatcheck == false) {
			return;
		}
		else {
			p.damage(1000);
			p.setHealth(0);
			h.setHealth(0);
			h.syncHealth();
			h.syncExperience();		
			Bukkit.getServer().broadcastMessage(ChatColor.AQUA + "[" + ChatColor.RED + "NOTICE" + ChatColor.AQUA + "]: " + p.getName() + " just CombatLogged and dropped their items!");
			}
		
	}

}
