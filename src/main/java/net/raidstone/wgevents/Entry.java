package net.raidstone.wgevents;

import com.sk89q.worldedit.util.Location;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.session.MoveType;
import com.sk89q.worldguard.session.Session;
import com.sk89q.worldguard.session.handler.Handler;
import org.bukkit.event.Listener;

import java.util.Set;


/**
 * @author weby@we-bb.com [Nicolas Glassey]
 * @version 1.0.0
 * @since 3/3/19
 */
public class Entry extends Handler implements Listener {
   
    public static final Factory factory = new Factory();
    private static Listeners listeners;
    public static class Factory extends Handler.Factory<Entry> {
        @Override
        public Entry create(Session session) {
            return new Entry(session);
        }
    }
    
    public static void setListeners(Listeners listeners)
    {
        Entry.listeners = listeners;
    }
    
    public Entry(Session session) {
        super(session);
    }
    
    @Override
    public boolean onCrossBoundary(LocalPlayer player, Location from, Location to, ApplicableRegionSet toSet, Set<ProtectedRegion> entered, Set<ProtectedRegion> left, MoveType moveType)
    {
        listeners.changeRegions(player.getUniqueId(), toSet.getRegions());
        return true;
    }
    
    
    
    

}
