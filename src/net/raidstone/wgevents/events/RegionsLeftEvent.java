package net.raidstone.wgevents.events;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author weby@we-bb.com [Nicolas Glassey]
 * @version 1.0.0
 * @since 2/24/19
 */
public class RegionsLeftEvent extends Event implements Cancellable {
 
	private static final HandlerList handlers = new HandlerList();

	private boolean cancelled=false;
	
	private final UUID uuid;
	private final Set<ProtectedRegion> regions;
	
	public RegionsLeftEvent(UUID playerUUID, @Nullable Set<ProtectedRegion> regions)
	{
		this.uuid = playerUUID;
		this.regions = regions==null?new HashSet<>():regions;
	}

	@Contract (pure = true)
	public static HandlerList getHandlerList() {
		return handlers;
	}

	public HandlerList getHandlers() {
		return handlers;
	}
	
	@NotNull
	public UUID getUUID() {
		return uuid;
	}

	@Nullable
	public Player getPlayer() {
		return Bukkit.getPlayer(uuid);
	}
	
	@NotNull
	public Set<ProtectedRegion> getRegions() {
		return regions;
	}
	
	@Override
	public boolean isCancelled() {
		return this.cancelled;
	}
	
	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled=cancelled;
	}
}
