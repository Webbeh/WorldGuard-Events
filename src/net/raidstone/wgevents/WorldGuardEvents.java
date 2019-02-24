package net.raidstone.wgevents;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author weby@we-bb.com [Nicolas Glassey]
 * @version 1.13.2-0
 * @since 2/24/19
 */
public class WorldGuardEvents extends JavaPlugin implements Listener {
    Listeners listeners = null;
    public void onEnable() {
        String version = Bukkit.getPluginManager().getPlugin("WorldGuard").getDescription().getVersion();
        
        if(version == null || version.isEmpty()) {
            Bukkit.getLogger().severe("[WorldGuardEvents] WorldGuard's version not detected. Are you sure it's installed properly ?");
            Bukkit.getLogger().severe("[WorldGuardEvents] Disabling WorldGuardEvents.");
            return;
        }
        
        if(!version.startsWith("7.0.0"))
        {
            Bukkit.getLogger().warning("[WorldGuardEvents] Detected WorldGuard version \"" + version + "\".");
            Bukkit.getLogger().warning("[WorldGuardEvents] This plugin is meant to work with WorldGuard version \"7.0.0-beta-03;e51a220\" or higher,");
            Bukkit.getLogger().warning("[WorldGuardEvents] and may not work properly with a lower version.");
            Bukkit.getLogger().warning("[WorldGuardEvents] Please update WorldGuard if your version is below \"7.0.0-beta-03;e51a220\".");
        }
        listeners = new Listeners();
        Bukkit.getPluginManager().registerEvents(listeners, this);
    }
    
    @NotNull
    public Set<ProtectedRegion> getRegionsFromPlayer(UUID u)
    {
        return listeners==null?new HashSet<>():listeners.getPlayerRegions(u);
    }
    @NotNull
    public Set<String> getRegionsNamesFromPlayer(UUID u)
    {
        return listeners==null?new HashSet<>():listeners.getPlayerRegionsNames(u);
    }
}
