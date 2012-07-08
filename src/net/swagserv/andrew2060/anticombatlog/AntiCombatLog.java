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
    	if(getConfig().getString("commandblock").equalsIgnoreCase("false")){
        	log.info("[Swagserv-AntiCombatLog] Will Not Block Commands While In Combat"); 
    	}
		if(getConfig().getString("essentialsnotify").equalsIgnoreCase("true")) {
			log.info("[Swagserv-AntiCombatLog] Will use Essentials Mail");
			HeroesCombatLogListener.ess = true;
		}
		if(getConfig().getString("essentialsnotify").equalsIgnoreCase("false")) {
			log.info("[Swagserv-AntiCombatLog] Will not use Essentials Mail");
		}
	}
}
