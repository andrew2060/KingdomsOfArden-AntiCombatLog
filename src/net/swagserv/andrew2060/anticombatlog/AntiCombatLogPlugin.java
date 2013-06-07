package net.swagserv.andrew2060.anticombatlog;

import java.util.logging.Level;
import java.util.logging.Logger;

import mc.alk.arena.BattleArena;
import net.swagserv.andrew2060.anticombatlog.listeners.ArenaListener;
import net.swagserv.andrew2060.anticombatlog.listeners.CommandBlacklistListener;
import net.swagserv.andrew2060.anticombatlog.listeners.CommandWhitelistListener;
import net.swagserv.andrew2060.anticombatlog.listeners.CombatLogListener;
import net.swagserv.andrew2060.anticombatlog.listeners.TeleportListener;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.herocraftonline.heroes.Heroes;

public class AntiCombatLogPlugin extends JavaPlugin{ 
	private BattleArena battleArena;
	Logger logger;
	private Heroes heroes;
	private ConfigManager config;
	public void onEnable() {
		getConfig().options().copyDefaults(true);
		saveConfig();
		logger = this.getLogger();
		this.heroes = (Heroes) Bukkit.getPluginManager().getPlugin("Heroes");
		if(getHeroes() == null) {
			logger.log(Level.SEVERE, "Required heroes dependency not Found!");
			Bukkit.getPluginManager().disablePlugin(this);
			return;
		}
		this.config = new ConfigManager(this);
		
		if(config.blockCommands) {
			if(config.blockCommandMode == 1) {
				Bukkit.getServer().getPluginManager().registerEvents(new CommandBlacklistListener(this) , this);
				logger.log(Level.INFO, "Blocking Commands in Blacklist Mode");
			} else {
				Bukkit.getServer().getPluginManager().registerEvents(new CommandWhitelistListener(this) , this);
				logger.log(Level.INFO, "Blocking Commands in Whitelist Mode");
			}
		}
		if(config.blockPluginTeleports || config.blockCommandTeleports) {
			Bukkit.getServer().getPluginManager().registerEvents(new TeleportListener(this, config.blockPluginTeleports,config.blockCommandTeleports) , this);
			logger.log(Level.INFO, "Blocking Teleports");
		}
		
		/*
		 * Not a config option, this is needed for both to coexist peacefully
		 */
		this.battleArena = (BattleArena) Bukkit.getPluginManager().getPlugin("BattleArena");
		if(!(battleArena == null)) {
			logger.log(Level.INFO, "Found and Hooking into BattleArena");
			Bukkit.getServer().getPluginManager().registerEvents(new ArenaListener(this.getHeroes()), this);
		}
		
		//Register Main Plugin Listener
		Bukkit.getServer().getPluginManager().registerEvents(new CombatLogListener(getHeroes(), this) , this);
		

		
	}
	
	public ConfigManager getConfigManager() {
		return this.config;
	}
	
	@Override 
	public void onDisable() {
		
	}

	public Heroes getHeroes() {
		return heroes;
	}

}
