package net.swagserv.andrew2060.anticombatlog;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class AntiCombatLog extends JavaPlugin{


	public void onEnable() {
		Bukkit.getServer().getPluginManager().registerEvents(new HeroesCombatLogListener(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new CombatCommandListener() , this);
	}
}
