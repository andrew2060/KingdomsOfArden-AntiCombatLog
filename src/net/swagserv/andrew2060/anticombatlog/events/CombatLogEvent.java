package net.swagserv.andrew2060.anticombatlog.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class CombatLogEvent extends Event {
	
	private Player attacker;
	private Player logger;
	private boolean cancelled;

	public CombatLogEvent(Player attacker, Player logger) {
		this.attacker = attacker;
		this.logger = logger;
		this.cancelled = false;
	}
	
	private static final HandlerList handlers = new HandlerList();
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	public static HandlerList getHandlerList() {
		return handlers;
	}
	/**
	 * @return the player that logged off
	 */
	public Player getWhoLogged() {
		return logger;
	}
	/**
	 * @return the player that caused the logger to be in combat
	 */
	public Player getAttacker() {
		return attacker;
	}
	public boolean isCancelled() {
		return this.cancelled;
	}
	public void setCancelled(boolean cancel) {
		this.cancelled = cancel;
	}
	

}
