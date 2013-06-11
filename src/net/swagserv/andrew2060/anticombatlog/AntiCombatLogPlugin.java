package net.swagserv.andrew2060.anticombatlog;

import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import mc.alk.arena.BattleArena;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import net.swagserv.andrew2060.anticombatlog.listeners.ArenaListener;
import net.swagserv.andrew2060.anticombatlog.listeners.BattleTrackerListener;
import net.swagserv.andrew2060.anticombatlog.listeners.CommandBlacklistListener;
import net.swagserv.andrew2060.anticombatlog.listeners.CommandWhitelistListener;
import net.swagserv.andrew2060.anticombatlog.listeners.CombatLogListener;
import net.swagserv.andrew2060.anticombatlog.listeners.TeleportListener;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.herocraftonline.heroes.Heroes;

public class AntiCombatLogPlugin extends JavaPlugin{ 
	private BattleArena battleArena;
	Logger logger;
	private Heroes heroes;
	private ConfigManager config;
	public static Permission permission = null;
    public static Economy economy = null;
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
		//Block Command Listeners
		if(config.blockCommands) {
			if(config.blockCommandMode == 1) {
				Bukkit.getServer().getPluginManager().registerEvents(new CommandBlacklistListener(this) , this);
				logger.log(Level.INFO, "Blocking Commands in Blacklist Mode");
				logger.log(Level.INFO, "Commands Blacklisted: ");
				Iterator<String> commands = config.listCommands.iterator();
				while(commands.hasNext()) {
					logger.log(Level.INFO, "- " + commands.next());
				}
			} else {
				Bukkit.getServer().getPluginManager().registerEvents(new CommandWhitelistListener(this) , this);
				logger.log(Level.INFO, "Blocking Commands in Whitelist Mode");
				logger.log(Level.INFO, "Commands Whitelisted: ");
				Iterator<String> commands = config.listCommands.iterator();
				while(commands.hasNext()) {
					logger.log(Level.INFO, "- " + commands.next());
				}
			}
		}
		//Block Teleports Listener
		if(config.blockPluginTeleports || config.blockCommandTeleports) {
			Bukkit.getServer().getPluginManager().registerEvents(new TeleportListener(this, config.blockPluginTeleports,config.blockCommandTeleports) , this);
			logger.log(Level.INFO, "Blocking Teleports");
			if(!config.ignoredWorldsTPOutgoing.isEmpty()) {
				logger.log(Level.INFO,"Ignoring Teleports originating from these worlds:");
				Iterator<World> worlds = config.ignoredWorldsTPOutgoing.iterator();
				while(worlds.hasNext()) {
					logger.log(Level.INFO, "- " + worlds.next());
				}

			}
		}
		//BattleTracker Ranking Penalty Listener
		if(config.battleTrackerIntegration) {
			Bukkit.getServer().getPluginManager().registerEvents(new BattleTrackerListener(this), this);
			logger.log(Level.INFO, "BattleTracker Integration Enabled");
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
		
		//Register Vault
		setupPermissions();
		setupEconomy();
		
	}
	private boolean setupPermissions()	{
		RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
        if (permissionProvider != null) {
            permission = permissionProvider.getProvider();
        }
        return (permission != null);
    }

	private boolean setupEconomy()	{
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }

        return (economy != null);
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
