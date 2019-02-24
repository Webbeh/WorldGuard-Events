package net.raidstone.wgevents;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.util.Location;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import net.raidstone.wgevents.events.RegionEnteredEvent;
import net.raidstone.wgevents.events.RegionLeftEvent;
import net.raidstone.wgevents.events.RegionsEnteredEvent;
import net.raidstone.wgevents.events.RegionsLeftEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

import java.util.*;

/**
 * @author weby@we-bb.com [Nicolas Glassey]
 * @version 1.0.0
 * @since 2/24/19
 */
public class Listeners implements Listener {
    private RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
    
    private HashMap<UUID, Set<ProtectedRegion>> playerRegions = new HashMap<>();
  
    private Set<ProtectedRegion> getRegions(UUID u)
    {
        Player p = Bukkit.getPlayer(u);
       
        //If player is offline
        if(p==null)
            return new HashSet<>();
      
        Location l = BukkitAdapter.adapt(p.getLocation());
        World w = BukkitAdapter.adapt(p.getLocation().getWorld());
        
        RegionQuery q = WorldGuard.getInstance().getPlatform().getRegionContainer().createQuery();
        ApplicableRegionSet ars = q.getApplicableRegions(l);
        return ars.getRegions();
    }
    
    private int count=0;
    private void changeRegions(UUID u, Set<ProtectedRegion> actual)
    {
        
        playerRegions.putIfAbsent(u, new HashSet<>());
        //If the sets contain the same info, ignore.
        int previousSize = playerRegions.get(u).size();
        int actualSize = actual.size();
        
        if(actual.size()==playerRegions.get(u).size() && actual.containsAll(playerRegions.get(u)))
            return;
       
        Set<ProtectedRegion> previous = playerRegions.get(u);
       
        boolean joined = false;
        boolean left = false;
       
        //Check if we joined and/or left a region.
        if(actualSize==previousSize)
        {
            joined=true;
            left=true;
        }
        else if (actualSize<previousSize)
            left=true;
        else
            joined=true;
        
        if(left) {
            //If we left a region
            Set<ProtectedRegion> leftregions = new HashSet<>(previous);
            leftregions.removeAll(actual);
            RegionsLeftEvent rle = new RegionsLeftEvent(u, leftregions);
            Bukkit.getPluginManager().callEvent(rle);
            for(ProtectedRegion region : leftregions) {
                RegionLeftEvent re = new RegionLeftEvent(u, region);
                Bukkit.getPluginManager().callEvent(re);
            }
        }
        
        if(joined) {
            //If we entered a region
            Set<ProtectedRegion> enteredregions = new HashSet<>(actual);
            enteredregions.removeAll(previous);
            RegionsEnteredEvent ree = new RegionsEnteredEvent(u, enteredregions);
            Bukkit.getPluginManager().callEvent(ree);
            for(ProtectedRegion region : enteredregions) {
                RegionEnteredEvent re = new RegionEnteredEvent(u, region);
                Bukkit.getPluginManager().callEvent(re);
            }
        }
    
        //Apply the changes
        playerRegions.put(u, actual);
        StringBuilder s = new StringBuilder("Applicable : ");
        for(ProtectedRegion pr : playerRegions.get(u))
            s.append(pr.getId()).append(", ");
        Bukkit.broadcastMessage(s.toString());
    }
    
    private void quit(UUID u)
    {
        Set<ProtectedRegion> empty = new HashSet<>();
        changeRegions(u, empty);
    }
    
    private void changeWorld(UUID u)
    {
        changeRegions(u, getRegions(u));
    }
    
    
    @EventHandler
    public void onJoin(PlayerJoinEvent event)
    {
        changeRegions(event.getPlayer().getUniqueId(), getRegions(event.getPlayer().getUniqueId()));
    }
    
    @EventHandler
    public void onQuit(PlayerQuitEvent event)
    {
        quit(event.getPlayer().getUniqueId());
    }
    
    @EventHandler
    public void onKick(PlayerKickEvent event)
    {
        quit(event.getPlayer().getUniqueId());
    }
    

    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent event)
    {
        changeWorld(event.getPlayer().getUniqueId());
    }
    
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event)
    {
        UUID uuid = event.getPlayer().getUniqueId();
        /*
        Location to = BukkitAdapter.adapt(event.getTo());
        World tow = BukkitAdapter.adapt(event.getTo().getWorld());
        Location from = BukkitAdapter.adapt(event.getFrom());
        World fromw = BukkitAdapter.adapt(event.getFrom().getWorld());
       
        
        //World change, clear regions the player's in.
        if(!tow.equals(fromw))
        {
            changeWorld(uuid);
            return;
        }
         */
        
        changeRegions(uuid, getRegions(uuid));
    }
    
    Set<String> getPlayerRegionsNames(UUID u) {
        Set<String> reg = new HashSet<>();
        for (ProtectedRegion pr : getPlayerRegions(u))
            reg.add(pr.getId());
    
        return reg;
    }
    
    Set<ProtectedRegion> getPlayerRegions(UUID u) {
        if(!playerRegions.containsKey(u))
            return new HashSet<>();
        
        Set<ProtectedRegion> regions = playerRegions.get(u);
        if(regions==null) return new HashSet<>();
        
        return regions;
    }
}
