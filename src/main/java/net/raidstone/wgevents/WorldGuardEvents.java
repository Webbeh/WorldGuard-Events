package net.raidstone.wgevents;

import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author weby@we-bb.com [Nicolas Glassey]
 * @since 2/24/19
 */
public class WorldGuardEvents extends JavaPlugin implements Listener {
//    Listeners listeners = null;
    public void onEnable() {
        Plugin p = Bukkit.getPluginManager().getPlugin("WorldGuard");
        if(p==null)
        {
            Bukkit.getLogger().severe("[WorldGuardEvents] WorldGuard wasn't found. Disabling WorldGuardEvents.");
            return;
        }
        String version = p.getDescription().getVersion();
        
        if(version.isEmpty()) {
            Bukkit.getLogger().severe("[WorldGuardEvents] WorldGuard's version not detected. Are you sure it's installed properly ?");
            Bukkit.getLogger().severe("[WorldGuardEvents] Disabling WorldGuardEvents.");
            return;
        }
        
        if(!version.startsWith("7.0."))
        {
            Bukkit.getLogger().warning("[WorldGuardEvents] Detected WorldGuard version \"" + version + "\".");
            Bukkit.getLogger().warning("[WorldGuardEvents] This plugin is meant to work with WorldGuard version \"7.0.0-beta-03;e51a220\" or higher,");
            Bukkit.getLogger().warning("[WorldGuardEvents] and may not work properly with a lower version.");
            Bukkit.getLogger().warning("[WorldGuardEvents] Please update WorldGuard if your version is below \"7.0.0-beta-03;e51a220\".");
        }
//        listeners = new Listeners();
//        Entry.setListeners(listeners);
//        Bukkit.getPluginManager().registerEvents(listeners, this);
        if(!WorldGuard.getInstance().getPlatform().getSessionManager().registerHandler(Entry.factory, null)) {
            Bukkit.getLogger().severe("[WorldGuardEvents] Could not register the entry handler !");
            Bukkit.getLogger().severe("[WorldGuardEvents] Please report this error. The plugin will now be disabled.");
        }
    }
   
    /**
     * Gets the regions a player is currently in.
     * @param uuid UUID of the player in question.
     * @return Set of WorldGuard protected regions that the player is currently in.
     */
    private Set<ProtectedRegion> getRegions(UUID uuid)
    {
        return new HashSet<>();
//        return listeners.getPlayerRegions(uuid);
    }
    
    /**
     * Gets the regions names a player is currently in.
     * @param uuid UUID of the player in question.
     * @return Set of Strings with the names of the regions the player is currently in.
     */
    public Set<String> getRegionsNames(UUID uuid)
    {
        return getRegions(uuid).stream().map(ProtectedRegion::getId).collect(Collectors.toSet());
    }
    
    /**
     * Checks whether a player is in one or several regions
     * @param uuid UUID of the player in question.
     * @param name List of regions to check.
     * @return True if the player is in the named region.
     */
    public boolean isPlayerInRegion(UUID uuid, String... name)
    {
        /*
        Set<ProtectedRegion> regions = listeners.getPlayerRegions(uuid);
       
        if(regions.size()==0) return false;
    
        for(ProtectedRegion region : regions)
        {
            AtomicBoolean foundThisRegion= new AtomicBoolean(false);
            for(String n : name)
            {
                if(region.getId().equalsIgnoreCase(n))
                    foundThisRegion.set(true);
            }
            if(!foundThisRegion.get())
                return false;
        }
        return true;
         */
        return false;
    }
}
