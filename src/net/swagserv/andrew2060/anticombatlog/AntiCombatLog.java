package net.swagserv.andrew2060.anticombatlog;

import java.util.logging.Level;
import java.util.logging.Logger;

import mc.alk.arena.BattleArena;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class AntiCombatLog extends JavaPlugin{ 
	BattleArena battleArena;
	Logger log = this.getLogger();
	public void onEnable() {
    	getConfig().options().copyDefaults(true);
		Bukkit.getServer().getPluginManager().registerEvents(new HeroesCombatLogListener() , this);
		HeroesCombatLogListener.ess = false;
    	if(getConfig().getString("commandblock").equalsIgnoreCase("true")){
        	getServer().getPluginManager().registerEvents(new CombatCommandListener(), this); 
        	log.log(Level.INFO, "Will Block Commands While In Combat");
    	}
    	if(getConfig().getString("commandblock").equalsIgnoreCase("false")){
        	log.log(Level.INFO, "Will Not Block Commands While In Combat"); 
    	}
		if(getConfig().getString("essentialsnotify").equalsIgnoreCase("true")) {
			log.log(Level.INFO, "Will use Essentials Mail");
			HeroesCombatLogListener.ess = true;
		}
		if(getConfig().getString("essentialsnotify").equalsIgnoreCase("false")) {
			log.log(Level.INFO, "Will not use Essentials Mail");
		}
		
		
	}
}
