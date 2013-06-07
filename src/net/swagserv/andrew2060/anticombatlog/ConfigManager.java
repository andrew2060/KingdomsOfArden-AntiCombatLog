package net.swagserv.andrew2060.anticombatlog;

import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {
	private FileConfiguration config;
	
	public final boolean blockCommands;	//block.command.enabled
	public final int blockCommandMode;	//0 for whitelist, 1 for blacklist
	public final List<String> listCommands;
	
	public final boolean blockCommandTeleports;
	public final boolean blockPluginTeleports;
	
	public final boolean essentialsIntegration;
	public final String essIntegrationMessage;

	public final boolean economyEnabled;
	public final double economyFlatValue;
	public final double economyPercentValue; 
	
	public final boolean battleTrackerIntegration;
	public final int eloLoss;

	
	public ConfigManager(AntiCombatLogPlugin plugin) {
		this.config = plugin.getConfig();
		this.blockCommands = config.getBoolean("block.command.enabled");
		if(blockCommands) {
			String mode = config.getString("block.command.mode").toLowerCase();
			if(mode.equals("blacklist")) {
				this.blockCommandMode = 1;
			} else {
				this.blockCommandMode = 0;
			}
			if(blockCommandMode == 1) {
				listCommands = config.getStringList("block.command.blacklist");
			} else {
				listCommands = config.getStringList("block.command.whitelist");
			}
		} else {
			this.blockCommandMode = -1;
			this.listCommands = null;
		}
		this.blockCommandTeleports = config.getBoolean("block.teleport.blockCommandTeleports");
		this.blockPluginTeleports = config.getBoolean("block.teleport.blockPluginTeleports");
		
		this.economyEnabled = config.getBoolean("economy.enabled");
		if(economyEnabled) {
			this.economyFlatValue = config.getDouble("economy.flatvalue");
			this.economyPercentValue = config.getDouble("economy.percentbalance");
		} else {
			this.economyFlatValue = 0;
			this.economyPercentValue = 0;
		}
		
		this.essentialsIntegration = config.getBoolean("integration.essentials.enabled");
		if(essentialsIntegration) {
			this.essIntegrationMessage = config.getString("integration.essentials.message");
		} else {
			this.essIntegrationMessage = "";
		}
		this.battleTrackerIntegration = config.getBoolean("integration.battletracker.enabled",false);
		if(battleTrackerIntegration) {
			this.eloLoss = config.getInt("integration.battletracker.lossPerCombatLog");
		} else {
			this.eloLoss = 0;
		}
	}

}
