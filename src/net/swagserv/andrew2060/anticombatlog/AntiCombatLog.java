package net.swagserv.andrew2060.anticombatlog;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class AntiCombatLog extends JavaPlugin{
	Logger log = Logger.getLogger("Minecraft");
	public void onEnable() {
    	getConfig().options().copyDefaults(true);
		Bukkit.getServer().getPluginManager().registerEvents(new HeroesCombatLogListener() , this);
		HeroesCombatLogListener.ess = false;
    	if(getConfig().getString("commandblock").equalsIgnoreCase("true")){
        	getServer().getPluginManager().registerEvents(new CombatCommandListener(), this); 
        	log.info("[Swagserv-AntiCombatLog] Will Block Commands While In Combat");
    	}
    	if(getConfig().getString("lwcmessage").equalsIgnoreCase("false")){
        	log.info("[Swagserv-AntiCombatLog] Will Not Block Commands While In Combat");
    	}
		if(getConfig().getString("essentialsnotify").equalsIgnoreCase("true")) {
			log.info("[Swagserv-AntiCombatLog] Will Notify PvPLogger Using Essentials Mail");
			HeroesCombatLogListener.ess = true;
		}
		if(getConfig().getString("notifyplayer").equalsIgnoreCase("false")) {
			log.info("[Swagserv-AntiCombatLog] Will not Log Combat Logs");
		}
	}
}
