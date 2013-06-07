package net.swagserv.andrew2060.anticombatlog;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.earth2me.essentials.Essentials;
import com.herocraftonline.heroes.Heroes;
import com.herocraftonline.heroes.characters.Hero;
import com.herocraftonline.heroes.characters.effects.CombatEffect.LeaveCombatReason;
public class HeroesCombatLogListener implements Listener{
	public static boolean ess;
	AntiCombatLog plugin;
	private Heroes heroes = (Heroes)Bukkit.getServer().getPluginManager().getPlugin("Heroes");
	private Essentials essentials = (Essentials)Bukkit.getServer().getPluginManager().getPlugin("Essentials");
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onPlayerKick(PlayerKickEvent event) {
		Hero h = heroes.getCharacterManager().getHero(event.getPlayer());
		if(h.isInCombat() == true) {
			h.leaveCombat(LeaveCombatReason.LOGOUT);
			return;
		} 
	}
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onPlayerDeath(PlayerDeathEvent event) {
		Hero h = heroes.getCharacterManager().getHero(event.getEntity());
		if(h.isInCombat()) {
			h.leaveCombat(LeaveCombatReason.DEATH);
			return;
		} 
	}

	@EventHandler(priority=EventPriority.NORMAL)
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player p = event.getPlayer();
		Hero h = heroes.getCharacterManager().getHero(event.getPlayer());
		CombatInformation cI = Util.isInCombatWithPlayer(h);
		if(!cI.inCombat) {
			return;
		}
		
		if(p.getHealth() != 0) {
			String target = cI.lastCombatant.getName();
			if(cI.lastCombatant == p) {
				h.leaveCombat(LeaveCombatReason.LOGOUT);
				return;
			}
			p.setHealth(0);
			Bukkit.getServer().broadcastMessage(ChatColor.AQUA + "[" + ChatColor.RED + "NOTICE" + ChatColor.AQUA + "]: " + p.getName() + " just CombatLogged against " + target + " and dropped their items!");
			if(ess == true) {
				essentials.getUser(p).addMail("You were automatically killed for pvp logging against " + target + "!");
				return;
			}
			return;
		}
		h.leaveCombat(LeaveCombatReason.LOGOUT);
	}
}

