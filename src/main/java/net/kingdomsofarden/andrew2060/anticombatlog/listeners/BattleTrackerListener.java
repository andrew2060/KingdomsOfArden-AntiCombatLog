package net.kingdomsofarden.andrew2060.anticombatlog.listeners;

import mc.alk.tracker.Tracker;
import mc.alk.tracker.objects.PlayerStat;
import net.kingdomsofarden.andrew2060.anticombatlog.AntiCombatLogPlugin;
import net.kingdomsofarden.andrew2060.anticombatlog.ConfigManager;
import net.kingdomsofarden.andrew2060.anticombatlog.events.CombatLogEvent;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class BattleTrackerListener implements Listener {
	private ConfigManager config;
	public BattleTrackerListener(AntiCombatLogPlugin plugin) {
		this.config = plugin.getConfigManager();
	}
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onCombatLog(CombatLogEvent event) {
		PlayerStat loggerStats = Tracker.getInterface(config.trackerName).getPlayerRecord(event.getWhoLogged());
		loggerStats.setLosses(loggerStats.getLosses() + config.raiseLossRecord);
		loggerStats.setRating(loggerStats.getRating() - config.eloLoss);
	}
	
}
