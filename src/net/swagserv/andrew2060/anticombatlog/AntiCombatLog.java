package net.swagserv.andrew2060.anticombatlog;

import java.util.logging.Level;
import java.util.logging.Logger;

import mc.alk.arena.BattleArena;
import net.swagserv.andrew2060.anticombatlog.listeners.ArenaListener;
import net.swagserv.andrew2060.anticombatlog.listeners.CombatCommandListener;
import net.swagserv.andrew2060.anticombatlog.listeners.HeroesCombatLogListener;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.herocraftonline.heroes.Heroes;

public class AntiCombatLog extends JavaPlugin{ 
	BattleArena battleArena;
	Logger logger;
	Heroes heroes;
	public void onEnable() {
    	getConfig().options().copyDefaults(true);
		Bukkit.getServer().getPluginManager().registerEvents(new HeroesCombatLogListener() , this);
		logger = this.getLogger();
		heroes = (Heroes) Bukkit.getPluginManager().getPlugin("Heroes");
		if(heroes == null) {
			logger.log(Level.SEVERE, "Required heroes dependency not Found!");
			Bukkit.getPluginManager().disablePlugin(this);
			return;
		}
		HeroesCombatLogListener.ess = false;
    	if(getConfig().getString("commandblock").equalsIgnoreCase("true")){
        	getServer().getPluginManager().registerEvents(new CombatCommandListener(), this); 
        	logger.log(Level.INFO, "Will Block Commands While In Combat");
    	}
    	if(getConfig().getString("commandblock").equalsIgnoreCase("false")){
        	logger.log(Level.INFO, "Will Not Block Commands While In Combat"); 
    	}
		if(getConfig().getString("essentialsnotify").equalsIgnoreCase("true")) {
			logger.log(Level.INFO, "Will use Essentials Mail");
			HeroesCombatLogListener.ess = true;
		}
		if(getConfig().getString("essentialsnotify").equalsIgnoreCase("false")) {
			logger.log(Level.INFO, "Will not use Essentials Mail");
		}
		this.battleArena = (BattleArena) Bukkit.getPluginManager().getPlugin("BattleArena");
		if(!(battleArena == null)) {
			logger.log(Level.INFO, "Found and Hooking into BattleArena");
			Bukkit.getServer().getPluginManager().registerEvents(new ArenaListener(this.heroes), this);
		}
		
	}
}
