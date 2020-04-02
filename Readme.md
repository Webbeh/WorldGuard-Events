# WorldGuard Events
## For server admins

This plugin may be required by some other plugins. If so, simply drop the JAR in the **plugins** folder of your server, reload it, and you're set.

  It doesn't do anything on its own and is useless unless required by another plugin.

## For developers

This API provides you with the following events :

   - **RegionEnteredEvent**
   - **RegionsEnteredEvent**
   - **RegionLeftEvent**
   - **RegionsLeftEvent**

All of those are fired upon each player movement into a new set of regions.

They are all pretty self-explainatory. You can use any event interchangeably : the events returning a single ProtectedRegion are fired at the same time as the events returning a Set of ProtectedRegions (which is useful if you have multiple overlapping/neighbouring regions).


## Usage example

```java
import net.raidstone.wgevents.events.RegionEnteredEvent;
import net.raidstone.wgevents.events.RegionsLeftEvent;
import org.bukkit.event.Listener;

public class RegionEventsListener implements Listener {

    @EventHandler
    public void onRegionEntered(RegionEnteredEvent event)
    {
        Player p = Bukkit.getPlayer(event.getUUID());
        String region = event.getRegionName();
        Bukkit.getLogger().info(p.getDisplayName() + " entered the region "+region+" !");
    }
 
    @EventHandler
    public void onRegionsLeft(RegionsLeftEvent event)
    {
        Player p = Bukkit.getPlayer(event.getUUID());
        Set<String> regionsNames = event.getRegionsNames();
        StringBuilder sb = new StringBuilder(p.getDisplayName()+" left the regions :");
        for(String regionName : regionsNames)
          sb.append(regionName).append(", ");
        Bukkit.getLogger().info(sb.toString());
    }
 
}
```

## Maven dependency
This plugin is located in the Maven Central repository, so you simply need to add it as dependency.

```xml
<dependency>
    <groupId>net.raidstone</groupId>
    <artifactId>WorldGuardEvents</artifactId>
    <version>1.15.1</version>
    <scope>provided</scope>
</dependency>
```
    
## [Spigot page](https://www.spigotmc.org/resources/worldguard-events.65176/)

### If you like this plugin, don't forget to rate it, to help other people discover it !
